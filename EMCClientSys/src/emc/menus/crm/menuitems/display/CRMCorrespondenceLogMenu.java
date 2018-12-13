/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.crm.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.crm.display.correspondencelog.CRMCorrespondenceLogForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class CRMCorrespondenceLogMenu extends EMCMenuItem {

    public CRMCorrespondenceLogMenu() {
        this.setClassPath(CRMCorrespondenceLogForm.class.getName());
        this.setMenuItemName("Correspondence Log");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Keep trac of all Correspondences");
    }
}
