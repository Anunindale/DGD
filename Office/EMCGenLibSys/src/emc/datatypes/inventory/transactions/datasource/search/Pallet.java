package emc.datatypes.inventory.transactions.datasource.search;

import emc.datatypes.datasources.inventory.PalletByDimIdDS;

/**
 * 
 * @author wikus
 */
public class Pallet extends PalletByDimIdDS {

    /** Creates a new instance of Pallet */
    public Pallet() {
    	this.setColumnWidth(19);
        this.getDsRelation().setFKField("itemDimId");
    }
}
