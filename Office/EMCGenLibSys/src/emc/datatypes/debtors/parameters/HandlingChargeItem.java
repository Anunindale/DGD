/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKNotMandatory;

/**
 * @description : Data type for handlingChargeId on DebtorsParameters.
 *
 * @date        : 22 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class HandlingChargeItem extends ItemIdFKNotMandatory {

    /** Creates a new instance of HandlingChargeItem */
    public HandlingChargeItem() {
        this.setEmcLabel("Handling Charge Item");
    }
}
