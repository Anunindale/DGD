/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorders;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPGoodsReceivedBean extends EMCReportBean implements POPGoodsReceivedLocal {

    @EJB
    private InventoryReferenceLocal refBean;

    /** Creates a new instance of POPGoodsReceivedBean. */
    public POPGoodsReceivedBean() {

    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> transactions, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {

        List<Object> ret = new ArrayList<Object>();

        InventoryTransactions transaction;
        InventoryItemMaster item = null;
        InventoryDimensionTable dimensions = null;

        EMCQuery query = null;

        POPGoodsReceivedDS ds = null;

        Map<String, POPGoodsReceivedDS> dsMap = new HashMap<String, POPGoodsReceivedDS>();

        List<String> orderedKeys = new ArrayList<String>();

        for (Object ob : transactions) {
            transaction = (InventoryTransactions) ob;
            if (transaction.getQuantity() < 0 || isBlank(transaction.getDocumentNo()) || InventoryTransactionDirection.OUT.toString().equals(transaction.getDirection())) {
                continue;
            }

            //query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class.getName());
            //query.addAnd("supplierId", transaction.getSupplierId());

            //supplier = (POPSuppliers)util.executeSingleResultQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addAnd("itemId", transaction.getItemId());

            item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class.getName());
            query.addAnd("recordID", transaction.getItemDimId());

            dimensions = (InventoryDimensionTable) util.executeSingleResultQuery(query, userData);

            String mapKey = transaction.getDocumentNo() + item.getItemId() + dimensions.getDimension1Id() +
                    dimensions.getDimension2Id() + dimensions.getDimension3Id();

            if (!orderedKeys.contains(mapKey)) {
                orderedKeys.add(mapKey);
            }

            if (dsMap.containsKey(mapKey)) {
                ds = dsMap.get(mapKey);
            } else {
                ds = new POPGoodsReceivedDS();
            }

            ds.setSupplier(transaction.getSupplierId());
            ds.setPurchaseOrder(transaction.getRefNumber());

            ds.setDeliveryDate(transaction.getPhysicalDate() == null ? "" : Functions.date2String(transaction.getPhysicalDate()));

            ds.setUom(item.getBaseUOM());

            String itemRef = refBean.checkItemReference(item.getItemId(), userData)[1];

            ds.setItemId(itemRef);
            ds.setItemDescription(item.getDescription());
            ds.setDimension1(dimensions.getDimension1Id());
            ds.setDimension2(dimensions.getDimension2Id());
            ds.setDimension3(dimensions.getDimension3Id());
            ds.setItemPrice(Math.abs(transaction.getCost()) / transaction.getQuantity());

            //Transaction fields
            ds.setDeliveryNote(transaction.getDocumentNo());
            //No document date?  null in SQL
            ds.setQtyReceived(ds.getQtyReceived() + (transaction.getQuantity()));

            //doValueReceived(ds, purchaseOrderLine);

            ds.setValueReceived(ds.getQtyReceived() * ds.getItemPrice());

            dsMap.put(mapKey, ds);
        }

        for (String key : orderedKeys) {
            ret.add(dsMap.get(key));
        }

        return ret;
    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        List<Object> results = executeQuery(parameters.get(1).toString(), userData);

        return manipulateQueryResult(results, null, userData);
    }

    /** Calculates the outstanding value on a line */
    private void doValueReceived(POPGoodsReceivedDS ds, POPPurchaseOrderLines poLine) {
        //Use net amount, to ensure that discount is included
        double netAmount = poLine.getNetAmount();

        double receivedItems = ds.getQtyReceived();
        double itemNetCost = (netAmount / poLine.getQuantity());

        ds.setValueReceived(receivedItems * itemNetCost);
    }
}
