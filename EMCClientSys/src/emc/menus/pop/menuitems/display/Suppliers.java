/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.pop.display.suppliers.SuppliersForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class Suppliers extends EMCMenuItem {
    
    /** Creates a new instance of Suppliers */
    public Suppliers() {
        this.setClassPath(SuppliersForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Suppliers");
        this.setToolTipText("Edit suppliers");
    }

}
