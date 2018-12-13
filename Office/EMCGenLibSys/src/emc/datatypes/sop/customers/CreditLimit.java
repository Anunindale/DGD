/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.customers;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class CreditLimit extends EMCDouble{

    public CreditLimit() {
        this.setEmcLabel("Credit Limit");
        this.setMinValue(0);
        this.setNumberDecimalsDisplay(2);
        this.setNumberDecimalsInput(2);
        this.setMandatory(true);
    }

}
