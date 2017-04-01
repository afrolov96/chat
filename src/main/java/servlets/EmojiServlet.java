package servlets;

import com.google.gson.Gson;
import models.Emoji;
import models.Message;
import utils.AppProperties;
import utils.EmojiCash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(urlPatterns = {"/emoji"})
public class EmojiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Collection<Emoji> emojis = new ArrayList<>();
        for (int i = 0; i < 230; i++) {
            emojis.add(((ArrayList<Emoji>)EmojiCash.getInstance(null)).get(i));
        }

        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(emojis));
        resp.getWriter().flush();
        resp.getWriter().close();
    }
}
