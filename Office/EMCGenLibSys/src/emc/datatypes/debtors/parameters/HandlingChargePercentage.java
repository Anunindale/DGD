/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCDouble;

/**
 * @description : Data type for handlingChargePercentage on DebtorsParameters.
 *
 * @date        : 22 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class HandlingChargePercentage extends EMCDouble {

    /** Creates a new instance of HandlingChargePercentage */
    public HandlingChargePercentage() {
        this.setEmcLabel("Handling Charge %");
    }
}
