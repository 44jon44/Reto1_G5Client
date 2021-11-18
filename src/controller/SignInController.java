/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Signable;
import model.SignableFactory;
import model.User;

/**
 * @author ibai arriola
 */
public class SignInController {

    //Hasta que esté la BD Lista de usuarios de prueba para ejercicios
    private ArrayList<User> usuarios = new ArrayList<User>();

    // un logger que nos informará mediante la terminal
    private static final Logger LOG = Logger.getLogger(SignInController.class.getName());
    //declaramos los componentes de la ventana  que manipularemos a continuación
    private Stage signInStage;
    // textField  donde añadimos  el usuario
    @FXML
    private TextField tfUser;
    // textField  donde añadimos  la contraseña
    @FXML
    private PasswordField tfPassword;
    // button que inicia la sesión
    @FXML
    private Button btnSignIN;
    // un  label que visualiza los diferentes errores
    @FXML
    private AnchorPane panelSignIN;
    @FXML
    private Label lblError;
    //un hyperlink que llama a la ventana modal viewSingUP
    @FXML
    private Hyperlink hyperSignUP;

    //getter y setter del state SingIN
    public Stage getSignInStage() {
        return signInStage;
    }

    public void setSignInStage(Stage signInStage) {
        this.signInStage = signInStage;
    }

    public void initStage(Parent root, Stage stage) throws IOException {
        LOG.info("Init Stage de la VentanaSignIN");
        //Llamamos al metodo que se encarga del comportamiento del botón
        disableSignInBtn();
        //llamar al método de iniciar sesion cuando pulsas el botón
        btnSignIN.setDefaultButton(true);
        btnSignIN.setOnAction(this::signIN);
        //llamar al método de  registrarse cuando pulsas el hyperEnlace
        hyperSignUP.setOnAction(this::signUp);
        //stage.setOnCloseRequest(this::windowClose);
    }

    /**
     * El usuario podra iniciar la sesion
     *
     * @param event el evento de activacion del boton
     */
    @FXML
    private void signIN(ActionEvent event) {
        //usuario ficticio hasta tener bd
        Signable signable = SignableFactory.getClientImplementation();
        User user = new User();
        user.setLogin(tfUser.getText());
        user.setPassword(tfPassword.getText());
        try {
            User usuario_servidor = signable.signIn(user);
            //user = getClientImplementation().signIn(user);
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader signIn = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
            Parent root;
            root = (Parent) signIn.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene UserViewScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            signInStage = new Stage();
            //definimos como modal la nueva ventana
            signInStage.initModality(Modality.NONE);
            //añadimos la escena en el stage
            signInStage.setScene(UserViewScene);
            //por defecto no podra redimensionarse
            signInStage.setResizable(false);
            //mostramos la ventana modal mientras la actual se queda esperando
            LogOutController controler = (LogOutController) signIn.getController();
            controler.initStage(root);
            //enviamos el usuario devuelto por nuestro servidor para llevarlo
            // a la ventana UserView
            controler.initUser(usuario_servidor);
            signInStage.show();
            panelSignIN.getScene().getWindow().hide();

        } catch (ConnectionNotAvailableException ex) {
            lblError.setText(ex.getMessage());
            LOG.log(Level.SEVERE, "no hay conexiones");
        } catch (LoginNotFoundException ex) {
            lblError.setText(ex.getMessage());
            tfUser.setStyle(" -fx-text-box-border: red ;");
            LOG.log(Level.SEVERE, "error ,el login no coincide con el de la bd");
        } catch (PasswordNotFoundException ex) {
            lblError.setText(ex.getMessage());
            tfPassword.setStyle("-fx-text-box-border: red ;");
            LOG.log(Level.SEVERE, "Error,  el password no coincide con el de la bd");
        } catch (Exception ex) {
            lblError.setText("No se ha podido establecer conexión");
            LOG.log(Level.SEVERE, "No se ha podido establecer conexión");
        }
    }

    /**
     * Abre una ventana modal de signUP que que el usuario se pueda registrar
     *
     * @param event el evento de activacion del enlace
     */
    @FXML
    private void signUp(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/view/ViewSignUp.fxml"));
            Parent root;
            root = (Parent) signUp.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene UserViewScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage logout = new Stage();
            //añadimos la escena en el stage
            logout.setScene(UserViewScene);
            //por defecto no podra redimensionarse
            logout.setResizable(false);
            //cargamos el controlador de la ventana
            SignUpController controller = signUp.getController();
            controller.initStage(root);
            //mostramos la ventana
            logout.show();
            //cerramos la ventana
            panelSignIN.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * El metodo disableSigInBtn se encargara de comportamiento del boton el
     * botón por defecto está deshabilitado mientras no se informen los campos
     * TfUser y Tf Password
     */
    private void disableSignInBtn() {
        LOG.info("El boton esta desabilitado hasta informar los campos");
        btnSignIN.disableProperty().bind(tfUser.textProperty().isEmpty()
                .or(tfPassword.textProperty().isEmpty()
                ));

    }

    /**
     * pide una confirmacion para poder cerrar la ventana.
     *
     * @param event evento asociado al botón cerrar de la barra de título
     */
    private void windowClose(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmacion");
        alert.setContentText("¿Desea cerrar la sesion?");
        Optional<ButtonType> result = alert.showAndWait();

        if (ButtonType.OK != result.get()) {
            event.consume();
        } else {
            LOG.info("se ha salido de la ventana");
        }
    }
}
