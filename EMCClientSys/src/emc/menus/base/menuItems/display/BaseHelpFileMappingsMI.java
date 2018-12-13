package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.helpfiles.BaseHelpFileMappingsForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author riaan
*/
public class BaseHelpFileMappingsMI extends EMCMenuItem{

    /** Creates a new instance of BaseHelpFileMappingsMI. */
    public BaseHelpFileMappingsMI() {
        this.setClassPath(BaseHelpFileMappingsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Help File Mappings");
        this.setToolTipText("View and Edit Help File Mappings");
    }
}