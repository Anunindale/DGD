/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.EMCInt;
import emc.datatypes.datasources.DSRelation;
import emc.entity.inventory.register.InventoryStocktakeRegister;

/**
 *
 * @author wikus
 */
public class PageNumberDS extends EMCInt{

    public PageNumberDS() {
        this.setEmcLabel("P");
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedField("pageNumber");
        dsr.setPKField("pageNumber");
        dsr.setFKField("pageNumber");
    	this.setColumnWidth(15);
        this.setDsRelation(dsr);
    }

}
