package com.company.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.company.BL.Authorization;
import com.company.DB.UserMySQLRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private Button SignUpButton;

    @FXML
    private Button backButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    void initialize() {
        SignUpButton.setOnAction(actionEvent -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            String repeatPassword = repeatPasswordField.getText().trim();

            if (!login.equals("") && !password.equals("") && !repeatPassword.equals("") && password.equals(repeatPassword)) {
                Authorization auth = new Authorization(new UserMySQLRepository());
                auth.signUp(login, password);

                SignUpButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Authorization.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } else {
                System.out.println("Passwords are not similar");
            }
        });

        backButton.setOnAction(actionEvent -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Authorization.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        });
    }
}
