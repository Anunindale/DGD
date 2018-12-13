/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.analysiscode4.GLAnalysisCodes4Form;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class GLAnalysisCode4Menu extends EMCMenuItem {

    public GLAnalysisCode4Menu() {
        this.setClassPath(GLAnalysisCodes4Form.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Analysis Code 4");
        this.setToolTipText("Setup Analysis Code 4");
    }
}
