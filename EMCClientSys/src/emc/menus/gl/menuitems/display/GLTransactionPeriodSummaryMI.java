package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.transactionperiodsummary.GLTransactionPeriodSummaryForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author claudette
*/
public class GLTransactionPeriodSummaryMI extends EMCMenuItem{

    /** Creates a new instance of GLTransactionPeriodSummaryMI. */
    public GLTransactionPeriodSummaryMI() {
        this.setClassPath(GLTransactionPeriodSummaryForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Transaction Period Summary");
        this.setToolTipText("View and Edit Transaction Period Summary data");
    }
}