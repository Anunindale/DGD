/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.crm.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.crm.display.interestgroup.CRMInterestGroupForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class CRMInterestGroupMI extends EMCMenuItem{
    public CRMInterestGroupMI(){
        this.setClassPath(CRMInterestGroupForm.class.getName());
        this.setMenuItemName("Interest Groups");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage prospect interest groups");
    }
}
