/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.posting.datasource;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.entity.pop.posting.POPPurchasePostLines;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class POPPurchasePostLinesDS extends POPPurchasePostLines {

    private String warehouse;
    private String dimension1Id;
    private String dimension2Id;
    private String dimension3Id;
    private String itemDesc;

    /** Creates a new instance of POPPurchasePostLinesDS. */
    public POPPurchasePostLinesDS() {
        setDataSource(true);
    }

    public String getDimension1Id() {
        return dimension1Id;
    }

    public void setDimension1Id(String dimension1Id) {
        this.dimension1Id = dimension1Id;
    }

    public String getDimension2Id() {
        return dimension2Id;
    }

    public void setDimension2Id(String dimension2Id) {
        this.dimension2Id = dimension2Id;
    }

    public String getDimension3Id() {
        return dimension3Id;
    }

    public void setDimension3Id(String dimension3Id) {
        this.dimension3Id = dimension3Id;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("dimension1Id", new Dimension1FKNotMandatory());
        toBuild.put("dimension2Id", new Dimension2FKNotMandatory());
        toBuild.put("dimension3Id", new Dimension3FKNotMandatory());
        return toBuild;
    }
}
