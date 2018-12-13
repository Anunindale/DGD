/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.classifications;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemClassificationBean extends EMCEntityBean implements InventoryItemClassificationLocal {
    
    /** Creates a new instance of InventoryItemClassificationBean */
    public InventoryItemClassificationBean() {
        
    }

}
