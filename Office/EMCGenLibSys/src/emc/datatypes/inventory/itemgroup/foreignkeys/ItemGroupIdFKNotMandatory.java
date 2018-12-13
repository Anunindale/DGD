/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemgroup.foreignkeys;


/**
 *
 * @author riaan
 */
public class ItemGroupIdFKNotMandatory extends ItemGroupIdFK {

    /** Creates a new instance of ItemGroupIdFK */
    public ItemGroupIdFKNotMandatory() {
        this.setMandatory(false);
    }
}
