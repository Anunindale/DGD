/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.inventory.output.stockvaluation.StockValuationReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class StockValuationReportMenu extends EMCMenuItem {

    /** Creates a new instance of OnHandReportMenu */
    public StockValuationReportMenu() {
       this.setClassPath(StockValuationReportForm.class.getName());
       this.setMenuItemType(enumMenuItems.OUTPUT);
       this.setMenuItemName("Stock Valuation");
       this.setToolTipText("Print Stock Valuation Report");
    }
}
