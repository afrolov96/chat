package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

public class PostgresCP {
    private static Queue<Connection> connectionQueue = new ConcurrentLinkedQueue<>();
    private static String url = null;
    private static String user = null;
    private static String password = null;
    private static volatile int connectionCount = 0;

    private PostgresCP(){

    }

    public static void init(Properties properties) throws Exception {
        if (properties == null) {
            throw new Exception("Empty connection pool properties!");
        }
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        connectionCount = Integer.parseInt(properties.getProperty("connectionCount"));
        Class.forName("org.postgresql.Driver");
        if (url != null && user != null && password != null && connectionCount > 0) {
            for (int i = 0; i < connectionCount; i++) {
                connectionQueue.add(DriverManager.getConnection(url, user, password));
            }
        } else {
            throw new Exception("Some properties incorrect");
        }
        System.out.println("ConnectionPool ready for a " + connectionCount + " connection(s)");
    }

    public static Connection getConnection() {
        Connection connection = null;
        if (connectionQueue.size() > 0) {
            synchronized (PostgresCP.class) {
                if (connectionQueue.size() > 0) {
                    connection = connectionQueue.remove();
                }
            }
        }
        return connection;
    }

    public static void putConnection(Connection connection) {
        if (connectionQueue.size() < connectionCount) {
            synchronized (PostgresCP.class) {
                if (connectionQueue.size() < connectionCount) {
                    connectionQueue.add(connection);
                }
            }
        }
    }

    public static void destroyCP() {
        for (Connection connection : connectionQueue) {
            try (Connection conn = connection) {
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Connections closed");
            }
        }
    }
}
