/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.entity.inventory.journals.InventoryJournalLines;

/**
 *
 * @author wikus
 */
public class Dimension3DS extends Dimension3FKNotMandatory {

    public Dimension3DS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryJournalLines.class.getName());
        dsr.setRelatedTable(InventoryJournalLines.class.getName());
        dsr.setRelatedField("dimension3");
        dsr.setPKField("dimension3");
        dsr.setFKField("dimension3");
    	this.setColumnWidth(59);
        this.setDsRelation(dsr);
    }
}
