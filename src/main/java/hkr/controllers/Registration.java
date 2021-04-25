package hkr.controllers;

import hkr.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Registration implements Initializable {

    @FXML
    TextField email;
    @FXML
    PasswordField password, repassword;
    @FXML
    Button register, backToLogin;
    @FXML
    Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backToLogin.setOnMouseClicked(e -> {
            URL url = getClass().getResource("/views/login.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            try {
                Scene login = new Scene(loader.load());
                App.app.getStage().setScene(login);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        register.setOnMouseClicked(e -> {
            if(!email.getText().isEmpty()){
                if(!password.getText().isEmpty() & !repassword.getText().isEmpty()){
                    if(password.getText().equals(repassword.getText())){
                        App.database.addAccount(email.getText(), password.getText());
                        URL url = getClass().getResource("/views/login.fxml");
                        FXMLLoader loader = new FXMLLoader(url);
                        try {
                            Scene login = new Scene(loader.load());
                            App.app.getStage().setScene(login);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    else {
                        error.setText("Password do not match!");
                        error.setVisible(true);
                    }
                }
                else {
                    error.setText("Password/RePassword cannot be empty!");
                    error.setVisible(true);
                }
            }
            else {
                error.setText("Email cannot be empty!");
                error.setVisible(true);
            }
        });
    }
}
