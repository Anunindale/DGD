/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.dangerousgoods.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.dangerousgoods.display.un.DGDUNForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author pj
 */
public class DGDUNMI extends EMCMenuItem{
    
    public DGDUNMI()
    {    
        this.setClassPath(DGDUNForm.class.getName());
        this.setMenuItemName("UN Items");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Edit UN Items");
    }
}
