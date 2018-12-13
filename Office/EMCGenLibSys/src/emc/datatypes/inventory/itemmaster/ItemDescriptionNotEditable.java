/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.EMCString;

/**
 * @Name         : ItemDescriptionNotEditable
 *
 * @Date         : 17 Jul 2009
 * 
 * @Description  : Datatype to be used in datasources with item reference and description.
 *
 * @author       : riaan
 */
public class ItemDescriptionNotEditable extends EMCString {

    /** Creates a new instance of ItemDescriptionNotEditable. */
    public ItemDescriptionNotEditable() {
        this.setEmcLabel("Item Description");
        this.setEditable(false);
        this.setColumnWidth(135);
    }
}
