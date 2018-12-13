/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.classification4.SOPCustomerClassification4Form;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPCustomerClassification4Menu extends EMCMenuItem {

    public SOPCustomerClassification4Menu() {
        this.setClassPath(SOPCustomerClassification4Form.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Classification 4");
        this.setToolTipText("Setup Classification 4");
    }
}
