/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.fileassociations.FileAssociationsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class BaseFileAssociationsMI extends EMCMenuItem {
    
    /** Creates a new instance of BaseFileAssociationsMI */
    public BaseFileAssociationsMI() {
       this.setClassPath(FileAssociationsForm.class.getName());
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("File Associations");
       this.setToolTipText("Edit EMC File Associations");
    }
}
