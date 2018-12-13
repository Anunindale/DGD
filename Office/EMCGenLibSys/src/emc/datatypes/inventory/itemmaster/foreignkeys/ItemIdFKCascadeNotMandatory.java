/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster.foreignkeys;

/**
 *
 * @author riaan
 */
public class ItemIdFKCascadeNotMandatory extends ItemIdFKCascade {
    
    /** Creates a new instance of ItemIdFKCascadeNotMandatory. */
    public ItemIdFKCascadeNotMandatory() {
        this.setMandatory(false);
    }

}
