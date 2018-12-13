package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.grouprules.GLGroupRulesForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author claudette
*/
public class GLGroupRulesMI extends EMCMenuItem{

    /** Creates a new instance of GLGroupRulesMI. */
    public GLGroupRulesMI() {
        this.setClassPath(GLGroupRulesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Group Rules");
        this.setToolTipText("View and Edit Group Rules data");
    }
}