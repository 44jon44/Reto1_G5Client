/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import clientapp.ClientApplication;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
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
 *
 * @author alex
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Before
    public void start() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
    }

    /**
     * Test que comprueba que todos los campos estan habilitados
     */
    @Test
    public void testA_InicioVentana() {
        clickOn("#hyperSignUP");
        verifyThat("#tfFullName", TextInputControlMatchers.hasText(""));
        verifyThat("#tfUser", TextInputControlMatchers.hasText(""));
        verifyThat("#tfEmail", TextInputControlMatchers.hasText(""));
        verifyThat("#tfPassword", TextInputControlMatchers.hasText(""));
        verifyThat("#tfRepeatPassword", TextInputControlMatchers.hasText(""));
        verifyThat("#btnSingUp", isEnabled());

    }

    /**
     * Test para comprobar que el hyperlynk funciona correctamente
     */
    @Test
    public void testB_HyperlinkInicioSesion() {
        clickOn("#hyperSignUP");
        //clickOn("#hyperSignIn");
        verifyThat("#paneSignUp", isVisible());
    }

    /**
     * Test para comprobar que todos los campos son correctos y que pasa a la
     * venta de logout
     */
    @Test
    public void testC_TodoCorrecto() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        clickOn("#tfUser");
        write("diferetuser");
        clickOn("#tfEmail");
        write("username@gmail.com");
        clickOn("#tfPassword");
        write("Username$1");
        clickOn("#tfRepeatPassword");
        write("Username$1");
        verifyThat("#btnSingUp", isVisible());
        clickOn("#btnSingUp");
        verifyThat("#logOutPane", isVisible());
    }

    /**
     * Test para comprobar que ya existe un usuario con el mismo login
     */
    @Test
    public void testD_UsuarioYaExiste() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        clickOn("#tfUser");
        write("ibai");
        clickOn("#tfEmail");
        write("username@gmail.com");
        clickOn("#tfPassword");
        write("username$1");
        clickOn("#tfRepeatPassword");
        write("username$1");
        clickOn("#btnSingUp");
        verifyThat("#lblErrorUser", LabeledMatchers.hasText("El usuario ya existe"));
    }

    /**
     * Test para comprobar que el formato de email no es valido
     */
    @Test
    public void testE_EmailNoValido() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        clickOn("#tfUser");
        write("user");
        clickOn("#tfEmail");
        write("useremail");
        clickOn("#tfPassword");
        write("username$1");
        clickOn("#tfRepeatPassword");
        write("username$1");
        clickOn("#btnSingUp");
        verifyThat("#lblErrorEmail", TextInputControlMatchers.hasText("Email inválido"));
    }

    /**
     * Test para comprobar que la contraseña es muy corta, (minimo 8 caracteres)
     */
    @Test
    public void testF_ContraseñaMuyCorto() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        clickOn("#tfUser");
        write("user");
        clickOn("#tfEmail");
        write("username@gmail.com");
        clickOn("#tfPassword");
        write("u");
        
        clickOn("#btnSingUp");
        verifyThat("#lblErrorPassword", TextInputControlMatchers.hasText("Longitud mínima de 8"));
    }

    /**
     * Test para comprobar que los campos de contraseña y repetir contraseña no
     * coinciden
     *
     */
    @Test
    public void testG_RepetirContraseñaIncorrecto() {
        clickOn("#hyperSignUP");
        
        clickOn("#tfPassword");
        write("username$1");
        clickOn("#tfRepeatPassword");
        write("user");
        clickOn("#btnSingUp");
        verifyThat("#lblErrorRepeatPassword", LabeledMatchers.hasText("No coinciden"));
    }


 @Test
    public void testH_ValidarEmailCorrecto() {
        clickOn("#hyperSignUP");
        clickOn("#tfEmail");
        write("jonmadi21@gm.ail.com");
        clickOn("#tfFullName");
        verifyThat("#lblErrorEmail", TextInputControlMatchers.hasText(""));
    }

    @Test
    public void testI_ValidarEmailCorto() {
        clickOn("#hyperSignUP");
        clickOn("#tfEmail");
        write("jonmadi21@gmail.c");
        verifyThat("#lblErrorEmail", TextInputControlMatchers.hasText("Email inválido"));
    }

    @Test
    public void testJ_ValidarEmailDobleArroba() {
        clickOn("#hyperSignUP");
        clickOn("#tfEmail");
        write("jonmadi21@@gmail.com");
        verifyThat("#lblErrorEmail", TextInputControlMatchers.hasText("Email inválido"));
    }
}
