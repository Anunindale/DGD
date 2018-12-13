/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.numbersequences;

import emc.datatypes.EMCLong;

/**
 *
 * @author riaan
 */
public class MinNumber extends EMCLong { 

    /** Creates a new instance of MinNumber */
    public MinNumber() {
        this.setEmcLabel("Min Value");
        this.setMandatory(true);
        this.setEditable(true);
    }
}
