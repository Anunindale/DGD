/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCString;

/**
 * @description : Data type for debtorsAgingMode on DebtorsParameters.
 *
 * @date        : 08 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsAgingMode extends EMCString {

    /** Creates a new instance of DebtorsAgingMode */
    public DebtorsAgingMode() {
        this.setEmcLabel("Unallocated Credit Ageing");
    }
}
