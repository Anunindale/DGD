/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.crm.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.crm.display.documents.CRMDocumentsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class CRMDocumentsMenu extends EMCMenuItem {

    public CRMDocumentsMenu() {
        this.setClassPath(CRMDocumentsForm.class.getName());
        this.setMenuItemName("Documents");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage documents");
    }
}
