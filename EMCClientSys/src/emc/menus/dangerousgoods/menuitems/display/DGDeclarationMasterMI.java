/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.dangerousgoods.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.dangerousgoods.display.declarationmaster.DGDeclarationMasterForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author pj
 */
public class DGDeclarationMasterMI extends EMCMenuItem{

    public DGDeclarationMasterMI() 
    {
        
        this.setClassPath(DGDeclarationMasterForm.class.getName());
        this.setMenuItemName("Declarations");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Construct declarations");
    
    }
    
}
