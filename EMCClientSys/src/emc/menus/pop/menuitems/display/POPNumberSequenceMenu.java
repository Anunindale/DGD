/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.pop.menuitems.display;

import emc.forms.pop.display.numbersequence.POPNumberSequenceForm;
import emc.menus.base.menuItems.display.BaseNumberSequencesMenu;

/**
 *
 * @author wikus
 */
public class POPNumberSequenceMenu extends BaseNumberSequencesMenu {

    public POPNumberSequenceMenu() {
        this.setClassPath(POPNumberSequenceForm.class.getName());
    }
}
