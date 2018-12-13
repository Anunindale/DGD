/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.calendar.CalendarExceptionTypesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class CalendarExceptionTypesMenu extends EMCMenuItem {

    public CalendarExceptionTypesMenu() {
        this.setClassPath(CalendarExceptionTypesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Calendar Exception Types");
        this.setToolTipText("Setup the calendar exception types");
    }
}
