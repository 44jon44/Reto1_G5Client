/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 *
 * @author jon
 */
public class LogOutController {

    //un logger que nos informara mediante la terminal
    private static final Logger LOG = Logger.getLogger(LogOutController.class.getName());
    //declaramos los stage de la  ventanas que vamos a manipular   
    private Stage signInStage;
    // Button que cierra la aplicación
    @FXML
    private Button btnSalir;
    //botón que cierra la sesión y abre la ventana SignIn
    @FXML
    private Button btnCerrarSesion;
    //Label que muestra un saludo al usuario que ha iniciado sesión
    @FXML
    private Label lblNombre;
    
    @FXML
    private Label lblEmail;
    /**
     * Método que inicializa la ventana  
     * @param root 
     */
    public void initStage(Parent root) {
        LOG.info("Se ha entrado en la ventana UserView");
        btnSalir.setOnAction(this::exit);
        btnCerrarSesion.setOnAction(this::logOut);
    }
    /**
     * El usuario cierra la aplicación
     * @param event evento de pulsación del botón salir
     */
    @FXML
    public void exit(ActionEvent event) {
        LOG.info("Se ha pulsado el botón Salir");
        //Se crea objeto Alert de tipo confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //Se añaden los textos a la ventana alert
        alert.setTitle("Exit");
        alert.setHeaderText("Confirmación");
        alert.setContentText("¿Desea cerrar la aplicación?");
        //La ventana se hace no redimensionable
        alert.setResizable(false);
        //La ventana se muestra
        //Se instancia un objeto contendor que recoge un valor segun 
        //lo que se seleccione 
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);
        //Condicion if que se ejcuta si se ha seleccionado aceptar
        if (button == ButtonType.OK) {
            //Cierra la ventana
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.close();
        }
    }
    /**
     * Metodo que cierra la ventana logOut y vuelve a la de signIN 
     * @param event evento de pulsacion del boton cerrar sesion
     */
    @FXML
    public void logOut(ActionEvent event) {
        LOG.info("Se ha pulsado el botón Cerrar Sesión");
        //Se crea objeto Alert de tipo confirmacion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //Se añaden los textos a la ventana alert
        alert.setTitle("Log Out");
        alert.setHeaderText("Confirmación");       
        alert.setContentText("¿Desea cerrar la sesión?");
        //La ventana se hace no redimensionable
        alert.setResizable(false);
        //La ventana se muestra
        //Se instancia un objeto contendor Optional, 
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                //Se cierra la ventana userView
                Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
                stage.close();
                //Se llama al metodo que abre la ventana SignIn
                openSignIn();
            } catch (IOException ex) {
                Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Método que abre la ventana signIn
     * 
     * @throws IOException 
     */
    private void openSignIn() throws IOException {
        LOG.info("Se ha pulsado el botón  Cerrar Sesión");
        //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewSignIn.fxml"));
        Parent root = (Parent) loader.load();
        //Creamos la escena para la ventana SignIn
        Scene SignInScene = new Scene(root);
        //Creamos un nuevoStage
        signInStage = new Stage();
        //La ventana sera modal
        signInStage.initModality(Modality.APPLICATION_MODAL);
        //Asociamos el stage con nuestra escena
        signInStage.setScene(SignInScene);
        //La ventana no se podra redimensonar
        signInStage.setResizable(false);
        //La ventana se muestra
        signInStage.showAndWait();
    }
    /**
     * Método que muestra el nombre completo y el email del usuario que ha iniciado sesión
     * @param user objeto que es devuelto de la base de datos
     */
    public void initUser(User user){  
        lblNombre.setText(lblNombre.getText() + user.getFullName());
        lblEmail.setText(lblEmail.getText() + user.getEmail()); 
    }
}


