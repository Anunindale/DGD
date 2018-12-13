/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.courier;

import emc.datatypes.EMCString;

/**
 * @description : Data type for courierContactEmail on DebtorsCourier.
 *
 * @date        : 01 Aug 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CourierContactEmail extends EMCString {

    /** Creates a new instance of CourierContactEmail */
    public CourierContactEmail() {
        this.setEmcLabel("Email");
    	this.setColumnWidth(104);
        this.setMandatory(false);
    }
}
