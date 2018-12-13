/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.pop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.pop.output.millpurchase.POPMillPurchseOrderReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class MillPurchseOrderReport extends EMCMenuItem {

    public MillPurchseOrderReport() {
        this.setClassPath(POPMillPurchseOrderReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Mill Purchase Orders");
        this.setToolTipText("Print Mill Purchase Orders");
    }
}
