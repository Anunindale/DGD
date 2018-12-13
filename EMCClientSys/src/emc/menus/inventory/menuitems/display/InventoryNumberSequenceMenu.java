/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.forms.inventory.display.numbersequence.InventoryNumberSequenceForm;
import emc.menus.base.menuItems.display.BaseNumberSequencesMenu;

/**
 *
 * @author wikus
 */
public class InventoryNumberSequenceMenu extends BaseNumberSequencesMenu {

    public InventoryNumberSequenceMenu() {
        this.setClassPath(InventoryNumberSequenceForm.class.getName());
    }
}
