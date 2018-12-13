package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.transactionssummary.GLTransactionsSummaryForm;
import emc.framework.EMCMenuItem;

/** 
 *
 * @author claudette
 */
public class GLTransactionsSummaryMI extends EMCMenuItem {

    /** Creates a new instance of GLTransactionsSummaryMI. */
    public GLTransactionsSummaryMI() {
        this.setClassPath(GLTransactionsSummaryForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Transaction Summary");
        this.setToolTipText("View transaction summary data");
    }
}