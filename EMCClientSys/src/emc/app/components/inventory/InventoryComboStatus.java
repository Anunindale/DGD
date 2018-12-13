/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.inventory;

import emc.enums.inventory.inventorytables.InventoryStatus;
import javax.swing.JComboBox;

/**
 *
 * @author rico
 */
public class InventoryComboStatus extends JComboBox{
    public InventoryComboStatus(){
         super(new String[] {InventoryStatus.ACTIVE.toString(),InventoryStatus.INDESIGN.toString(),
         InventoryStatus.REJECTED.toString(),InventoryStatus.STOPPED.toString()});
    }

}