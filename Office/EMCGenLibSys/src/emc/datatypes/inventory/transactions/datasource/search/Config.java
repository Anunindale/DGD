package emc.datatypes.inventory.transactions.datasource.search;

import emc.datatypes.datasources.inventory.Dim1ByDimIdDS;

/**
 * 
 * @author wikus
 */
public class Config extends Dim1ByDimIdDS {

    /** Creates a new instance of Config */
    public Config() {
    	this.setColumnWidth(78);
        this.getDsRelation().setFKField("itemDimId");
    }
}
