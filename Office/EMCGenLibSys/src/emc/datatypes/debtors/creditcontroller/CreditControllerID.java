/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditcontroller;

import emc.datatypes.EMCString;

/**
 * @description : Datatype for creditContollerId on DebtorsCreditController.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditControllerID extends EMCString {

    /** Creates a new instance of CreditControllerID */
    public CreditControllerID() {
        this.setEmcLabel("Credit Controller");
        this.setMandatory(true);
    }
}
