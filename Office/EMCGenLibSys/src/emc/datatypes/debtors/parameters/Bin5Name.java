/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCString;

/**
 * @description : Data type for agingBin5Name on DebtorsParameters.
 *
 * @date        : 02 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Bin5Name extends EMCString {

    /** Creates a new instance of Bin5Name */
    public Bin5Name() {
        this.setEmcLabel("Bin 5 Name");
        this.setLowerCaseAllowed(true);
    }
}
