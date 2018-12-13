/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package emc.bus.inventory;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemSerialBean extends EMCEntityBean implements InventoryItemSerialLocal {

    /** Create a new instance of InventoryItemSerialBean */
    public InventoryItemSerialBean() {
        
    }
}
