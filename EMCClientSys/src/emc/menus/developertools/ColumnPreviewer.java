/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.developertools;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class ColumnPreviewer extends EMCMenuItem {
    /** Creates a new instance of ColumnPreviewer */
    public ColumnPreviewer() {
        this.setClassPath("emc.forms.developertools.display.columnpreview.ColumnPreviewer");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Column Previewer");
        this.setToolTipText("Preview the size of a column in a table");
    }
}
