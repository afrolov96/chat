package models;

public class Message {
    private Long messageTime;
    private String messageBody;

    public Long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageTime=" + messageTime +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
