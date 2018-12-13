/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCString;

/**
 * @description : Data type for agingBin1Name on DebtorsParameters.
 *
 * @date        : 02 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Bin1Name extends EMCString {

    /** Creates a new instance of Bin1Name */
    public Bin1Name() {
        this.setEmcLabel("Bin 1 Name");
        this.setLowerCaseAllowed(true);
    }
}
