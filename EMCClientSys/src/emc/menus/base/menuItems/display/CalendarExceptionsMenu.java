/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class CalendarExceptionsMenu extends EMCMenuItem {

    /** Creates a new instance of AdminUsers */
    public CalendarExceptionsMenu() {
        this.setClassPath(emc.forms.base.display.calendar.CalendarExceptionForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Calendar Exceptions");
        this.setToolTipText("Setup the calendar exceptions");
    }
}
