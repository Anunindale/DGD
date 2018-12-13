/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.pickinglistsummary;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class PickingListSummaryReportBean extends EMCReportBean implements PickingListSummaryReportLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryDimensionTableLocal dimBean;

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        InventoryPickingListLines pickListLine;
        InventoryPickingListMaster pickListMaster;
        PickingListSummaryReportDS ds;
        Map<String, PickingListSummaryReportDS> sortedMap = new TreeMap<String, PickingListSummaryReportDS>(new MapComparator());
        String key;
        List<String> itemList;
        for (Object o : queryResult) {
            pickListLine = (InventoryPickingListLines) o;
            pickListMaster = findMasterPickList(pickListLine.getPickingListId(), userData);
            itemList = referenceBean.findReferenceAndDesc(pickListLine.getItemId(), InventoryReferenceTypes.PRIMARY, userData);
            key = pickListMaster.getOrderId() + pickListMaster.getPickingListId() + itemList.get(0) + pickListLine.getDeliveryAddress3() +
                    getSizeSortCode(pickListLine.getDimension2(), userData) + pickListLine.getBatch() + pickListLine.getSerial();
            ds = new PickingListSummaryReportDS();
            ds.setAwoId(pickListMaster.getOrderId());
            ds.setPickListId(pickListLine.getPickingListId());
            ds.setItemId(itemList.get(0));
            ds.setItemDesc(itemList.get(1));
            ds.setDimension1(pickListLine.getDimension1());
            ds.setDimension2(pickListLine.getDimension2());
            ds.setDimension3(pickListLine.getDimension3());
            ds.setBatch(pickListLine.getBatch());
            ds.setSerial(pickListLine.getSerial());
            ds.setRequiredQty(pickListLine.getOrderQty());
            ds.setIssuedQty(pickListLine.getIssueQty());
            ds.setIssued(pickListLine.getIssued());
            ds.setIsseuedCost(ds.getIsseuedCost() + getIssuedCost(pickListLine, userData));
            sortedMap.put(key, ds);
        }
        return new ArrayList<Object>(sortedMap.values());
    }

    private InventoryPickingListMaster findMasterPickList(String pickListId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryPickingListMaster.class.getName());
        query.addAnd("pickingListId", pickListId);
        return (InventoryPickingListMaster) util.executeSingleResultQuery(query, userData);
    }

    private String getSizeSortCode(String size, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        query.addAnd("dimensionId", size);
        query.addField("sortCode");
        Integer sortCode = (Integer) util.executeSingleResultQuery(query, userData);
        if (sortCode == null) {
            return "-0";
        } else return "-" + sortCode;
    }

    private double getIssuedCost(InventoryPickingListLines pickListLine, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        query.addAnd("transId", pickListLine.getTransId());
        query.addAnd("itemDimId", dimBean.getDimRecordId(pickListLine.getBatch(), pickListLine.getDimension1(), pickListLine.getDimension2(),
                pickListLine.getDimension3(), pickListLine.getWarehouse(), pickListLine.getLocation(),
                pickListLine.getPallet(), pickListLine.getSerial(), userData));
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Picking_List_Lines.toString());
        InventoryTransactions trans = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
        if (trans == null) {
            return 0;
        } else {
            return trans.getCost();
        }
    }
}

class MapComparator implements Comparator<String> {

    public int compare(String o1, String o2) {
        return o1.toString().compareTo(o2.toString());
    }
}
