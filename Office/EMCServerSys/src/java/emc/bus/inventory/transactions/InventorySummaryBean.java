/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.reservationhelper.InventoryReservationHelper;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.unreserve.InventoryUnreservedSummary;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventorySummarySuspense;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderTypes;
import emc.enums.sop.SalesOrderTypes;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author rico
 */
@Stateless
public class InventorySummaryBean extends EMCEntityBean implements InventorySummaryLocal {

    @EJB
    private InventoryDimensionTableLocal dimTableBean;
    @EJB
    private ProcessStockTransactionLocal processStockTxBean;

    /**
     * Creates a new instance of InventorySummaryBean.
     */
    public InventorySummaryBean() {
    }

    /**
     * Returns the number of items with the given itemId and dimensions
     * available in stock.
     *
     * @param itemId Id of item to check.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param warehouse Warehouse.
     * @param batch Batch.
     * @param serialNo Serial number.
     * @param location Location.
     * @param pallet Pallet.
     * @param userData Userdata.
     * @return A double indicating the number of available items.
     */
    public double getQuantityAvailable(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String batch, String serialNo, String location, String pallet, EMCUserData userData) {
        long dimId = dimTableBean.getDimRecordId(batch, dimension1, dimension2, dimension3, warehouse, location, pallet, serialNo, userData);
        return getQuantityAvailable(itemId, dimId, userData);
    }

    /**
     * Returns the number of items with the given itemId and dimensionId
     * available in stock.
     *
     * @param itemId Id of item to search for.
     * @param dimId Dimension id to search for.
     * @param userData User data.
     * @return A double indicating the number of available items.
     */
    public double getQuantityAvailable(String itemId, long dimId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        query.addFieldAggregateFunction("physicalAvailable", "SUM");
        query.addAnd("itemDimId", dimId);
        query.addAnd("itemId", itemId);

        Double quantity = (Double) util.executeSingleResultQuery(query, userData);

        return quantity == null ? 0d : quantity;
    }

    /**
     * This method should be used by RESERVE only. It is called from
     * UpdateOnHand superclass
     *
     * @param toEx
     * @param suspence summary that will be inserted into SQL to show that this
     * stock is taken
     * @param userData
     * @return List with 0 - the availableQty 1 - the recordId of inserted
     * suspense summary
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getQuantityAvailableLoadSuspenceTrans(EMCQuery checkQty, InventorySummary suspence, InventorySummaryUpdateOptions theOption, String transRef, EMCUserData userData) throws EMCEntityBeanException {
        //Trans ref in itself is too long - more than 255 characters.  Use String value of HashCode.
        transRef = String.valueOf(transRef.hashCode());

        List toRet = new ArrayList();
        double availQty = 0;
        boolean insert = false;
        InventoryReservationHelper helper = null;
        //read from reserve block table;
        EMCQuery resHQ = new EMCQuery(enumQueryTypes.SELECT, InventoryReservationHelper.class);
        resHQ.setNativeForUpdate(true);
        List<InventoryReservationHelper> resList = util.executeNativeQuery(resHQ, InventoryReservationHelper.class, userData);
        if (resList == null || resList.size() == 0) {
            //insert an instance
            helper = new InventoryReservationHelper();
            helper.setLastUpdateInfo("First Instance");
            insert = true;
        } else {
            helper = resList.get(0);
            helper.setLastUpdateInfo(suspence.getItemId() + ":" + suspence.getItemDimId());
        }

        List qResL = util.executeNativeQuery(checkQty, userData);
        if (qResL != null && !qResL.isEmpty() && qResL.get(0) != null) {
            availQty = Double.parseDouble(qResL.get(0).toString());
        }
        //check Unreserved
        EMCQuery unResQ = new EMCQuery(enumQueryTypes.SELECT, InventoryUnreservedSummary.class);
        List fieldList = checkQty.getFieldList();
        if (fieldList == null || fieldList.isEmpty()) {
            this.logMessage(Level.SEVERE, "Reserve mechanism expects a field.", userData);
        }
        if (fieldList.get(0).toString().contains("physicalAvailable")) {
            unResQ.addFieldAggregateFunction("availUnreserved", "SUM");
        } else if (fieldList.get(0).toString().contains("recAvailable")) {
            unResQ.addFieldAggregateFunction("recUnreserved", "SUM");
        } else if (fieldList.get(0).toString().contains("qcAvailable")) {
            unResQ.addFieldAggregateFunction("qcUnreserved", "SUM");
        } else {
            this.logMessage(Level.SEVERE, "Reserve mechanism expects a field.", userData);
        }
        unResQ.addAnd("transactionRef", transRef);
        unResQ.addAnd("itemDimId", suspence.getItemDimId());
        unResQ.addAnd("itemId", suspence.getItemId());

        qResL = util.executeNativeQuery(unResQ, userData);
        if (qResL != null && !qResL.isEmpty() && qResL.get(0) != null) {
            availQty += Double.parseDouble(qResL.get(0).toString());
        }
        //finish unreserved select
        toRet.add(availQty);
        switch (theOption) {
            case ORDERED_OUT_RESERVED:
                InventorySummary clone = (InventorySummary) this.doClone(suspence, userData);
                clone.setClosed(InventorySummarySuspense.SUSPENSE.toString());
                clone = (InventorySummary) insert(clone, userData);
                toRet.add(clone.getRecordID());
                break;
            case ORDERED_OUT_UNRESERVED:
                InventoryUnreservedSummary unRes = new InventoryUnreservedSummary();
                unRes.setItemDimId(suspence.getItemDimId());
                unRes.setItemId(suspence.getItemId());
                unRes.setTransactionRef(transRef);
                unRes.setAvailUnreserved(suspence.getPhysicalAvailable());
                unRes.setRecUnreserved(suspence.getRecAvailable());
                unRes.setQcUnreserved(suspence.getQcAvailable());
                unRes = (InventoryUnreservedSummary) super.insert(unRes, userData);
                toRet.add(unRes.getRecordID());
                break;
            default:
                this.logMessage(Level.SEVERE, "Reserve mechanism does not support the option " + theOption.toString(), userData);
                break;
        }
        if (insert) {
            super.insert(helper, userData);
        } else {
            super.update(helper, userData);
        }
        return toRet;
    }

    /**
     * Returns the total number of items available in stock for the specified
     * item/dimensions. Any dimension may be specified as null or a blank
     * String. Dimensions which are specified as null or blank Strings will not
     * be included in the group by clause.
     *
     * @param itemId Item id.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param conn Connection used to execute JDBC queries directly. May be
     * null.
     * @param userData User data.
     * @return A BigDecimal containing the total number of items available in
     * stock for the specified item/dimensions.
     */
    public BigDecimal getOnHandAvailable(String itemId, String dimension1, String dimension2, String dimension3, Connection conn, EMCUserData userData) {
        return getOnHandAvailable(itemId, dimension1, dimension2, dimension3, null, null, null, null, null, conn, userData);
    }

    /**
     * Returns the total number of items available in stock for the specified
     * item/dimensions. Any dimension may be specified as null or a blank
     * String. Dimensions which are specified as null or blank Strings will not
     * be included in the group by clause.
     *
     * @param itemId Item id.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param warehouse Warehouse.
     * @param location Location.
     * @param batch Batch.
     * @param serialNo Serial number.
     * @param pallet Pallet.
     * @param conn Connection used to execute JDBC queries directly. May be
     * null.
     * @param userData User data.
     * @return A BigDecimal containing the total number of items available in
     * stock for the specified item/dimensions.
     */
    public BigDecimal getOnHandAvailable(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serialNo, String pallet, Connection conn, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addFieldAggregateFunction("physicalAvailable", "SUM");
        query.addAnd("itemId", itemId);

        if (!isBlank(dimension1)) {
            query.addAnd("dimension1", dimension1);
            query.addGroupBy("dimension1");
        }

        if (!isBlank(dimension2)) {
            query.addAnd("dimension2", dimension2);
            query.addGroupBy("dimension2");
        }

        if (!isBlank(dimension3)) {
            query.addAnd("dimension3", dimension3);
            query.addGroupBy("dimension3");
        }

        if (!isBlank(warehouse)) {
            query.addAnd("warehouse", warehouse);
            query.addGroupBy("warehouse");
        }

        if (!isBlank(location)) {
            query.addAnd("location", location);
            query.addGroupBy("location");
        }

        if (!isBlank(batch)) {
            query.addAnd("batch", batch);
            query.addGroupBy("batch");
        }

        if (!isBlank(serialNo)) {
            query.addAnd("serialNo", serialNo);
            query.addGroupBy("serialNo");
        }

        if (!isBlank(pallet)) {
            query.addAnd("pallet", pallet);
            query.addGroupBy("pallet");
        }

        Double available = null;

        if (conn == null) {
            available = (Double) util.executeSingleResultQuery(query, userData);
        } else {
            try {
                List<Object[]> selected = util.exJDBCFieldReadQuery(conn, query, userData);
                if (selected == null || selected.isEmpty()) {
                    return BigDecimal.ZERO;
                } else {
                    Object[] selectedRow = selected.get(0);
                    if (selectedRow == null || selectedRow.length == 0 || selectedRow[0] == null) {
                        return BigDecimal.ZERO;
                    } else {
                        return util.getBigDecimal((Double) selectedRow[0]);
                    }
                }
            } catch (EMCEntityBeanException ex) {
                return BigDecimal.ZERO;
            }
        }

        return available != null ? util.getBigDecimal(available, 2) : BigDecimal.ZERO;
    }

    public BigDecimal getOnHandTotal(String itemId, String dimension1, String dimension2, String dimension3, Connection conn, EMCUserData userData) {
        return getOnHandTotal(itemId, dimension1, dimension2, dimension3, null, null, null, null, null, conn, userData);
    }

    public BigDecimal getOnHandTotal(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serialNo, String pallet, Connection conn, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addFieldAggregateFunction("physicalTotal", "SUM");
        query.addAnd("itemId", itemId);

        if (!isBlank(dimension1)) {
            query.addAnd("dimension1", dimension1);
            query.addGroupBy("dimension1");
        }

        if (!isBlank(dimension2)) {
            query.addAnd("dimension2", dimension2);
            query.addGroupBy("dimension2");
        }

        if (!isBlank(dimension3)) {
            query.addAnd("dimension3", dimension3);
            query.addGroupBy("dimension3");
        }

        if (!isBlank(warehouse)) {
            query.addAnd("warehouse", warehouse);
            query.addGroupBy("warehouse");
        }

        if (!isBlank(location)) {
            query.addAnd("location", location);
            query.addGroupBy("location");
        }

        if (!isBlank(batch)) {
            query.addAnd("batch", batch);
            query.addGroupBy("batch");
        }

        if (!isBlank(serialNo)) {
            query.addAnd("serialNo", serialNo);
            query.addGroupBy("serialNo");
        }

        if (!isBlank(pallet)) {
            query.addAnd("pallet", pallet);
            query.addGroupBy("pallet");
        }

        Double available = null;

        if (conn == null) {
            available = (Double) util.executeSingleResultQuery(query, userData);
        } else {
            try {
                List<Object[]> selected = util.exJDBCFieldReadQuery(conn, query, userData);
                if (selected == null || selected.isEmpty()) {
                    return BigDecimal.ZERO;
                } else {
                    Object[] selectedRow = selected.get(0);
                    if (selectedRow == null || selectedRow.length == 0 || selectedRow[0] == null) {
                        return BigDecimal.ZERO;
                    } else {
                        return util.getBigDecimal((Double) selectedRow[0]);
                    }
                }
            } catch (EMCEntityBeanException ex) {
                return BigDecimal.ZERO;
            }
        }

        return available != null ? util.getBigDecimal(available, 2) : BigDecimal.ZERO;
    }

    public BigDecimal getOnHandReserved(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serialNo, String pallet, Connection conn, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addFieldAggregateFunction("physicalReserved", "SUM");
        query.addAnd("itemId", itemId);

        if (!isBlank(dimension1)) {
            query.addAnd("dimension1", dimension1);
            query.addGroupBy("dimension1");
        }

        if (!isBlank(dimension2)) {
            query.addAnd("dimension2", dimension2);
            query.addGroupBy("dimension2");
        }

        if (!isBlank(dimension3)) {
            query.addAnd("dimension3", dimension3);
            query.addGroupBy("dimension3");
        }

        if (!isBlank(warehouse)) {
            query.addAnd("warehouse", warehouse);
            query.addGroupBy("warehouse");
        }

        if (!isBlank(location)) {
            query.addAnd("location", location);
            query.addGroupBy("location");
        }

        if (!isBlank(batch)) {
            query.addAnd("batch", batch);
            query.addGroupBy("batch");
        }

        if (!isBlank(serialNo)) {
            query.addAnd("serialNo", serialNo);
            query.addGroupBy("serialNo");
        }

        if (!isBlank(pallet)) {
            query.addAnd("pallet", pallet);
            query.addGroupBy("pallet");
        }

        Double available = null;

        if (conn == null) {
            available = (Double) util.executeSingleResultQuery(query, userData);
        } else {
            try {
                List<Object[]> selected = util.exJDBCFieldReadQuery(conn, query, userData);
                if (selected == null || selected.isEmpty()) {
                    return BigDecimal.ZERO;
                } else {
                    Object[] selectedRow = selected.get(0);
                    if (selectedRow == null || selectedRow.length == 0 || selectedRow[0] == null) {
                        return BigDecimal.ZERO;
                    } else {
                        return util.getBigDecimal((Double) selectedRow[0]);
                    }
                }
            } catch (EMCEntityBeanException ex) {
                return BigDecimal.ZERO;
            }
        }

        return available != null ? util.getBigDecimal(available, 2) : BigDecimal.ZERO;
    }

    @Override
    public boolean fixBlanketOrderSummaries(EMCUserData userData) throws EMCEntityBeanException {
        TransactionHelper helper;

        //Delete Existing Summaries in new Tx
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, InventorySummary.class);
        query.addAnd("inventoryTransRef", 0);
        util.executeUpdateInNewTransaction(query, userData);

        //Fetch All Open Sales Blanket Orders
        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
        query.addTableAnd(SOPSalesOrderMaster.class.getName(), "salesOrderNo", SOPSalesOrderLines.class.getName(), "salesOrderNo");
        query.addAnd("salesOrderType", SalesOrderTypes.BLANKET_ORDER.toString(), SOPSalesOrderMaster.class.getName());
        query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        query.addAnd("blanketOrderQtyReleased", EMCQueryConditions.LESS_THAN, "quantity", SOPSalesOrderLines.class.getName());
        List<SOPSalesOrderLines> salesBlanketOrders = util.executeGeneralSelectQuery(query, userData);


        for (SOPSalesOrderLines blanketLine : salesBlanketOrders) {
            System.out.println("Processing Sales - " + salesBlanketOrders.indexOf(blanketLine) + " of " + salesBlanketOrders.size());
            helper = new TransactionHelper(TransactionType.SOP_ORDER_BLANKET_ORDER_LINE);
            helper.setSalesBOQuantity(blanketLine.getQuantity().subtract(blanketLine.getBlanketOrderQtyReleased()));

            try {
                processStockTxBean.post(blanketLine, helper, userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException(ex.getMessage());
            }
        }

        //Fetch All Open Purchasing Blanket Orders
        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addTableAnd(POPPurchaseOrderMaster.class.getName(), "purchaseOrderId", POPPurchaseOrderLines.class.getName(), "purchaseOrderId");
        query.addAnd("purchaseOrderType", PurchaseOrderTypes.BLANKET_ORDER.toString(), POPPurchaseOrderMaster.class.getName());
        query.addAnd("cancelled", false, POPPurchaseOrderMaster.class.getName());
        query.addAnd("status", PurchaseOrderStatus.RECEIVED.toString(), POPPurchaseOrderMaster.class.getName(), EMCQueryConditions.NOT);
        query.addAnd("itemsReceived", EMCQueryConditions.LESS_THAN, "quantity", POPPurchaseOrderLines.class.getName());
        List<POPPurchaseOrderLines> purchasingBlanketOrders = util.executeGeneralSelectQuery(query, userData);


        for (POPPurchaseOrderLines blanketLine : purchasingBlanketOrders) {
            System.out.println("Processing Purchasing - " + purchasingBlanketOrders.indexOf(blanketLine) + " of " + purchasingBlanketOrders.size());
            helper = new TransactionHelper(TransactionType.POP_UPDATE_BLANCKET_ORDER);
            helper.setPOPBlancketOrderQty(blanketLine.getQuantity() - blanketLine.getItemsReceived());

            try {
                processStockTxBean.post(blanketLine, helper, userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException(ex.getMessage());
            }
        }

        return true;
    }
}
