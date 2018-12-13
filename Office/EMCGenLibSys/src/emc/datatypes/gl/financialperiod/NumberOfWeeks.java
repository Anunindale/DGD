/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.financialperiod;

import emc.datatypes.EMCInt;

/**
 *
 * @author rico
 */
public class NumberOfWeeks extends EMCInt{
    public NumberOfWeeks(){
        this.setEditable(false);
        this.setEmcLabel("Number of Weeks");
        this.setColumnWidth(30);
    }
}
