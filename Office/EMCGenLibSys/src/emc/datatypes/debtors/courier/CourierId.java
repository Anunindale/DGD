/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.courier;

import emc.datatypes.EMCString;

/**
 * @description : Data type for courierId on DebtorsCourier.
 *
 * @date        : 07 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CourierId extends EMCString {

    /** Creates a new instance of CourierId */
    public CourierId() {
        this.setEmcLabel("Courier");
    	this.setColumnWidth(59);
        this.setMandatory(true);
    }
}
