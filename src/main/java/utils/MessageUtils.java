package utils;

import com.alibaba.fastjson.JSONObject;
import domain.Message;
import domain.User;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import javax.jms.JMSException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName MessageUtils
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 17:13
 */
public class MessageUtils {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String DEFAULT_TOPIC_NAME = "GROUP_LAB_3";

    public boolean sendMessage(ListView<Message> messageListView, String waitToSend, User currentUser) throws JMSException {
        if(waitToSend == null || waitToSend.length() == 0 || messageListView == null || currentUser == null){
            return false;
        }

        // 创建消息实体
        Message message = new Message(currentUser, new Date(), waitToSend);

        // 使用 activemq 发送消息，注意消息封装的是 Message 对象
        ActiveMqUtils activeMqUtils = new ActiveMqUtils();
        activeMqUtils.publishSubscribeSend(DEFAULT_TOPIC_NAME, JSONObject.toJSONString(message));

        // 将消息添加至前端页面并展示
        ObservableList<Message> items = messageListView.getItems();
        items.add(message);
        messageListView.scrollTo(items.size() - 1);
        return true;

    }


    public void receiveMessage(ListView<Message> messageListView,User currenUser) throws JMSException {
        ActiveMqUtils activeMqUtils = new ActiveMqUtils();
        activeMqUtils.publishSubscribeReceive(DEFAULT_TOPIC_NAME,messageListView,currenUser);
    }

}
