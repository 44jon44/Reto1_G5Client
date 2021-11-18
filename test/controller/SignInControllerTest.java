package controller;

import clientapp.ClientApplication;
import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;

import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

/**
 * @author ibai y jon
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTest extends ApplicationTest {

    /**
     * esta metodo se ejecutara por cada test iniciado
     *
     * @throws TimeoutException excepcion lanzada
     */
    @Before
    public void openWindow() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
    }

    /**
     * comprueba que la ventatana se inicia corrrectamente
     */
    @Test
    public void test1_InicioVentana() {
        verifyThat("#panelSignIN", isVisible());
        verifyThat("#btnSignIN", isDisabled());
        verifyThat("#lblError", isEnabled());
        verifyThat("#tfUser", TextInputControlMatchers.hasText(""));
        verifyThat("#tfPassword", TextInputControlMatchers.hasText(""));
        verifyThat("#hyperSignUP", isEnabled());
    }

    /**
     * Comprueba cuando se llenen los 2 campos el boton iniciar sesion se
     * habilite
     */
    @Test
    public void test2_BtnHabilitado() {
        clickOn("#tfUser");
        write("ibai");
        clickOn("#tfPassword");
        write("1");
        verifyThat("#btnSignIN", isEnabled());
    }

    /**
     * salta el mensaje de la excepcion al escribir mal login
     */
    @Test
    public void test3_LoginNotFoundException() {
        clickOn("#tfUser");
        write("IncorrectUser");
        clickOn("#tfPassword");
        write("abcd*1234");
        clickOn("#btnSignIN");
        verifyThat("#lblError", LabeledMatchers.hasText("El usuario introducido no existe"));

    }

    /**
     * salta el mensaje de la excepcion al escribir mal la contraseña
     */
    @Test
    public void test4_PasswordNotFoundException() {
        clickOn("#tfUser");
        write("ibai");
        clickOn("#tfPassword");
        write("incorrectPassword");
        clickOn("#btnSignIN");
        verifyThat("#lblError", LabeledMatchers.hasText("La contraseña no es correcta"));

    }

    /**
     * comprueba que el boton se queda desabilidado si no se rellenan los campos
     * tflogin y tfpassword
     */
    @Test
    public void test5_BtnDesabilitado() {
        clickOn("#tfUser");
        write("user");
        verifyThat("#btnSignIN", isDisabled());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        clickOn("#tfPassword");
        write("password");
        verifyThat("#btnSignIN", isDisabled());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        verifyThat("#btnSignIN", isDisabled());
    }

    /**
     * comprueba un inicio de sesion correcto verificando que salga la ventana
     * del login.
     */
    @Test
    public void test6_CorrectLogin() {
        clickOn("#tfUser");
        write("ibai");
        clickOn("#tfPassword");
        write("Abcd*1234");
        clickOn("#btnSignIN");
        verifyThat("#logOutPane", isVisible());
        clickOn("#btnCerrarSesion");
    }

    /**
     * Comprueba la navegavilidad de la ventana iniciar sesion a la ventana
     * registrar
     */
    @Test
    public void test7_Hiperlink() {
        clickOn("#hyperSignUP");
        verifyThat("#paneSignUp", isVisible());
    }

   // @Test
    public void test8_server_error() {
        clickOn("#tfUser");
        write("ibai");
        clickOn("#tfPassword");
        write("1");
        clickOn("#btnSignIN");
        verifyThat("#lblError", LabeledMatchers.hasText("No se ha podido establecer conexión"));
    }

   // @Test
    public void test9_max_connections() {
        clickOn("#tfUser");
        write("ibai");
        clickOn("#tfPassword");
        write("1");
        clickOn("#btnSignIN");
        verifyThat("#lblError", LabeledMatchers.hasText("No se ha podido establecer conexión"));
    }

}

