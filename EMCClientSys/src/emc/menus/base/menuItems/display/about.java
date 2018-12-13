/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class about extends EMCMenuItem{
    
    /** Creates a new instance of companies */
    public about()  {
       this.setClassPath("emc.forms.base.display.about.aboutform");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("About EMC");
       this.setToolTipText("Info on EMC by ASD");
       this.setPermissionNotRequired(true);
    }
    
}