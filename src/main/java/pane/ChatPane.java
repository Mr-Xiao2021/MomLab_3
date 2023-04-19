package pane;

import domain.Message;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import pane.bubble.BubbleSpec;
import pane.bubble.BubbledLabel;
import utils.ElementListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName ChatPane
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 20:09
 */
public class ChatPane {
    public BorderPane getChatPane(Stage primaryStage, User currentUser){


        //聊天纪录列表，布局中间位置
        AnchorPane listViewPane = new AnchorPane();

        listViewPane.setStyle(
                "-fx-background-color:#afdde1;"
        );
        listViewPane.setPrefHeight(100);
        listViewPane.setPrefWidth(350);

        // 测试数据
//        User u1 = new User("Jeson", "a man");
//        User u2 = new User("Lanni", "a girl");
//        User u3 = new User("Whale", "a dog");
//        Message m1 = new Message(u1, new Date(), "a message from Jeson");
//        Message m2 = new Message(u2, new Date(), "a crazy girl");
//        Message m3 = new Message(u3, new Date(), "a bad day");
//        List<Message> messageList = new ArrayList<>();
//        messageList.add(m1);
//        messageList.add(m2);
//        messageList.add(m3);
//        ObservableList<Message> messages = FXCollections.observableArrayList(messageList);


        // 创建messageListView
        MessageListViewFactory messageListViewFactory = new MessageListViewFactory();
        ListView<Message> messageListView = messageListViewFactory.getMessageListView(currentUser);


//        messageListView.setItems(messages);




        listViewPane.getChildren().add(messageListView);
        AnchorPane.setBottomAnchor(messageListView, 0.0);
        AnchorPane.setTopAnchor(messageListView, 0.0);
        AnchorPane.setRightAnchor(messageListView, 0.0);
        AnchorPane.setLeftAnchor(messageListView, 0.0);
        listViewPane.setPadding(new Insets(10.0));




        // 底层含有输入框及关闭、发送按钮
        TextArea enterMessageArea = new TextArea();
        enterMessageArea.setPromptText("Enter message here");
        enterMessageArea.setFont(new Font(13.0));

        HBox controlSend = new HBox();
        Button closeBtn = new Button("关闭");
        Button sendBtn = new Button("发送");

        Button addUserBtn = new Button("多用户聊天");

        addUserBtn.setOnAction(event -> {
            AddUserInfoPane addUserInfoPane = new AddUserInfoPane();
            addUserInfoPane.showAddPane(primaryStage);
        });





        controlSend.getChildren().add(0,sendBtn);
        controlSend.getChildren().add(1,closeBtn);

        controlSend.setPadding(new Insets(10.0));
        controlSend.setSpacing(10.0);
        AnchorPane controlPane = new AnchorPane(enterMessageArea,controlSend,addUserBtn);
        AnchorPane.setRightAnchor(controlSend,10.0);
        AnchorPane.setRightAnchor(addUserBtn,20.0);
        AnchorPane.setBottomAnchor(controlSend,20.0);
        AnchorPane.setTopAnchor(addUserBtn,20.0);
        AnchorPane.setRightAnchor(enterMessageArea,10.0);
        AnchorPane.setLeftAnchor(enterMessageArea,0.0);


        // top层是聊天对象信息


        TextField targetUserInfo = new TextField("水友群");
        targetUserInfo.setEditable(false);
        targetUserInfo.setAlignment(Pos.CENTER);


        // 设置监听事件，这是代码核心
        ElementListener listener = new ElementListener();
        listener.bindListView(messageListView,currentUser);
        listener.bindSendBtn(enterMessageArea,sendBtn,messageListView,currentUser);
        listener.bindCloseBtn(closeBtn,primaryStage);




        BorderPane borderPane = new BorderPane(listViewPane, targetUserInfo, null, controlPane, null);
        borderPane.setPadding(new Insets(0.0,0.0,0.0,0.0));
        return borderPane;
    }
}
