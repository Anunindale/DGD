/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.salesarea;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class SalesArea extends EMCString {

    public SalesArea() {
        this.setEmcLabel("Sales Area");
        this.setMandatory(true);
    }
}
