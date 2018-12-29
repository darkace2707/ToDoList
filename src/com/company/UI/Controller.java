package com.company.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.company.BL.Authorization;
import com.company.DB.UserMySQLRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LogInButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button SignUpButton;

    @FXML
    void initialize() {
        LogInButton.setOnAction(actionEvent -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();

            int id = -1;
            if (!login.equals("") && !password.equals("")) {
                Authorization auth = new Authorization(new UserMySQLRepository());
                id = auth.logIn(login, password);
            }

            if (id != -1) {
                System.out.println("Success");
            } else {
                System.out.println("Error");
            }
        });

        SignUpButton.setOnAction(event -> {
            SignUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SignUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        });
    }

}
