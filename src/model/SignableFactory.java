/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * @author ibai ,  jon 
 */
public class SignableFactory {
   /**
     * @return  la implementacion del lado cliente
    */ 
    public static Signable getClientImplementation () {     
        return new   SignableImplementation();
     
}
}
