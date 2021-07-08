import Property.Property;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Properties prop = new Properties();
        File fileUser = new File("UserPref.properties");


        AnchorPane root = FXMLLoader.load(this.getClass().getResource("View/EditForm.fxml"));
        Scene mainscene = new Scene(root);
        if (fileUser.exists()) {
            try (FileReader fileReader = new FileReader(fileUser);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                prop.load(bufferedReader);
                primaryStage.setX(Double.parseDouble(prop.getProperty("xpos")));
                primaryStage.setY(Double.parseDouble(prop.getProperty("ypos")));
                primaryStage.setWidth(Double.parseDouble(prop.getProperty("width")));
                primaryStage.setWidth(Double.parseDouble(prop.getProperty("height")));
            }
        } else {
            new Property().Default();
            File fileDef = new File("Default.properties");
            try (FileReader fileReader = new FileReader(fileDef);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                prop.load(bufferedReader);
                primaryStage.setX(Double.parseDouble(prop.getProperty("xpos")));
                primaryStage.setY(Double.parseDouble(prop.getProperty("ypos")));
                primaryStage.setWidth(Double.parseDouble(prop.getProperty("width")));
                primaryStage.setWidth(Double.parseDouble(prop.getProperty("height")));
            }


        }
        primaryStage.setScene(mainscene);
        primaryStage.setTitle("Simple Text Editor");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

}
