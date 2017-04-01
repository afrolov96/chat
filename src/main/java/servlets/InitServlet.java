package servlets;

import db.PostgresCP;
import org.apache.log4j.Logger;
import utils.AppProperties;
import utils.EmojiCash;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.util.Map;
import java.util.Properties;

@WebListener
public class InitServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println(servletContextEvent.getServletContext().getRealPath(File.separator) );

        Properties properties = new Properties();
        try {
            properties.load(servletContextEvent.getServletContext().getResourceAsStream("WEB-INF/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppProperties.setProperties(properties);

        try {
            PostgresCP.init(AppProperties.getProperties());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String picsAbsolutePath = servletContextEvent.getServletContext().getRealPath(File.separator) + AppProperties.getProperties().get("picsPath");
        EmojiCash.getInstance(picsAbsolutePath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
