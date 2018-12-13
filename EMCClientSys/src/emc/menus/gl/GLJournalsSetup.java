/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl;

import emc.framework.EMCMenu;
import emc.menus.gl.menuitems.display.GLJournalDefinitions;

/**
 *
 * @author riaan
 */
public class GLJournalsSetup extends EMCMenu {

    /** Creates a new instance of GLSetup*/
    public GLJournalsSetup() {
        this.setMenuName("Journals");
        this.setMenuList(new GLJournalApproval());
        this.setMenuList(new GLJournalDefinitions());
    }
}
