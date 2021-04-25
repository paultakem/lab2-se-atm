package hkr.controllers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.jfoenix.controls.JFXButton;
import hkr.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ATM implements Initializable {
    @FXML
    private Text balanceOutput, errorText;
    @FXML
    private TextField inputNum;
    @FXML
    private JFXButton addBtn, takeBtn;
    private Double inputFromUser, balanceNum;
    @FXML
    private Button exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        balanceNum = App.app.getUser().getBalance().doubleValue();
        balanceOutput.setText(String.valueOf(App.app.getUser().getBalance()));
        inputNum.setText("");
        exit.setOnMouseClicked(e -> {
            URL url = getClass().getResource("/views/login.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            try {
                Scene login = new Scene(loader.load());
                App.app.getStage().setScene(login);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        errorText.setVisible(false);
        inputNum.setOnKeyTyped(event -> {
            errorText.setVisible(false);
        });
        addBtn.setOnMouseClicked(event -> {
            errorText.setVisible(false);
            if(checkForNumber()){
                double newBalance = balanceNum + inputFromUser;
                setBalance(newBalance);
                // record to db
                App.database.updateBalance(App.app.getUser().getId(), newBalance);
            }
        });
        takeBtn.setOnMouseClicked(event -> {
            errorText.setVisible(false);
            if(checkForNumber() && balanceNum >= inputFromUser){
                double newBalance = balanceNum - inputFromUser;
                setBalance(newBalance);
                // record to db
                App.database.updateBalance(App.app.getUser().getId(), newBalance);
            }
            else if(checkForNumber() && balanceNum < inputFromUser){
                errorText.setVisible(true);
                errorText.setText("Insufficient funds");
            }
            else {
                errorText.setVisible(true);
                errorText.setText("No input provided");
            }
        });
    }

    public void setBalance(double balance){
        this.balanceOutput.setText(new DecimalFormat("#.##").format(balance).toString());
        this.balanceNum = balance;
    }

    private boolean checkForNumber(){
        if(!NumberUtils.isParsable(inputNum.getText())){
            errorText.setVisible(true);
            errorText.setText("The input value must be number, not a string");
            return false;
        }

        double input = Double.parseDouble(inputNum.getText());
        inputFromUser = Math.abs(input);

        return true;
    }
}
