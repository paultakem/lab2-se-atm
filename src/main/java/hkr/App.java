package hkr;

import hkr.models.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class App extends Application {

    public static Database database;
    public static App app;
    private Stage stage;

    static {
        database = new Database();
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = getClass().getResource("/views/atm.fxml");
        FXMLLoader loader = new FXMLLoader(url);

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        stage = primaryStage;
    }

    public void setScene(Scene scene){
        this.stage.setScene(scene);
    }

    public Stage getStage(){
        return this.stage;
    }

}
