package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.servertransactions.BaseServerTransactionsLogForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author Owner
*/
public class BaseServerTransactionsLogMI extends EMCMenuItem{

    /** Creates a new instance of BaseServerTransactionsLogMI. */
    public BaseServerTransactionsLogMI() {
        this.setClassPath(BaseServerTransactionsLogForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Server Transactions Log");
        this.setToolTipText("View and Edit Server Transactions Log data");
    }
}