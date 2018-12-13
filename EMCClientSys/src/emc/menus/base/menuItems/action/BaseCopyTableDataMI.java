/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.action;

import emc.enums.enumMenuItems;
import emc.forms.base.action.copytabledata.BaseCopyTableDataForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class BaseCopyTableDataMI extends EMCMenuItem {

    public BaseCopyTableDataMI() {
        this.setClassPath(BaseCopyTableDataForm.class.getName());
        this.setMenuItemType(enumMenuItems.ACTION);
        this.setMenuItemName("Copy Table Data");
        this.setToolTipText("Copy Table Data between companies.");
    }
}
