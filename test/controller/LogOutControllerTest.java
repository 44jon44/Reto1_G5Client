package controller;

import clientapp.ClientApplication;
import java.util.concurrent.TimeoutException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author jon e ibai
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogOutControllerTest extends ApplicationTest {

    /**
     * Metodo que abre la ventana al princio de la clase
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void openWindow() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);

    }
    /**
     * Metodo que verifica que los componentes se inicializan correctamente
     */
    @Test
    public void test1_InicioVentana() {
        clickOn("#tfUser");
        write("ibai");
        clickOn("#tfPassword");
        write("abcd*1234");
        clickOn("#btnSignIN");
        verifyThat("#btnCerrarSesion", isEnabled());
        verifyThat("#btnSalir", isEnabled());
    }

    /**
     * Metodo que comprueba que pulsar el boton CerrarSesion y cancelar no se
     * cierra sesion
     */

    @Test
    public void test2_LogOut() {
        clickOn("#btnCerrarSesion");
        clickOn("Cancelar");
        verifyThat("#logOutPane", isVisible());
    }

    /**
     * Metodo que comprueba que pulsar el boton Salir y cancelar no se cierra
     * sesion
     */
    @Test
    public void test3_salir() {
        clickOn("#btnSalir");
        clickOn("Cancelar");
        verifyThat("#logOutPane", isVisible());
    }

    /**
     * Metodo que comprueba que se cierra la sesion y se abre la ventana de
     * signIN
     */

    @Test
    public void test4_LogOut() {
        clickOn("#btnCerrarSesion");
        clickOn("Aceptar");
        verifyThat("#panelSignIN", isVisible());
    }
}
