/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.entity.inventory.register.InventoryStocktakeRegister;

/**
 *
 * @author wikus
 */
public class LocationDS extends LocationFKNotManditory {

    public LocationDS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedField("location");
        dsr.setPKField("location");
        dsr.setFKField("location");
    	this.setColumnWidth(57);
        this.setDsRelation(dsr);
    }
}
