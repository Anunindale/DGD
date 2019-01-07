/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.dangerousgoods.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.dangerousgoods.output.declaration.DGDeclarationReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author pj
 */
public class DeclarationReportMI extends EMCMenuItem
{
    public DeclarationReportMI()
    {
        this.setClassPath(DGDeclarationReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Dangerous Goods Declaration");
        this.setToolTipText("Print a dangerous goods declaration");
    }
}
