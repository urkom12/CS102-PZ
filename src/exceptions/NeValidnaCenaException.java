/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Veljko
 */
public class NeValidnaCenaException extends Exception {

    public NeValidnaCenaException(String poruka) {
        super(poruka);
    }

    public NeValidnaCenaException() {
    }

}
