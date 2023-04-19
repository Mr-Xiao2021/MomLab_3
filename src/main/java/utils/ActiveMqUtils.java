package utils;

import com.alibaba.fastjson.JSON;
import domain.Message;
import domain.User;
import javafx.scene.control.ListView;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ClassName ActiveMqUtils
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 17:16
 */
public class ActiveMqUtils {
    private static final String BROKER_URL = "tcp://114.132.224.128:61616";













    // 发布订阅模式，即群发模式


    public void publishSubscribeSend(String topicName,String messageContent) throws JMSException {
        //1.创建连接工厂
        ConnectionFactory factory
                = new ActiveMQConnectionFactory(BROKER_URL);

        //2.创建连接
        Connection connection = factory.createConnection();

        //3.打开连接
        connection.start();

        //4.创建session

        /*
         * 参数一：是否开启事务操作
         * 参数二：消息确认机制
         */

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建目标地址（Queue:点对点消息，Topic：发布订阅消息）
        Topic topic = session.createTopic(topicName);

        //6.创建消息生产者
        MessageProducer producer = session.createProducer(topic);

        //7.创建消息
        //createTextMessage: 文本类型
        TextMessage textMessage = session.createTextMessage(messageContent);

        //8.发送消息
        producer.send(textMessage);

        System.out.println("消息发送完成");

        //9.释放资源
        session.close();
        connection.close();
    }


    public void publishSubscribeReceive(String topicName, ListView<Message> messageListView, User currentUser) throws JMSException {
        //1.创建连接工厂
        ConnectionFactory factory
                = new ActiveMQConnectionFactory(BROKER_URL);

        //2.创建连接
        Connection connection = factory.createConnection();

        //3.打开连接
        connection.start();

        //4.创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.指定目标地址
        Topic topic = session.createTopic(topicName);

        //6.创建消息的消费者
        MessageConsumer consumer = session.createConsumer(topic);

        //7.设置消息监听器来接收消息
        //处理消息
        consumer.setMessageListener(message -> {
            if(message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;

                try {
                    String receiveText = textMessage.getText();
                    Message srcMessage = JSON.parseObject(receiveText, Message.class);
                    // 此处耦合度较高，但实在没想到怎么怎么解耦

                    // 如果不是自己发送的，那么就添加至消息窗，避免之前sendBtn消息重复添加消息记录
                    if(!srcMessage.getSender().equals(currentUser)){
                        messageListView.getItems().add(srcMessage);
//                        int scrollIndex = messageListView.getItems().size() - 1;
//                        messageListView.scrollTo(scrollIndex);
                    }


                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //注意：在监听器的模式下千万不要关闭连接，一旦关闭，消息无法接收

    }
}
