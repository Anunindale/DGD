/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.menus.pop.menuitems.display.CostChangeSummaryMenu;

/**
 *
 * @author wikus
 */
public class CostChangeButton extends emcMenuButtonList {

    public CostChangeButton(BaseInternalFrame form) {
        super("Cost Approval", form);
        CostChangeSummaryMenu menu = new CostChangeSummaryMenu();
        this.addMenuItem("All PO", menu, 2, false);
        this.addMenuItem("Selected PO", menu, 3, false);
    }

}
