/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.basket;

import emc.datatypes.EMCString;

/**
 *
 * @author asd_admin
 */
public class SessionId extends EMCString {
    public SessionId(){
        this.setEmcLabel("Session ID");
        this.setMandatory(true);
    }
}
