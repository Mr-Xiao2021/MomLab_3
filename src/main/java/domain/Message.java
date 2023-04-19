package domain;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName Message
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 17:12
 */

public class Message implements Serializable {
    private User sender;
    private Date timeStamp;
    private String messageContent;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Message(User sender, Date timeStamp, String messageContent) {
        this.sender = sender;
        this.timeStamp = timeStamp;
        this.messageContent = messageContent;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getStrSender(){
        return sender.toString();
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getStrTimeStamp(){
        return SDF.format(this.timeStamp);
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + getStrSender() +
                ", timeStamp=" + getStrTimeStamp() +
                ", messageContent=" + messageContent +
                '}';
    }
}
