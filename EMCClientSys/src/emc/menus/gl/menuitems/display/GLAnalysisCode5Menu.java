/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.analysiscode5.GLAnalysisCodes5Form;
import emc.framework.EMCMenuItem;

/**
 *
 * @author claudette
 */
public class GLAnalysisCode5Menu extends EMCMenuItem {

    public GLAnalysisCode5Menu() {
        this.setClassPath(GLAnalysisCodes5Form.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Analysis Code 5");
        this.setToolTipText("Setup Analysis Code 5");
    }
}
