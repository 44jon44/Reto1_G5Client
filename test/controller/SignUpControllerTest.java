/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import clientapp.ClientApplication;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
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
    @Override
    public void start(Stage stage) throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
    }

    /**
     * Test que comprueba que todos los campos estan habilitados
     */
    @Test
    public void test1_InicioVentana() {
        clickOn("#hyperSignUP");
        verifyThat("#tfFullName", TextInputControlMatchers.hasText(""));
        verifyThat("#tfUser", TextInputControlMatchers.hasText(""));
        verifyThat("#tfEmail", TextInputControlMatchers.hasText(""));
        verifyThat("#tfPassword", TextInputControlMatchers.hasText(""));
        verifyThat("#tfRepeatPassword", TextInputControlMatchers.hasText(""));
        verifyThat("#btnSingUp", isDisabled());

    }

    /**
     * Test para comprobar que el hyperlynk funciona correctamente
     */
    @Test
    public void test2_HyperlinkInicioSesion() {
        clickOn("#hyperSignUP");
        clickOn("#linkLogin");
        verifyThat("#panelSignIN", isVisible());
    }

    /**
     * Test para comprobar que el boton esta desabilitado hasta que se informen
     * todos los campos
     */
    @Test
    public void test3_BotonCrearCuentaDesabilitado() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        verifyThat("#btnSingUp", isDisabled());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        clickOn("#tfUser");
        write("user");
        verifyThat("#btnSingUp", isDisabled());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        clickOn("#tfEmail");
        write("username@gmail.com");
        verifyThat("#btnSingUp", isDisabled());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        clickOn("#tfPassword");
        write("username$1");
        verifyThat("#btnSingUp", isDisabled());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        clickOn("#tfRepeatPassword");
        write("username$1");
        verifyThat("#btnSingUp", isDisabled());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        verifyThat("#btnSingUp", isDisabled());
    }

    /**
     * Test para comprobar que el boton se habilitara cuando se informen todos
     * los campos
     */
    @Test
    public void test4_BotonCrearCuentaHabilitado() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        clickOn("#tfUser");
        write("user");
        clickOn("#tfEmail");
        write("username@gmail.com");
        clickOn("#tfPassword");
        write("username$1");
        clickOn("#tfRepeatPassword");
        write("username$1");
        verifyThat("#btnSingUp", isEnabled());
    }

    /**
     * Test para comprobar que todos los campos son correctos y que pasa a la
     * venta de logout
     */
    @Test
    public void test5_TodoCorrecto() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        clickOn("#tfUser");
        write("diferetuser");
        clickOn("#tfEmail");
        write("username@gmail.com");
        clickOn("#tfPassword");
        write("username$1");
        clickOn("#tfRepeatPassword");
        write("username$1");
        verifyThat("#btnSingUp", isVisible());
        clickOn("#btnSingUp");
        verifyThat("#logOutPane", isVisible());
    }

    /**
     * Test para comprobar que ya existe un usuario con el mismo login
     */
    @Test
    public void test6_UsuarioYaExiste() {
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
        verifyThat("#lblErrorUser", LabeledMatchers.hasText("l"));
    }

    /**
     * Test para comprobar que el formato de email no es valido
     */
    @Test
    public void test7_EmailNoValido() {
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
        verifyThat("#lblErrorEmail", LabeledMatchers.hasText("l"));
    }

    /**
     * Test para comprobar que la contraseña es muy corta, (minimo 8 caracteres)
     */
    @Test
    public void test8_ContraseñaMuyCorto() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("username");
        clickOn("#tfUser");
        write("user");
        clickOn("#tfEmail");
        write("username@gmail.com");
        clickOn("#tfPassword");
        write("u");
        clickOn("#tfRepeatPassword");
        write("u");
        clickOn("#btnSingUp");
        verifyThat("#lblErrorPassword", LabeledMatchers.hasText("l"));
    }

    /**
     * Test para comprobar que los campos de contraseña y repetir contraseña no
     * coinciden
     *
     */
    @Test
    public void test9_RepetirContraseñaIncorrecto() {
        clickOn("#hyperSignUP");
        clickOn("#tfFullName");
        write("user");
        clickOn("#tfUser");
        write("user");
        clickOn("#tfEmail");
        write("username@gmail.com");
        clickOn("#tfPassword");
        write("username$1");
        clickOn("#tfRepeatPassword");
        write("user");
        clickOn("#btnSingUp");
        verifyThat("#lblErrorRepeatPassword", LabeledMatchers.hasText("l"));
    }
}


