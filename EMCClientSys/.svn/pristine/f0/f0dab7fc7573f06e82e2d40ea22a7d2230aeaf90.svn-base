/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.resources;

import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.menus.inventory.menuitems.display.StockTakeCaptureMenu;

/**
 *
 * @author wikus
 */
public class StockTakeButton extends emcMenuButtonList {

    public StockTakeButton(BaseInternalFrame form) {
        super("Stock Take", form);
        this.addMenuItem("Count", new StockTakeCaptureMenu(), 1, true);
        this.addMenuItem("Re Count", new StockTakeCaptureMenu(), 2, true);
    }
}
