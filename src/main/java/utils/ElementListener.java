package utils;

import domain.Message;
import domain.User;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.jms.JMSException;

/**
 * ClassName ElementListener
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 17:13
 */
public class ElementListener {

    private static final MessageUtils MESSAGE_UTILS = new MessageUtils();
    public void bindListView(ListView<Message> messageListView, User currentUser){
        // 开启消息监听
        try {
            MESSAGE_UTILS.receiveMessage(messageListView,currentUser);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void bindCloseBtn(Button closeBtn, Stage primaryStage){

        closeBtn.setOnAction(event -> {
            primaryStage.close();
        });
    }


    // 绑定发送按钮，信息列表，以及信息输入框

    public void bindSendBtn(TextArea enterMessageArea, Button sendBtn, ListView<Message> messageListView, User currentUser){
        sendBtn.setOnAction(event -> {
            String sendText = enterMessageArea.getText();
            try {
                MESSAGE_UTILS.sendMessage(messageListView,sendText,currentUser);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            enterMessageArea.clear();
        });
    }


}