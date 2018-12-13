package emc.datatypes.inventory.transactions.datasource.search;

import emc.datatypes.datasources.inventory.Dim3ByDimIdDS;

/**
 * 
 * @author wikus
 */
public class Colour extends Dim3ByDimIdDS {

    /** Creates a new instance of Colour */
    public Colour() {
    	this.setColumnWidth(78);
        this.getDsRelation().setFKField("itemDimId");
    }
}
