/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.entity.inventory.journals.InventoryJournalLines;

/**
 *
 * @author wikus
 */
public class Dimension2DS extends Dimension2FKNotMandatory {

    public Dimension2DS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryJournalLines.class.getName());
        dsr.setRelatedTable(InventoryJournalLines.class.getName());
        dsr.setRelatedField("dimension2");
        dsr.setPKField("dimension2");
        dsr.setFKField("dimension2");
    	this.setColumnWidth(40);
        this.setDsRelation(dsr);
    }
}
