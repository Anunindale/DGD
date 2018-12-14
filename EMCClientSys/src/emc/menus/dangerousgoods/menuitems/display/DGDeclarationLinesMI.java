/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.dangerousgoods.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.dangerousgoods.display.declarationlines.DGDeclarationLinesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author pj
 */
public class DGDeclarationLinesMI extends EMCMenuItem{
    
    public DGDeclarationLinesMI()
    {
        this.setClassPath(DGDeclarationLinesForm.class.getName());
        this.setMenuItemName("Declarations");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("View and create dangerous goods declarations");
    }
    
}
