/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import exceptions.ConnectionNotAvailableException;
import exceptions.LoginExistException;
import exceptions.LoginNotFoundException;
import exceptions.PasswordNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author ibai y jon
 */
public class SignableImplementation implements Signable {

    private static final Logger LOG = Logger.getLogger(SignableImplementation.class.getName());
    private final static String IP = ResourceBundle.getBundle("model.config").getString("SERVER_IP");
    private final static int PORT = Integer.valueOf(ResourceBundle.getBundle("model.config").getString("SERVER_PORT"));
    private Message msg;
    private Socket socket;
    
    //metodo que 
    public Message communicate(Message msg) throws IOException {
        socket = new Socket(IP, PORT);
        LOG.info("Metodo  run() ");
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(msg);
            msg = (Message) ois.readObject();
        } catch (Exception e) {
            LOG.info("Error de LECTURA o ESCRITURA del objeto Message");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                LOG.info("Error al cerrar el ois o oos");
            }
        }
        return msg; 
    } 

    @Override
    public User signIn(User user ) throws LoginNotFoundException, ConnectionNotAvailableException, PasswordNotFoundException, Exception {
        //Iniciamos el mensaje que lanzaremos al server
        msg = new Message(user, Order.SIGN_IN);
        Message ret = communicate(msg);
        User userRet = ret.getUser();
        if ( ret.getOrder() == Order.LOGIN_NOT_FOUND )
            throw new LoginNotFoundException();
        else if ( ret.getOrder() == Order.PASSWORD_NOT_FOUND )
            throw new PasswordNotFoundException();
        return userRet;
    }

    @Override
    public boolean signUp(User user) throws LoginExistException, ConnectionNotAvailableException, Exception {
        msg = new Message(user, Order.SIGN_UP);
        Message res = communicate(msg);
        socket.close();
        if(res.getOrder() == Order.LOGIN_EXIST)
            throw new LoginExistException();
        return res.getOrder() == Order.OK;
    }


}
