/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.inventory;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.datatypes.systemwide.Dimention1Interface;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class Dim1ByDimIdDS extends EMCString implements Dimention1Interface {

    public Dim1ByDimIdDS() {
        DSRelation dsr = new DSRelation();
        dsr.setPKField("recordID");
        dsr.setFKField("dimensionId");
        dsr.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, java.lang.String queryCondition, EMCUserData userData) {
                query.addTable(InventoryDimensionTable.class.getName());
                query.addTableAnd(query.getEntityClassName(), PKField, InventoryDimensionTable.class.getName(), FKField);
                query.addAndCommaSeperated("dimension1Id", theValue.toString(), InventoryDimensionTable.class.getName(), EMCQueryConditions.fromString(queryCondition));
                return query;
            }
        });
        this.setDsRelation(dsr);
        this.setMandatory(false);
        this.setEmcLabel("Config");
        this.setColumnWidth(20);
        this.setRelatedTable(InventoryDimension1.class.getName());
        this.setRelatedField("dimensionId");
    }
}
