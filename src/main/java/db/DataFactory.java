package db;

import models.Message;
import models.MessageMapper;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    private final String SQL_LOAD_OLD_MESSAGES = "select message_time, message_body from messages where message_time < ? limit ? ";
    private final String SQL_LOAD_NEW_MESSAGES = "select message_time, message_body from messages where message_time > ? ";
    private final String SQL_ADD_NEW_MESSAGE = "insert into messages(message_time, message_body) values (?, ?)";

    public Iterable<Message> loadMessages(Long timeStampt) {
        List<Message> messageList = new ArrayList<>();
        Connection connection = PostgresCP.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_NEW_MESSAGES)) {
            preparedStatement.setLong(1, timeStampt);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                messageList.add(MessageMapper.getMessageFromDb(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            PostgresCP.putConnection(connection);
        }
        return messageList;
    }

    public void addMessage(HttpServletRequest req) {
        Message message = MessageMapper.getMessageFromHttpRequest(req);
        Connection connection = PostgresCP.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_NEW_MESSAGE)) {
            preparedStatement.setLong(1, message.getMessageTime());
            preparedStatement.setString(2, message.getMessageBody());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            PostgresCP.putConnection(connection);
        }
    }
}
