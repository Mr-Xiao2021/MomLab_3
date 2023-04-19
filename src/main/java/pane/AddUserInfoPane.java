package pane;

import domain.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * ClassName AddUserInfoPane
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/19 11:05
 */
public class AddUserInfoPane {

    private final static ChatPane CHAT_PANE = new ChatPane();
    private final static double STAGE_WIDTH = 700;
    private final static double STAGE_HEIGHT = 600;

    private Label nameLabel = new Label("姓名");
    private Label infoLabel = new Label("密码");
    private TextField nameText = new TextField();
    private TextField infoText = new TextField();

    private Button confirmBtn = new Button("确定");
    private Button cancelBtn = new Button("重置");
    private HBox hBox = new HBox(50, cancelBtn, confirmBtn);



    {
        nameText.setFocusTraversable(false);
        infoLabel.setFocusTraversable(false);
        hBox.setPadding(new Insets(5));


        cancelBtn.setOnAction(event -> {
            nameText.clear();
            infoText.clear();
        });




    }


    public void showAddPane(Stage mainStage){
        nameText.setPromptText("请输入用户姓名");
        infoText.setPromptText("请输入用户密码");



        GridPane showPane = new GridPane();


        showPane.add(nameLabel, 0, 0);
        showPane.add(nameText, 1, 0);
        showPane.add(infoLabel, 0, 1);
        showPane.add(infoText, 1, 1);
        showPane.add(cancelBtn, 0, 3);
        showPane.add(confirmBtn, 1, 3);
        showPane.setAlignment(Pos.CENTER);
        showPane.setHgap(10.0);
        showPane.setVgap(10.0);
        showPane.setPrefHeight(100);
        showPane.setPrefWidth(100);





        Stage primaryStage = new Stage();
        primaryStage.initOwner(mainStage);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(showPane);
        primaryStage.setScene(scene);

        primaryStage.setTitle("添加用户");
        primaryStage.setHeight(300);
        primaryStage.setWidth(400);

        primaryStage.show();
        confirmBtn.setOnAction(event -> {

            User newUser = getNewUser();
            if(newUser != null){
                // 关闭添加窗口
                primaryStage.close();
                // 开启新的聊天框


                Stage newChatUserStage = new Stage();
                Scene newChatUserScene = new Scene(CHAT_PANE.getChatPane(newChatUserStage,newUser));

                newChatUserStage.setScene(newChatUserScene);
                newChatUserStage.setTitle(newUser.getName());
                newChatUserStage.setHeight(STAGE_HEIGHT);
                newChatUserStage.setWidth(STAGE_WIDTH);

                newChatUserStage.show();


            }


        });
    }

    public TextField getNameText() {
        return nameText;
    }

    public void setNameText(TextField nameText) {
        this.nameText = nameText;
    }

    public TextField getInfoText() {
        return infoText;
    }

    public void setInfoText(TextField infoText) {
        this.infoText = infoText;
    }

    private User getNewUser(){
        if(infoText.getText() == null || nameText.getText() == null || infoText.getText().length() == 0 || nameText.getText().length() == 0){
            return null;
        }
        return new User(nameText.getText(),infoText.getText());
    }
}

