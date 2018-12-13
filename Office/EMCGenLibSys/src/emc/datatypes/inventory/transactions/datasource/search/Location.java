package emc.datatypes.inventory.transactions.datasource.search;

import emc.datatypes.datasources.inventory.LocationByDimIdDS;

/**
 * 
 * @author wikus
 */
public class Location extends LocationByDimIdDS {

    /** Creates a new instance of Location */
    public Location() {
        this.setEmcLabel("Loc");
    	this.setColumnWidth(31);
        this.getDsRelation().setFKField("itemDimId");
    }
}
