/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.developertools.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.developertools.output.taskmanagement.DevDevelopmentSummaryForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class DevDevelopmentSummaryMI extends EMCMenuItem {

    public DevDevelopmentSummaryMI() {
        this.setClassPath(DevDevelopmentSummaryForm.class.getName());
        this.setMenuItemName("Development Summary");
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print a development summary.");
    }

}
