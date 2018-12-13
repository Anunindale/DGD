package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.transactiondaysummary.GLTransactionDaySummaryForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author claudette
*/
public class GLTransactionDaySummaryMI extends EMCMenuItem{

    /** Creates a new instance of GLTransactionDaySummaryMI. */
    public GLTransactionDaySummaryMI() {
        this.setClassPath(GLTransactionDaySummaryForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Transaction Day Summary");
        this.setToolTipText("View and Edit Transaction Day Summary data");
    }
}