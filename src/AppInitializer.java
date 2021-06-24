import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root= FXMLLoader.load(this.getClass().getResource("View/EditForm.fxml"));
        Scene mainscene= new Scene(root);
        primaryStage.setScene(mainscene);
        primaryStage.setTitle("Simple Text Editor");
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
