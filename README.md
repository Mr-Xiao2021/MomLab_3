# 利用谷歌翻译（百度翻译）实现聊天软件的边聊天边翻译



## 一、技术选型



* JavaFx
* ActiveMQ
* 百度翻译开放API



## 二、运行效果



* **基本群聊界面**

![image.png](https://cdn.nlark.com/yuque/0/2023/png/26076549/1681894134539-6c77bbac-d3cb-41c1-92ae-806c4db59207.png)

* **群添加多用户**

![image.png](https://cdn.nlark.com/yuque/0/2023/png/26076549/1681894271065-dff7d5f5-c405-40c6-b7c7-38c59e311892.png)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/26076549/1681894440729-05152af5-ac19-49d7-8975-dc8e972ae678.png)



![image.png](https://cdn.nlark.com/yuque/0/2023/png/26076549/1681894690053-14f4614c-3497-4d70-a053-745aa1089b43.png?x-oss-process=image%2Fresize%2Cw_1125%2Climit_0)



* **总体效果**



![ChatDemo.gif](https://cdn.nlark.com/yuque/0/2023/gif/26076549/1681886181160-19678519-e977-415f-9c8d-18a71707e684.gif)





## 三、核心代码

![image.png](https://cdn.nlark.com/yuque/0/2023/png/26076549/1681894893181-a0101e18-fe8b-4b65-8bb9-fc488707b5e8.png?x-oss-process=image%2Fresize%2Cw_1125%2Climit_0)





### 3.1、自动翻译



```java
public class TransUtils {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private String appid;
    private String securityKey;
    public TransUtils(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }
    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", appid);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));
        return params;
    }

}



public class Trans {
    private static final String APP_ID = "20230418001646798";
    private static final String SECURITY_KEY = "5YFlJjr3IMLTz8Bet7QP";

    public static final TransUtils TRANS_UTILS = new TransUtils(APP_ID, SECURITY_KEY);

    public static String getTranslateRes(String src){
        String transResultStr = TRANS_UTILS.getTransResult(src, "zh", "en");
        JSONObject result = JSONObject.parseObject(transResultStr);
        JSONArray jsonArray = result.getJSONArray("trans_result");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        return jsonObject.getString("dst");
    }
}
```





### 3.2、消息传送机制（发布订阅模式）



````java
	// 发布订阅模式，即群发模式

    public void publishSubscribeSend(String topicName,String messageContent) throws JMSException {
        ConnectionFactory factory
                = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage(messageContent);
        producer.send(textMessage);
        session.close();
        connection.close();
    }


    public void publishSubscribeReceive(String topicName, ListView<Message> messageListView, User currentUser) throws JMSException {
 
        ConnectionFactory factory
                = new ActiveMQConnectionFactory(BROKER_URL);


        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            if(message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;

                try {
                    String receiveText = textMessage.getText();
                    Message srcMessage = JSON.parseObject(receiveText, Message.class);
                    // 此处耦合度较高，但实在没想到怎么怎么解耦
                    if(!srcMessage.getSender().equals(currentUser)){
                        messageListView.getItems().add(srcMessage);
                    }

                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //注意：在监听器的模式下保持连接，一旦关闭，消息无法接收

    }
````





### 3.3、源码地址



[Mr-Xiao2021/MomLab_3: XMU_中间件实验三 ](https://github.com/Mr-Xiao2021/MomLab_3)
