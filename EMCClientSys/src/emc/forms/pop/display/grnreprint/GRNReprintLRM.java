/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.grnreprint;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class GRNReprintLRM extends EMCLookupRelationManager {

    @Override
    public void doRelation(EMCLookup lookup) {
        if (this.getLookupName(lookup).equals("item")) {
            Object item = lookup.getValue();
            EMCQuery query;
            if (Functions.checkBlank(item)) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
                this.getLookupFromName("dimension1").get(0).setTheQuery(query);

                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
                this.getLookupFromName("dimension2").get(0).setTheQuery(query);

                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
                this.getLookupFromName("dimension3").get(0).setTheQuery(query);
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
                query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryDimension1.class.getName(), "dimension1Id");
                query.addAnd("itemId", item, InventoryItemDimensionCombinations.class.getName());
                this.getLookupFromName("dimension1").get(0).setTheQuery(query);

                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
                query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryDimension2.class.getName(), "dimension2Id");
                query.addAnd("itemId", item, InventoryItemDimensionCombinations.class.getName());
                this.getLookupFromName("dimension2").get(0).setTheQuery(query);

                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
                query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryDimension3.class.getName(), "dimension3Id");
                query.addAnd("itemId", item, InventoryItemDimensionCombinations.class.getName());
                this.getLookupFromName("dimension3").get(0).setTheQuery(query);
            }
        }
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {
        Object item = drm.getFieldValueAt(drm.getLastRowAccessed(), "itemId");
        EMCQuery query;
        if (Functions.checkBlank(item)) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
            this.getLookupFromName("dimension1").get(0).setTheQuery(query);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
            this.getLookupFromName("dimension2").get(0).setTheQuery(query);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
            this.getLookupFromName("dimension3").get(0).setTheQuery(query);
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
            query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryDimension1.class.getName(), "dimension1Id");
            query.addAnd("itemId", item, InventoryItemDimensionCombinations.class.getName());
            this.getLookupFromName("dimension1").get(0).setTheQuery(query);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
            query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryDimension2.class.getName(), "dimension2Id");
            query.addAnd("itemId", item, InventoryItemDimensionCombinations.class.getName());
            this.getLookupFromName("dimension2").get(0).setTheQuery(query);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
            query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryDimension3.class.getName(), "dimension3Id");
            query.addAnd("itemId", item, InventoryItemDimensionCombinations.class.getName());
            this.getLookupFromName("dimension3").get(0).setTheQuery(query);
        }
    }
}
