/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.batchconsolidation;

import emc.entity.inventory.InventoryBatchConsolidationLines;
import emc.entity.inventory.InventoryBatchConsolidationMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BatchConsolidationPickingListReportBean extends EMCReportBean implements BatchConsolidationPickingListReportLocal {

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        //Default query joins InventoryBatchConsolidationMaster, InventoryBatchConsolidationLines, InventoryJournalLines and InventoryItemMaster
        query.addField("consolidationNumber", InventoryBatchConsolidationMaster.class.getName());//0
        query.addField("itemReference", InventoryItemMaster.class.getName());//1
        query.addField("description", InventoryItemMaster.class.getName());//2
        query.addField("dimension1", InventoryJournalLines.class.getName());//3
        query.addField("dimension2", InventoryJournalLines.class.getName());//4
        query.addField("dimension3", InventoryJournalLines.class.getName());//5
        query.addField("warehouse", InventoryJournalLines.class.getName());//6
        query.addField("location", InventoryJournalLines.class.getName());//7
        query.addField("batch", InventoryJournalLines.class.getName());//8
        query.addField("toBatch", InventoryJournalLines.class.getName());//9
        query.addField("quantity", InventoryBatchConsolidationLines.class.getName());//10
        query.addField("baseUOM", InventoryItemMaster.class.getName());//11
        return query;
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {
        List<Object> reportData = new ArrayList<Object>();

        Object[] selectedData;
        BatchConsolidationPickingListReportDS ds;

        Map<String, String> colourDescMap = new HashMap<String,String>();
        String colourDesc;
        EMCQuery query;

        for (Object o : queryResult) {
            selectedData = (Object[]) o;

            ds = new BatchConsolidationPickingListReportDS();
            ds.setConsolidationNumber((String) selectedData[0]);
            ds.setPickingDate(Functions.nowDateString("yyyy/MM/dd"));
            ds.setItemReference((String) selectedData[1]);
            ds.setItemDesc((String) selectedData[2]);
            ds.setDimension1((String) selectedData[3]);
            ds.setDimension2((String) selectedData[4]);
            ds.setDimension3((String) selectedData[5]);
            ds.setWarehouse((String) selectedData[6]);
            ds.setLocationId((String) selectedData[7]);
            ds.setBatch((String) selectedData[8]);
            ds.setToBatch((String) selectedData[9]);
            ds.setQtyToPick((BigDecimal) selectedData[10]);
            ds.setUoM((String) selectedData[11]);

            if (!isBlank(ds.getDimension3())) {
                colourDesc = colourDescMap.get(ds.getDimension3());

                if (colourDesc == null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class);
                    query.addAnd("dimensionId", ds.getDimension3());
                    query.addField("description");
                    colourDesc = (String) util.executeSingleResultQuery(query, userData);

                    colourDescMap.put(ds.getDimension3(), colourDesc);
                }

                ds.setColourDesc(colourDesc);
            }
            reportData.add(ds);
        }
        return reportData;
    }
}
