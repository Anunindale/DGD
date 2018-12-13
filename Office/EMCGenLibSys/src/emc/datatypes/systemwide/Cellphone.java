/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Cellphone extends EMCString {

    /** Creates a new instance of Telephone */
    public Cellphone() {
        this.setEmcLabel("Cellphone");
        this.setEditable(true);
        this.setStringSize(20);
    }
}