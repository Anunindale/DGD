/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCString;

/**
 * @description : Data type for agingCurrentBinName on DebtorsParameters.
 *
 * @date        : 02 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CurrentBinName extends EMCString {

    /** Creates a new instance of CurrentBinName */
    public CurrentBinName() {
        this.setEmcLabel("Current Bin Name");
        this.setLowerCaseAllowed(true);
    }
}
