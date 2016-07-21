import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Logic;
import tracking.Analytics;

public class Main extends Application {

    Logic TDDTLogic;
    Analytics anal;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader rootloader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = rootloader.load();
        Scene rootScene = new Scene(root,1025,750);
        primaryStage.setTitle("TDDT");
        primaryStage.setScene(rootScene);
        primaryStage.setResizable(true);
        primaryStage.show();

        FXMLLoader statisticsLoader = new FXMLLoader((getClass().getResource("/stats.fxml")));
        Parent stats = statisticsLoader.load();
        Scene statsScene = new Scene(stats,1000,800);

        Analytics anal = new Analytics(statisticsLoader.getController(), new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(rootScene);
            }
        });

        TDDTLogic = new Logic(rootloader.getController(), new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(statsScene);
                anal.loadTracking(TDDTLogic.timeLine);
            }
        } );

    }


    public static void main(String[] args) {
        launch(args);
    }
}
