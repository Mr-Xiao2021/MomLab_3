package pane;

import domain.Message;
import domain.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import pane.bubble.BubbleSpec;
import pane.bubble.BubbledLabel;
import service.Trans;

/**
 * ClassName MessageListViewFactory
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 20:23
 */
public class MessageListViewFactory {

    /**
     * 传入当前用户并返回消息的 ListView组件
     * @param currentUser
     * @return
     */
    public ListView<Message> getMessageListView(User currentUser){

        ListView<Message> messageListView = new ListView<>();
        messageListView.setPrefWidth(400);
        messageListView.setPrefWidth(100);

        // 翻译api预加载
        Trans.getTranslateRes("加载中....");

        messageListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                ListCell<Message> listCell = new ListCell<Message>(){
                    @Override
                    protected void updateItem(Message item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            Label senderName = new Label(item.getStrSender());

                            Label timeStamp = new Label(item.getStrTimeStamp());
                            timeStamp.setFont(new Font(10.0));

                            Label transResult = new Label(Trans.getTranslateRes(item.getMessageContent()));
                            transResult.setFont(new Font(10.0));

                            boolean isMe = item.getSender().equals(currentUser);
                            BubbleSpec bs = (!isMe) ? BubbleSpec.FACE_LEFT_CENTER : BubbleSpec.FACE_RIGHT_CENTER;
                            Color bc = (!isMe) ? Color.WHEAT : Color.LIGHTGREEN;




                            BubbledLabel bl = new BubbledLabel();
                            bl.setBubbleSpec(bs);
                            bl.setText(item.getMessageContent());
                            bl.setBackground(new Background(new BackgroundFill(bc,
                                    null, null)));

                            bl.setPrefHeight(50.0);

                            VBox sendInfoVbox = new VBox(timeStamp, bl,transResult);


                            HBox hBox;
                            if(!isMe){
                                sendInfoVbox.setAlignment(Pos.CENTER_LEFT);
                                hBox = new HBox(0.0, senderName, sendInfoVbox);
                                hBox.setAlignment(Pos.CENTER_LEFT);
                            }else{
                                sendInfoVbox.setAlignment(Pos.CENTER_RIGHT);
                                hBox = new HBox(0.0, sendInfoVbox, senderName);
                                hBox.setAlignment(Pos.CENTER_RIGHT);
                            }
                            hBox.setPrefHeight(50.0);
                            hBox.setSpacing(5.0);
                            hBox.setPadding(new Insets(5.0));
                            this.setGraphic(hBox);
                        }else{
                            HBox hBox = new HBox();
                            hBox.setPrefHeight(50.0);
                            hBox.setSpacing(5.0);
                            setGraphic(hBox);
                        }

                    }
                };
                return listCell;
            }
        });


        return messageListView;
    }
}
