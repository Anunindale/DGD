/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCString;

/**
 * @description : Data type for agingBin2Name on DebtorsParameters.
 *
 * @date        : 02 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Bin2Name extends EMCString {

    /** Creates a new instance of Bin2Name */
    public Bin2Name() {
        this.setEmcLabel("Bin 2 Name");
        this.setLowerCaseAllowed(true);
    }
}
