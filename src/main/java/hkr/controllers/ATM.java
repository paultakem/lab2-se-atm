package hkr.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.lang3.math.NumberUtils;

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
    private Double inputFromUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorText.setVisible(false);
        inputNum.setOnKeyTyped(event -> {
            errorText.setVisible(false);
        });
        addBtn.setOnMouseClicked(event -> {
            if(checkForNumber()){
                double balance = Double.parseDouble(balanceOutput.getText());
                setBalance(balance + inputFromUser);
                // record to db
            }

        });
        takeBtn.setOnMouseClicked(event -> {
            double balance = Double.parseDouble(balanceOutput.getText());
            if(checkForNumber() && balance >= inputFromUser){

                setBalance(balance - inputFromUser);
                // record to db
            }
            else if(balance < inputFromUser){
                errorText.setVisible(true);
                errorText.setText("Insufficient funds");
            }
        });
    }

    public void setBalance(double balance){
        this.balanceOutput.setText(new DecimalFormat("#.##").format(balance).toString());
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
