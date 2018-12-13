/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.classification6.SOPCustomerClassification6Form;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPCustomerClassification6Menu extends EMCMenuItem {

    public SOPCustomerClassification6Menu() {
        this.setClassPath(SOPCustomerClassification6Form.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Classification 6");
        this.setToolTipText("Setup Classification 6");
    }
}
