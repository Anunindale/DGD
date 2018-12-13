/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.whereused;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.whreused.InventoryDimension3WhereUsed;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryDimension3WhereUsedBean extends EMCEntityBean implements InventoryDimension3WhereUsedLocal {

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        String dimensionId = (String) userData.getUserData(0);
        List dataList = new ArrayList();
        if (!isBlank(dimensionId)) {
            List<Object[]> relatedTableData = fetchRelatedData(dimensionId, userData);
            InventoryDimension3WhereUsed record;
            Object[] data;
            for (Object o : relatedTableData) {
                data = (Object[]) o;
                record = new InventoryDimension3WhereUsed();
                record.setRecordID(1);
                record.setDimension3(dimensionId);
                record.setItemId((String) data[0]);
                record.setItemReference((String) data[1]);
                record.setItemDescription((String) data[2]);
                dataList.add(record);
            }
        }
        return dataList;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        String dimensionId = (String) userData.getUserData(0);
        if (!isBlank(dimensionId)) {
            return fetchRelatedData((String) userData.getUserData(0), userData).size() + ", 0";
        }
        return "0, 0";
    }

    private List<Object[]> fetchRelatedData(String dimensionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimensionCombinations.class.getName(), "itemId");
        query.addAnd("dimension3Id", dimensionId, InventoryItemDimensionCombinations.class.getName());
        query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        query.addField("itemId", InventoryItemMaster.class.getName());
        query.addField("itemReference", InventoryItemMaster.class.getName());
        query.addField("description", InventoryItemMaster.class.getName());
        query.addGroupBy(InventoryItemDimensionCombinations.class.getName(), "itemId");
        return util.executeGeneralSelectQuery(query, userData);
    }
}
