/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.inventory;

import emc.app.components.emcJComboBox;
import emc.enums.inventory.journals.InventoryJournalTypes;

/**
 *
 * @author rico
 */
public class InventoryJournalTypesCmbBox extends emcJComboBox{
     public InventoryJournalTypesCmbBox(){
         super(new String[] {InventoryJournalTypes.MOVEMENT.toString(),InventoryJournalTypes.STOCKTAKE.toString(),
         InventoryJournalTypes.TRANSFER.toString()});
    }
}
