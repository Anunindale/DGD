/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.dangerousgoods.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.dangerousgoods.display.contacts.DGDContactsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author pj
 */
public class DGDContactsMI extends EMCMenuItem{
    
    public DGDContactsMI()
    {
        this.setClassPath(DGDContactsForm.class.getName());
        this.setMenuItemName("Contacts");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Show and edit contacts");
    }
    
}
