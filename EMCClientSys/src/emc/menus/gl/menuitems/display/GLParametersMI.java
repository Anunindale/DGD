package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.parameters.GLParametersForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author claudette
*/
public class GLParametersMI extends EMCMenuItem{

    /** Creates a new instance of GLParametersMI. */
    public GLParametersMI() {
        this.setClassPath(GLParametersForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Parameters");
        this.setToolTipText("View and Edit Parameters data");
    }
}