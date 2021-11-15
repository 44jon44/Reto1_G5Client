/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import controller.SignInController;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * * @author ibai , jon , alex , markel
 */
public class ClientApplication extends Application {
    private static final Logger LOG = Logger.getLogger(ClientApplication.class.getName());
    /**
     * @param signInStage parametro utilizado para
     * @throws Exception captura y lanza cualquier posible error.
     */
    @Override
    public void start(Stage signInStage) throws Exception {
        LOG.info("Lanzando la ventana viewSignIn");
        //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
        FXMLLoader signIn = new FXMLLoader(getClass().getResource("/view/ViewSignIn.fxml"));
        Parent root = (Parent) signIn.load();
        //Creamos la escena para la ventana SignIn
        Scene signInScene = new Scene(root);
        //Despues Asociamos nuestra primaryStage con la escena 
        signInStage.setScene(signInScene);
       //La ventana no podra redimensioanar
        signInStage.setResizable(false);
        //un llamamiento a la clase SignIncontroler
        SignInController controller = ((SignInController) signIn.getController());
        //inicias el initStage
        controller.initStage(root);
        //Finalmente  mostramos nuestra ventana
        signInStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
