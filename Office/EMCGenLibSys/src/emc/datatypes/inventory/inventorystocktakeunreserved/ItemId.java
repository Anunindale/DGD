/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventorystocktakeunreserved;

import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;

/**
 *
 * @author claudette
 */
public class ItemId extends ItemIdFK {

    public ItemId() {
        this.setEditable(false);
    }
}
