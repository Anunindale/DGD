/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.warehouselocationtransfer;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryWarehouseLocationTransferReportBean extends EMCReportBean implements InventoryWarehouseLocationTransferReportLocal {

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addField("itemReference", InventoryItemMaster.class.getName());
        query.addField("description", InventoryItemMaster.class.getName());
        query.addField("dimension1", InventoryJournalLines.class.getName());
        query.addField("dimension2", InventoryJournalLines.class.getName());
        query.addField("dimension3", InventoryJournalLines.class.getName());
        query.addField("batch", InventoryJournalLines.class.getName());
        query.addField("serial", InventoryJournalLines.class.getName());
        query.addField("warehouse", InventoryJournalLines.class.getName());
        query.addField("location", InventoryJournalLines.class.getName());
        query.addField("toWarehouse", InventoryJournalLines.class.getName());
        query.addField("toLocation", InventoryJournalLines.class.getName());
        query.addField("quantity", InventoryJournalLines.class.getName());

        return query;
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {
        List<Object> reportData = new ArrayList<Object>();

        Boolean printToWarehouse = (Boolean) paramMap.get("printToWarehouse");
        if (printToWarehouse == null) {
            printToWarehouse = false;
        }
        Boolean printToLocation = (Boolean) paramMap.get("printToLocation");
        if (printToLocation == null) {
            printToLocation = false;
        }

        Object[] selected;
        InventoryWarehouseLocationTransferReportDS ds;

        for (Object o : queryResult) {
            selected = (Object[]) o;

            ds = new InventoryWarehouseLocationTransferReportDS();
            ds.setItem((String) selected[0]);
            ds.setDescription((String) selected[1]);
            ds.setDim1((String) selected[2]);
            ds.setDim2((String) selected[3]);
            ds.setDim3((String) selected[4]);
            ds.setBatch((String) selected[5]);
            ds.setSerial((String) selected[6]);
            ds.setWarehouse((String) selected[7]);
            ds.setLocation((String) selected[8]);
            if (printToWarehouse) {
                ds.setToWarehouse((String) selected[9]);
            }
            if (printToLocation) {
                ds.setToLocation((String) selected[10]);
            }
            ds.setQuantity(util.getBigDecimal((Double) selected[11]));

            reportData.add(ds);
        }

        return reportData;
    }
}
