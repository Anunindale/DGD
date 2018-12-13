/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.entity.inventory.journals.InventoryJournalLines;

/**
 *
 * @author wikus
 */
public class Dimension1DS extends Dimension1FKNotMandatory {

    public Dimension1DS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryJournalLines.class.getName());
        dsr.setRelatedTable(InventoryJournalLines.class.getName());
        dsr.setRelatedField("dimension1");
        dsr.setPKField("dimension1");
        dsr.setFKField("dimension1");
    	this.setColumnWidth(39);
        this.setDsRelation(dsr);
    }
}
