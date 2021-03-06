/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.inventory;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.datatypes.systemwide.Dimention2Interface;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class Dim2ByDimIdDS extends EMCString implements Dimention2Interface {

    public Dim2ByDimIdDS() {
        DSRelation dsr = new DSRelation();
        dsr.setPKField("recordID");
        dsr.setFKField("dimensionId");
        dsr.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, java.lang.String queryCondition, EMCUserData userData) {
                query.addTable(InventoryDimensionTable.class.getName());
                query.addTableAnd(query.getEntityClassName(), PKField, InventoryDimensionTable.class.getName(), FKField);
                query.addAndCommaSeperated("dimension2Id", theValue.toString(), InventoryDimensionTable.class.getName(), EMCQueryConditions.fromString(queryCondition));
                return query;
            }
        });
        this.setDsRelation(dsr);
        this.setMandatory(false);
        this.setEmcLabel("Size");
        this.setColumnWidth(65);
        this.setRelatedTable(InventoryDimension2.class.getName());
        this.setRelatedField("dimensionId");
    }
}
