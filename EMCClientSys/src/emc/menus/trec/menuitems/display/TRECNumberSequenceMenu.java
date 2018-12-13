/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.trec.menuitems.display;

import emc.forms.trec.display.numbersequence.TRECNumberSequenceForm;
import emc.menus.base.menuItems.display.BaseNumberSequencesMenu;

/**
 *
 * @author wikus
 */
public class TRECNumberSequenceMenu extends BaseNumberSequencesMenu {

    public TRECNumberSequenceMenu() {
        this.setClassPath(TRECNumberSequenceForm.class.getName());
    }
}
