import domain.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pane.ChatPane;

/**
 * ClassName Main3
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/19 9:05
 */
public class Main extends Application {
    private final static double STAGE_WIDTH = 700;
    private final static double STAGE_HEIGHT = 600;
    private final static ChatPane CHAT_PANE = new ChatPane();

    @Override
    public void start(Stage primaryStage) throws Exception {



        User currentUser = new User("Stark", "a wolf");
        Scene scene = new Scene(CHAT_PANE.getChatPane(primaryStage,currentUser));

        primaryStage.setScene(scene);
        primaryStage.setTitle(currentUser.getName());
        primaryStage.setHeight(STAGE_HEIGHT);
        primaryStage.setWidth(STAGE_WIDTH);

        primaryStage.show();
    }
}