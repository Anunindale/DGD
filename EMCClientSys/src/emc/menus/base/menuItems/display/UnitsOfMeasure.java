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
public class UnitsOfMeasure extends EMCMenuItem {

    /** Creates a new instance of PostalCodes */
    public UnitsOfMeasure()  {
       this.setClassPath("emc.forms.base.display.unitsofmeasure.UnitsOfMeasureForm");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Units of Measure");
       this.setToolTipText("Edit units of measure");
    }
}
