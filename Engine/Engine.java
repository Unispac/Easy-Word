package Engine;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Engine.controlManager.controlManager;
import Engine.home.home;
import Engine.memorize.memorize;
import Engine.review.review;
import Engine.wordListManager.wordListManager;

public class Engine extends Application
{
    public static void trigger(String[] args)
    {
        launch(args);
    }
    public void start(Stage primaryStage)
    {
        touch();
        primaryStage.setMaximized(true);
        //primaryStage.setAlwaysOnTop(true);
        //primaryStage.setFullScreen(true);
        //primaryStage.setIconified(true);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        controlManager.setStage(primaryStage);
        controlManager.pushPage("home", "/frontEnd/icon.png", "/frontEnd/home.fxml", "EasyWord-Home");
        controlManager.pushPage("memorize", "/frontEnd/icon.png", "/frontEnd/memorize.fxml", "EasyWord-Memorize");
        controlManager.pushPage("review", "/frontEnd/icon.png", "/frontEnd/review.fxml", "EasyWord-Review");
        controlManager.showPage("home");
    }
    private void touch()
    {
        home.touch();
        memorize.touch();
        review.touch();
        wordListManager.init();
    }
}