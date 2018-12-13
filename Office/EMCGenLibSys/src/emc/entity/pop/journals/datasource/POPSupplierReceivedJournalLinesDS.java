/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.journals.datasource;

import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;
import emc.datatypes.pop.journals.datasource.Dimension1FK;
import emc.datatypes.pop.journals.datasource.Dimension2FK;
import emc.datatypes.pop.journals.datasource.Dimension3FK;
import emc.datatypes.systemwide.ItemPrimaryReference;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class POPSupplierReceivedJournalLinesDS extends POPSupplierReceivedJournalLines {

    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String itemPrimaryReference;
    private String itemDescription;

    public POPSupplierReceivedJournalLinesDS() {
        this.setDataSource(true);
    }

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("dimension1", new Dimension1FK());
        toBuild.put("dimension2", new Dimension2FK());
        toBuild.put("dimension3", new Dimension3FK());
        toBuild.put("itemPrimaryReference", new ItemReferenceFK());

        return toBuild;
    }
}
