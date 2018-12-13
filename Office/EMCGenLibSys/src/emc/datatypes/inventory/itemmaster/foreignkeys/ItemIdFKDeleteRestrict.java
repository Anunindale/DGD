/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster.foreignkeys;

import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author rico
 */
public class ItemIdFKDeleteRestrict extends ItemIdFK {
    public ItemIdFKDeleteRestrict(){
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
    }

}
