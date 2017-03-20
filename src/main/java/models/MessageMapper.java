package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MessageMapper {
    public static Message getMessageFromDb(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setMessageTime(Long.parseLong(resultSet.getString("message_time")));
        message.setMessageBody(resultSet.getString("message_body"));
        return message;
    }

    public static Message getMessageFromHttpRequest(HttpServletRequest req){
        String params = req.getParameter("params");
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(params, new TypeToken<Map<String, String>>() {
        }.getType());
        Message message = new Message();
        message.setMessageTime(Long.parseLong(map.get("messageTime")));
        message.setMessageBody(map.get("messageBody"));
        return message;
    }
}
