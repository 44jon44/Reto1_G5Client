/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.ConnectionNotAvailableException;
import exceptions.LoginExistException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Signable;
import model.SignableFactory;
import model.User;

/**
 *
 * @author Alex Hurtado
 */
public class SignUpController {

    //Logger del controlador de la ventana "ViewSignIn"
    private static final Logger LOG = Logger.getLogger(SignUpController.class.getName());

    private Stage stageSignUp;
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
    private Hyperlink hyperSignIn;
    //Button para registrarse
    @FXML
    private Button btnSingUp;
    //booleanos que indican si los campos son válidos tras las comprobaciones oportunas
    private boolean tfUserIsValid = false;
    private boolean tfEmailIsValid = false;
    private boolean tfFullNameIsValid = false;
    private boolean tfPasswordIsValid = false;
    private boolean tfRepeatPasswordIsValid = false;
    private boolean hasANumber = false;
    private boolean hasAnLowerLetter = false;
    private boolean hasAnUppderLetter = false;
    private boolean hasASpecialChar = false;

    /**
     * Método que carga el estado inicial de ViewSignUp
     *
     * @param root
     * @throws IOException
     */
    public void initStage(Parent root) throws IOException {
        LOG.info("Entrando en la ventana ViewSignUp");
        tfUser.requestFocus();
        //Establecemos los handlers de los eventos de la ventana
        tfUser.setPromptText("usuario");
        tfUser.focusedProperty().addListener(this::tfUserFocusChanged);
        tfUser.textProperty().addListener(this::tfUserTextChanged);
        tfEmail.setPromptText("ejemplo@dominio.com");
        tfEmail.focusedProperty().addListener(this::tfEmailFocusChanged);
        tfEmail.textProperty().addListener(this::tfEmailTextChanged);
        tfFullName.setPromptText("Jon Doe");
        tfFullName.focusedProperty().addListener(this::tfFullNameFocusChanged);
        tfFullName.textProperty().addListener(this::tfFullNameTextChanged);
        tfPassword.setPromptText("P4$$w0rd");
        tfPassword.focusedProperty().addListener(this::tfPasswordFocusChanged);
        tfPassword.textProperty().addListener(this::tfPasswordTextChanged);
        tfRepeatPassword.setPromptText("P4$$w0rd");
        tfRepeatPassword.textProperty().addListener(this::tfRepeatPasswordTextChanged);
        //Handler del evento de pulsar el boton de "Registrarse"
        btnSingUp.setOnAction(this::signUp);
        //llamar al metodo de  "Iniciar Sesión" cuando pulsas el hiperenlace
        hyperSignIn.setOnAction(this::signIn);
    }

    @FXML
    private void signIn(ActionEvent event) {
        try
        {
            LOG.info("Se ha pulsado el hiperenlace Login");
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/view/ViewSignIn.fxml"));
            Parent root;
            root = (Parent) signUp.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene userViewScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage logout = new Stage();
            //definimos como modal la nueva ventana
            //logout.initModality(Modality.APPLICATION_MODAL);
            //añadimos la escena en el stage
            logout.setScene(userViewScene);
            //por defecto no podra redimensionarse
            logout.setResizable(false);
            //
            SignInController controller = signUp.getController();
            controller.initStage(root, logout);
            //mostramos la ventana modal mientras la actual se queda esperando
            logout.show();
            //cerramos la ventana
            Stage stage = (Stage) btnSingUp.getScene().getWindow();
            stage.hide();
        } catch (IOException ex)
        {
            LOG.log(Level.SEVERE, "Se ha producido un error al cargar el fichero FXML");
        }
    }

    private void signUp(ActionEvent event) {
        LOG.info("Se ha pulsado el botón de Registrarse");
        validateTfRepeatPassword(tfPassword.getText(), tfRepeatPassword.getText());
        //comprobamos si todos los campos son correctos y si lo son registramos al usuario
        if (validFields())
        {
            Signable signable = SignableFactory.getClientImplementation();
            User userSignUp = new User(tfUser.getText(), tfEmail.getText(), tfFullName.getText(), tfPassword.getText());
            try
            {
                boolean signUpCorrect = signable.signUp(userSignUp);
                if (signUpCorrect)
                {
                    LOG.info("Volviendo a la venta ViewSignIn");
                    signIn(event);
                }
            } catch (LoginExistException ex)
            {
                lblError.setText(ex.getMessage());
                LOG.log(Level.SEVERE, ex.getMessage());
            } catch (ConnectionNotAvailableException ex)
            {
                lblError.setText(ex.getMessage());
                LOG.log(Level.SEVERE, ex.getMessage());
            } catch (Exception ex)
            {
                lblError.setText("No se ha podido establecer conexión");
                LOG.log(Level.SEVERE, "No se ha podido establecer conexión");
            }
        } else
        {
            showFieldErrors();
            transferFocusFirstInvalidField();
        }
    }

    private void tfPasswordFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        //lblErrorPassword.setTextFill(Paint.valueOf("RED"));
        if (oldValue)
        {//foco perdido 
            tfPasswordIsValid = validateTfPassword(tfPassword.getText());
            //si la contraseña no es válida...
            if (!tfPasswordIsValid)
            {
                tfPassword.setStyle("-fx-text-inner-color: red;");
                showlblErrorPasswordMessages(tfPassword.getText());
            }
        } else if (newValue)
        {//foco ganado
            if (tfPasswordIsValid)
            {
                lblErrorPassword.setText("");
                lblErrorRepeatPassword.setText("");
            }

        }
    }

    private void tfFullNameFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        //lblErrorFullName.setTextFill(Paint.valueOf("RED"));
        if (oldValue)
        {//foco perdido 
            tfFullNameIsValid = validateTfFullName(tfFullName.getText());
            if (!tfFullNameIsValid)
            {
                tfFullName.setStyle("-fx-text-inner-color: red;");
                showlblErrorFullNameMessages(tfFullName.getText());
            }
        } else if (newValue)
        {//foco ganado

        }
    }

    private void tfEmailFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue)
        {//foco perdido 
            tfEmailIsValid = validateTfEmail(tfEmail.getText());
            if (!tfEmailIsValid)
            {
                tfEmail.setStyle("-fx-text-inner-color: red;");
                showlblErrorEmailMessages(tfEmail.getText());
            }
        } else if (newValue)
        {//foco ganado

        }
    }

    private void tfUserFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        LOG.info("Dentro de tfUserFocusChanged");
        if (oldValue)
        {//foco perdido 
            tfUserIsValid = validateTfUser(tfUser.getText());
            if (!tfUserIsValid)
            {
                showlblErrorUserMessages(tfUser.getText());
            }
            LOG.info(tfUser.getText());
        } else if (newValue)
        {//foco ganado

        }
    }

    //getter de stageSignUp
    public Stage getStageSignUp() {
        return stageSignUp;
    }

    //setter de 
    public void setStageSignUp(Stage stageSignUp) {
        this.stageSignUp = stageSignUp;
    }

    private boolean validateTfUser(String user) {
        return Pattern.matches("\\b[a-zA-Z][a-zA-Z0-9]+\\b", user);
    }

    private boolean validateTfEmail(String email) {
        return Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-@][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", email);
    }

    private boolean validateTfFullName(String fullName) {
        return Pattern.matches("\\b\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}", fullName);
    }

    private boolean validateTfPassword(String password) {
        return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@()/*#$%^&+=!¡¿?])(?=\\S+$).{8,}$", password);
    }

    private boolean validFields() {
        return tfUserIsValid && tfEmailIsValid && tfFullNameIsValid && tfPasswordIsValid && tfRepeatPasswordIsValid;
    }

    private void transferFocusFirstInvalidField() {
        if (!tfUserIsValid)
        {
            tfUser.requestFocus();
        } else if (!tfEmailIsValid)
        {
            tfEmail.requestFocus();
        } else if (!tfFullNameIsValid)
        {
            tfFullName.requestFocus();
        } else if (!tfPasswordIsValid)
        {
            tfPassword.requestFocus();
        } else if (!tfRepeatPasswordIsValid)
        {
            tfRepeatPassword.requestFocus();
        }
    }

    private void tfRepeatPasswordTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length())
        {
            lblErrorRepeatPassword.setText("");
            lblErrorPassword.setText("");
        }
    }

    private void tfPasswordTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length())
        {
            lblErrorPassword.setText("");
            lblErrorRepeatPassword.setText("");
            tfPassword.setStyle("-fx-text-inner-color: black;");
        }
        showlblErrorPasswordMessages(tfPassword.getText());
    }

    private void tfFullNameTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length())
        {
            lblErrorFullName.setText("");
            tfFullName.setStyle("-fx-text-inner-color: black;");
        }
    }

    private void tfEmailTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length())
        {
            lblErrorEmail.setText("");
            tfEmail.setStyle("-fx-text-inner-color: black;");
        }
    }

    private void tfUserTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length())
        {
            lblErrorUser.setText("");
            tfUser.setStyle("-fx-text-inner-color: black;");
        }
    }

    private void showFieldErrors() {
        if (!tfUserIsValid)
        {
            showlblErrorUserMessages(tfUser.getText());
        }
        if (!tfEmailIsValid)
        {
            showlblErrorEmailMessages(tfEmail.getText());
        }
        if (!tfFullNameIsValid)
        {
            showlblErrorFullNameMessages(tfFullName.getText());
        }
        if (!tfPasswordIsValid)
        {
            showlblErrorPasswordMessages(tfPassword.getText());
        }
        if (!tfRepeatPasswordIsValid)
        {
            showlblErrorRepeatPasswordMessages(tfRepeatPassword.getText());
        }
    }

    private void showlblErrorUserMessages(String user) {
        tfUser.setStyle("-fx-text-inner-color: red;");
        if (user.contains(" "))
        {
            lblErrorUser.setText("No puede contener espacios");
        } else if (user.trim().length() == 0)
        {
            lblErrorUser.setText("Campo obligatorio");
        } else if (user.trim().length() == 1)
        {
            lblErrorUser.setText("Longitud mínima 2");
        } else
        {
            lblErrorUser.setText("Usuario inválido");
        }
    }

    private void showlblErrorEmailMessages(String email) {
        if (email.trim().length() == 0)
        {
            lblErrorEmail.setText("Campo obligatorio");
        } else
        {
            lblErrorEmail.setText("Email inválido");
        }
    }

    private void showlblErrorFullNameMessages(String fullName) {
        if (fullName.trim().length() == 0)
        {
            lblErrorFullName.setText("Campo obligatorio");
        } else
        {
            lblErrorFullName.setText("Nombre inválido");
        }
    }

    private void showlblErrorPasswordMessages(String password) {
        if (password.trim().length() == 0)
        {
            lblErrorPassword.setText("Campo obligatorio");
        } else if (password.trim().length() < 8)
        {
            lblErrorPassword.setText("Longitud mínima de 8");
        } else if (password.trim().length() >= 8)
        {
            hasANumber = Pattern.matches(".*[0-9]{1,}.*", password);
            hasAnLowerLetter = Pattern.matches(".*[a-z]{1,}.*", password);
            hasAnUppderLetter = Pattern.matches(".*[A-Z]{1,}.*", password);
            hasASpecialChar = Pattern.matches(".*[@()/*#$%^&+=!¡¿?]{1,}.*", password);

            if (!hasAnLowerLetter)
            {
                lblErrorPassword.setText("Falta una letra minúscula");
            }else if (!hasAnUppderLetter)
            {
                lblErrorPassword.setText("Falta una letra mayúscula");
            }else if (!hasASpecialChar)
            {
                lblErrorPassword.setText("Falta un caracter especial");
            }else if (!hasANumber)
            {
                lblErrorPassword.setText("Falta número");
            }else{
                lblErrorPassword.setText("");
            }
        }
    }

    private void showlblErrorRepeatPasswordMessages(String repPassword) {
        if (tfPasswordIsValid || tfRepeatPassword.getText().trim().length() > 0)
        {
            lblErrorRepeatPassword.setText("No coinciden");
        } else if (repPassword.trim().length() == 0)
        {
            lblErrorRepeatPassword.setText("Campo obligatorio");
        }
    }

    private void validateTfRepeatPassword(String password, String repPassword) {
        if (tfPasswordIsValid)
        {
            tfRepeatPasswordIsValid = password.equals(repPassword);
            //si las contraseñas no coinciden...
            if (!tfRepeatPasswordIsValid)
            {
                lblErrorRepeatPassword.setText("No coinciden");
            }
        }
    }
}
