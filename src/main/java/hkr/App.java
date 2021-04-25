package hkr;

import hkr.models.Database;
import hkr.models.User;
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

    public static Database database = new Database();
    public static App app;
    private Stage stage;
    private User user;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        URL url = getClass().getResource("/views/atm.fxml");
//        FXMLLoader loader = new FXMLLoader(url);
//        atm = new Scene(loader.load());
        URL url2 = getClass().getResource("/views/login.fxml");
        FXMLLoader loader2 = new FXMLLoader(url2);
        Scene login = new Scene(loader2.load());
//        URL url3 = getClass().getResource("/views/register.fxml");
//        FXMLLoader loader3 = new FXMLLoader(url3);
//        register = new Scene(loader3.load());

        primaryStage.setScene(login);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        stage = primaryStage;
        app = this;
    }

    public Stage getStage(){
        return this.stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
