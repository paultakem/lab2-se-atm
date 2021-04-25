package hkr.controllers;

import hkr.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    Button login, register;
    @FXML
    TextField email, password;
    @FXML
    Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        register.setOnMouseClicked(e -> {
            URL url = getClass().getResource("/views/registration.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            try {
                Scene register = new Scene(loader.load());
                App.app.getStage().setScene(register);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        login.setOnMouseClicked(e -> {
            if(!email.getText().isEmpty() & !password.getText().isEmpty()){
                int userID = App.database.checkCredentials(email.getText(), password.getText());

                if(userID != -1){
                    URL url = getClass().getResource("/views/atm.fxml");
                    FXMLLoader loader = new FXMLLoader(url);
                    try {
                        App.app.setUser(App.database.getUser(userID));
                        Scene atm = new Scene(loader.load());
                        App.app.getStage().setScene(atm);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else {
                    error.setText("Incorrect email or password");
                    error.setVisible(true);
                }
            }
        });
    }
}
