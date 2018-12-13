/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.purchaseordermaster;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Orderer extends EMCString {

    /** Creates a new instance of Orderer */
    public Orderer() {
        this.setEmcLabel("Orderer");
        this.setColumnWidth(15);
        //this.setMandatory(true);
        this.setEditable(true);
    }
}
