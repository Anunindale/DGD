/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.dangerousgoods.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.dangerousgoods.display.vehicles.DGDVehiclesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author pj
 */
public class DGDVehiclesMI extends EMCMenuItem{
    
    public DGDVehiclesMI()
    {
        this.setClassPath(DGDVehiclesForm.class.getName());
        this.setMenuItemName("Vehicles");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Register vehicles");
    }
    
}
