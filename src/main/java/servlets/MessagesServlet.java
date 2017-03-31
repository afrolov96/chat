package servlets;

import com.google.gson.Gson;
import db.DataFactory;
import db.PostgresCP;
import models.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


@WebServlet(urlPatterns = {"/messages"}, loadOnStartup = 1)
public class MessagesServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:postgresql://localhost/chatdb");
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "postgres");
        properties.setProperty("connectionCount", "5");
        try {
            PostgresCP.init(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Enumeration<String> params = req.getParameterNames();

        /*while (params.hasMoreElements()) {
            String p_name = params.nextElement();
            System.out.println(">>> " + p_name + " = " + req.getParameter(p_name));
        }
*/
        DataFactory dataFactory = new DataFactory();
        Iterable<Message> messages = dataFactory.loadMessages(Long.parseLong(req.getParameter("p_now")));
        resp.getWriter().write(gson.toJson(messages));
        resp.getWriter().flush();
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataFactory dataFactory = new DataFactory();
        dataFactory.addMessage(req);
    }
}
