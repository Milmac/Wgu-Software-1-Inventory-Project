package wguMainAppSoftware1;

import View_Controller.MainScreenController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class mainApp extends Application {

    Stage window;
    private AnchorPane  mainScreenView;

    public void initMainScreen() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        AnchorPane mainScreenView = (AnchorPane) root;

        Scene scene = new Scene(mainScreenView);
        window.setScene(scene);
        window.show();
    }

    public void showMainScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(mainApp.class.getResource("/View_Controller/MainScreen.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        AnchorPane mainScreenView = (AnchorPane) loader.load();

        MainScreenController controller = loader.getController();
        controller.setMainApp(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Inventory Mangement System");
        initMainScreen();
        showMainScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
