/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.crm.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.crm.display.classification.CRMClassification3Form;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class CRMClassification3MI extends EMCMenuItem {

    public CRMClassification3MI() {
        this.setClassPath(CRMClassification3Form.class.getName());
        this.setMenuItemName("Classification 3");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup CRM Classification 3");
    }
}
