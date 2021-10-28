/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Alex Hurtado
 */
public class SignUpController extends Application{
    //Logger del controlador de la ventana "ViewSignIn"
    private static final Logger LOG = Logger.getLogger(SignUpController.class.getName());
    //Label para el campo "Usuario"
    @FXML
    private Label lblUser;
    //Label para el campo "e-mail"
    @FXML
    private Label lblEmail;
    //Label para el campo "Nombre completo"
    @FXML
    private Label lblFullName;
    //Label para el campo "Contraseña"
    @FXML
    private Label lblPassword;
    //Label para el campo "Repetir contraseña"
    @FXML
    private Label lblRepeatPassword;
    //Label para informar de un error al registrarse
    @FXML
    private Label lblError;
    //Label para informar de un error en el campo "Usuario"
    @FXML  
    private Label lblErrorUser;
    //Label para informar de un error en el campo "e-mail"
    @FXML
    private Label lblErrorEmail;
    //Label para informar de un error en el campo "Nombre completo"
    @FXML
    private Label lblErrorFullName;
    //Label para informar de un error en el campo "Contraseña"
    @FXML
    private Label lblErrorPassword;
    //Label para informar de un error en el campo "Repetir contraseña"
    @FXML
    private Label lblErrorRepeatPassword;
    //Label "¿Ya tienes una cuenta?"
    @FXML
    private Label lblSignIn;
    //TextField para el campo "Usuario"
    @FXML
    private TextField tfUser;
    //TextField para el campo "e-mail"
    @FXML
    private TextField tfEmail;
    //TextField para el campo "Nombre completo"
    @FXML
    private TextField tfFullName;
    //TextField para el campo "Contraseña"
    @FXML
    private TextField tfPassword;
    //TextField para el campo "Repetir contraseña"
    @FXML
    private TextField tfRepeatPassword;
    //HyperLink a la ventana princial "ViewSignIn"
    @FXML
    private Hyperlink linkLogin;
    //Button para registrarse
    @FXML
    private Button btnSingUp;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
    }
}
