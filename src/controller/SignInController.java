/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.*;
import java.io.IOException;
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
    // botón que inicia la sesión
    @FXML
    private Button btnSignIN;
    // un  label que visualiza los diferentes errores
    @FXML
    private AnchorPane panelSignIN;
    @FXML
    private Label lblError;
    //un hyperlink que llama a la ventana viewSingUp
    @FXML
    private Hyperlink hyperSignUP;

    //getter y setter del state SingIn
    public Stage getSignInStage() {
        return signInStage;
    }
    
    public void setSignInStage(Stage signInStage) {
        this.signInStage = signInStage;
    }
    
    public void initStage(Parent root,Stage stage) throws IOException {
        LOG.info("Entrando en la ventana ViewSignIn");
        //Llamamos al metodo que se encarga del comportamiento del botón
        disableSignInBtn();
        //llamar al método de iniciar sesión cuando pulsas el botón
        btnSignIN.setOnAction(this::signIn);
        //llamar al método de  resgistrarse cuando pulsas el hiperenlace
        hyperSignUP.setOnAction(this::signUp);
        //handler del evento de cerrar la ventana desde el botón cerrar de la barra de título
        stage.setOnCloseRequest(this::windowClose);
    }

    /**
     * Handler del evento de pulsar el botón de "Iniciar Sesión"
     *
     * @param event el evento de activación del botón
     */
    @FXML
    private void signIn(ActionEvent event) {
        LOG.info("Se ha pulsado el botón de Iniciar Sesión");
        //usario ficticio hasta tener bd
        Signable signable = SignableFactory.getClientImplementation();
        User user = new User();
        user.setLogin(tfUser.getText());
        user.setPassword(tfPassword.getText());
        try {
            User usuarioServidor = signable.signIn(user);
            //user = getClientImplementation().signIn(user);
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader signIn = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
            Parent root;
            root = (Parent) signIn.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene UserViewScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            signInStage = new Stage();
            //indicamos que la nueva ventana no es modal
            signInStage.initModality(Modality.NONE);
            //añadimos la escena en el stage
            signInStage.setScene(UserViewScene);
            //por defecto no podrá redimensionarse
            signInStage.setResizable(false);
            //mostramos la ventana de UserView
            LogOutController controler = (LogOutController) signIn.getController();
            controler.initStage(root);
            //enviamos el usuario devuelto por nuestro servidor para llevarlo
            // a la ventana UserView
            controler.initUser(usuarioServidor);
            signInStage.show();
            panelSignIN.getScene().getWindow().hide();
            
        } catch (ConnectionNotAvailableException ex) {
            lblError.setText(ex.getMessage());
            LOG.log(Level.SEVERE, ex.getMessage());
        } catch (LoginNotFoundException ex) {
            lblError.setText(ex.getMessage());
            LOG.log(Level.SEVERE, ex.getMessage());
        } catch (PasswordNotFoundException ex) {
            lblError.setText(ex.getMessage());
            LOG.log(Level.SEVERE, ex.getMessage());
        } catch (Exception ex) {
            lblError.setText("No se ha podido establecer conexión");
            LOG.log(Level.SEVERE, "No se ha podido establecer conexión");
        }
    }

    /**
     * Abre la ventana ViewSignUp para que el usuario se pueda registrar
     *
     * @param event el evento de activación del enlace
     */
    @FXML
    private void signUp(ActionEvent event) {
        LOG.info("Se ha pulsado el hiperenlace Regístrate");
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
            //por defecto no podrá redimensionarse
            logout.setResizable(false);
            //cargamos el controlador de la ventana
            SignUpController controller = signUp.getController();
            controller.initStage(root);
            //mostramos la ventana modal mientras la actual se queda esperando
            logout.show();
            //cerramos la ventana
            panelSignIN.getScene().getWindow().hide();
        } catch (IOException ex) {
           LOG.log(Level.SEVERE, "Se ha producido un error al cargar el fichero FXML");
        }
    }

    /**
     * Handler del evento de pulsar el botón "Iniciar Sesión".
     * El botón por defecto estará deshabilitado mientras no se informen los campos "Usuario" y "Contraseña"
     */
    private void disableSignInBtn() {
        LOG.info("El botón está desabilitado hasta que se informen los campos");
        btnSignIN.disableProperty().bind(tfUser.textProperty().isEmpty()
                .or(tfPassword.textProperty().isEmpty()
                ));
    }
    /**
     * Handler del evento de cerrar la ventana desde el botón cerrar de la barra de título
     * @param event Evento de pulsar el botón cerrara de la barra de título
     */
    private void windowClose(WindowEvent event){
        LOG.info("Se ha pulsado el botón cerrar de la barra de título de la ventana");
        //creamos una alerta de tipo confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(("Confirmación"));
        alert.setContentText("¿Desea cerrar la ventana?");
        Optional<ButtonType> result = alert.showAndWait();
        //si se ha pulsado el botón de aceptar cerramos la ventana
        if (result.get() == ButtonType.YES) {
            LOG.info("Cerrando ventana ViewSignIn");
            event.consume(); 
        }else{
            LOG.info("Se ha cancelado el cierre de la ventana ViewSignIN");
        }
    }   
}
