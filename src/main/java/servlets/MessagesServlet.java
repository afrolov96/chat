package servlets;

import com.google.gson.Gson;
import db.DataFactory;
import models.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


@WebServlet(urlPatterns = {"/messages"})
public class MessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        DataFactory dataFactory = new DataFactory();
        Iterable<Message> messages = dataFactory.loadMessages(Long.parseLong(req.getParameter("p_now")));

        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
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
