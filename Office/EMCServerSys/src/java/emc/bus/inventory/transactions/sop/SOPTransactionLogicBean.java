/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.sop;

import emc.bus.base.logic.BaseUOMLogicLocal;
import emc.bus.base.logic.EMCUOMConversionException;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.bus.inventory.transactions.TransactionsSuperClass;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.sop.SOPSalesOrderPostLines;
import emc.entity.sop.SOPSalesOrderPostRegister;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.enums.sop.SalesOrderTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPTransactionLogicBean extends TransactionsSuperClass implements SOPTransactionLogicLocal {

    @EJB
    private InventoryItemMasterLocal itemBean;

    /**
     * Manages the ordered out transactions for sales order lines.
     * @param salesOrderLine The Sales Order Line to reference.
     * @param userData Plain Old User Data.
     * @return The transacation in the form of a EMCTable Object.
     * @throws EMCStockException If anything goes wrong.
     */
    @Override
    public EMCTable orderSOLineStock(SOPSalesOrderLines salesOrderLine, EMCUserData userData) throws EMCStockException {
        //Flag to state transaction was found
        boolean foundOrderedTransaction = true;
        //Find Sales Order Master Master
        //Look For Ordered Transaction
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("type", InventoryTransactionTypes.SO.toString());
        query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("transId", salesOrderLine.getInventTransId());
        query.addAnd("refNumber", salesOrderLine.getSalesOrderNo());
        InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
        //Create Ordered Transaction if not found
        if (transaction == null) {
            foundOrderedTransaction = false;
            transaction = new InventoryTransactions();
            transaction.setTransId(salesOrderLine.getInventTransId());
            transaction.setCompanyId(util.getCompanyId(InventoryTransactions.class.getName(), userData));
            transaction.setDirection(InventoryTransactionDirection.OUT.toString());
            transaction.setType(InventoryTransactionTypes.SO.toString());
            transaction.setRefType(InventoryTransactionsRefType.Sales_Order.toString());
            transaction.setRefNumber(salesOrderLine.getSalesOrderNo());
            transaction.setStatus(InventoryTransactionStatus.ORDERED.toString());
            transaction.setExpectedDate(salesOrderLine.getRequiredDate());
        }
        //Find total quantity used
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("type", InventoryTransactionTypes.SO.toString());
        query.addAnd("transId", salesOrderLine.getInventTransId());
        query.addAnd("refNumber", salesOrderLine.getSalesOrderNo());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("status", InventoryTransactionStatus.ORDERED.toString(), EMCQueryConditions.NOT);
        query.addGroupBy("transId");
        query.addFieldAggregateFunction("quantity", "SUM");
        Double qtyUsed = (Double) util.executeSingleResultQuery(query, userData);
        if (qtyUsed == null) {
            qtyUsed = 0d;
        }
        //Convert Quantity to Base UOM
        double lineQuantity = 0d;
        try {
            lineQuantity = uomConv.convertToItemBase(salesOrderLine.getItemId(), salesOrderLine.getQuantity().doubleValue(), salesOrderLine.getUom(), userData);
        } catch (EMCUOMConversionException ex) {
            throw new EMCStockException("Could not convert quantity: " + ex.getMessage());
        }
        //Populate Regardless
        transaction.setQuantity(lineQuantity - qtyUsed);
        //transaction.setCost(salesOrderLine.getPrice().doubleValue());
        transaction.setItemDimId(dimIdLocal.getDimRecordId(salesOrderLine.getDimension1(), salesOrderLine.getDimension2(), salesOrderLine.getDimension3(),
                salesOrderLine.getWarehouse(), userData));
        transaction.setItemId(salesOrderLine.getItemId());
        transaction.setExpectedDate(salesOrderLine.getRequiredDate());
        try {
            if (util.compareDouble(transaction.getQuantity(), 0) > 0) {
                //Save or Update Transaction
                if (foundOrderedTransaction) {
                    trans.update(transaction, userData);
                } else {
                    trans.insert(transaction, userData);
                }
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, transaction, userData);
            } else {
                //Delete Transaction and Summary
                if (foundOrderedTransaction) {
                    trans.delete(transaction, userData);
                    EMCQuery summaryQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
                    summaryQuery.addAnd("inventoryTransRef", transaction.getRecordID());
                    InventorySummary summary = (InventorySummary) util.executeSingleResultQuery(summaryQuery, userData);
                    itemSumLocal.delete(summary, userData);
                }
            }
        } catch (EMCEntityBeanException ex) {
            throw new EMCStockException("Could not save Order stock transaction: " + ex.getMessage());
        }
        return transaction;
    }

    /**
     * Reserves stock for the given sales order post line
     * @param salesOrderNo The sales order to reserve against
     * @param salesOrderPostLine the sales order post line to reserve for
     * @param userData plain old user data
     * @return boolean based on success
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCTable reserveSOLineStock(SOPSalesOrderPostLines salesOrderPostLine, String salesOrderNo, String salesOrderType, boolean checkPickList, EMCUserData userData) throws EMCStockException {
        //Find SOPParameters Record
        EMCQuery query;
        InventoryTransactions orderTrans;
    
        //Find Reserved Quantity
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("type", InventoryTransactionTypes.SO.toString());
        query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("transId", salesOrderPostLine.getTransactionNumber());
        query.addAnd("refNumber", salesOrderNo);
        query.addFieldAggregateFunction("quantity", InventoryTransactions.class.getName(), "SUM");
        Double reserved = (Double) util.executeSingleResultQuery(query, userData);
        if (reserved == null) {
            reserved = 0d;
        }
        if (!checkPickList) {
            Double toPick = null;
            if (util.compareDouble(reserved, 0d) > 0) {
                //Find Picking List Quantity
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryPickingListLines.class);
                query.addTableAnd(InventoryPickingListMaster.class.getName(), "pickingListId", InventoryPickingListLines.class.getName(), "pickingListId");
                query.addAnd("orderId", salesOrderNo, InventoryPickingListMaster.class.getName());
                query.addAnd("issued", false, InventoryPickingListLines.class.getName());
                query.addAnd("cancelled", false, InventoryPickingListLines.class.getName());
                query.addAnd("itemId", salesOrderPostLine.getItemId());
                InventoryDimensionTable dim = dimIdLocal.getDimensionRecord(salesOrderPostLine.getDimensionId(), userData);
                if (dim != null) {
                    query.addAnd("dimension1", dim.getDimension1Id());
                    query.addAnd("dimension2", dim.getDimension2Id());
                    query.addAnd("dimension3", dim.getDimension3Id());
                }
                query.addFieldAggregateFunction("orderQty", InventoryPickingListLines.class.getName(), "SUM");
                toPick = (Double) util.executeSingleResultQuery(query, userData);
            }
            if (toPick == null) {
                toPick = 0d;
            }
            salesOrderPostLine.setPickQuantity(salesOrderPostLine.getPickQuantity().subtract(util.getBigDecimal(reserved).subtract(util.getBigDecimal(toPick))));
        } else {
            //  salesOrderPostLine.setPickQuantity(salesOrderPostLine.getPickQuantity().subtract(util.getBigDecimal(reserved)));
        }
        BigDecimal requiredQuantity = salesOrderPostLine.getPickQuantity();

        if (requiredQuantity.compareTo(new BigDecimal(BigInteger.ZERO)) <= 0) {
            return salesOrderPostLine;
        }

        if (SalesOrderTypes.SALES_ORDER.toString().equals(salesOrderType)) {
            //Find Order transaction
            EMCQuery transQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            transQuery.addAnd("itemDimId", String.valueOf(salesOrderPostLine.getDimensionId()));
            transQuery.addAnd("itemId", salesOrderPostLine.getItemId());
            transQuery.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            transQuery.addAnd("direction", InventoryTransactionDirection.OUT.toString());
            transQuery.addAnd("refNumber", salesOrderNo);
            transQuery.addAnd("refType", InventoryTransactionsRefType.Sales_Order);
            transQuery.addAnd("type", InventoryTransactionTypes.SO);
            transQuery.addAnd("transId", salesOrderPostLine.getTransactionNumber());
            orderTrans = (InventoryTransactions) util.executeSingleResultQuery(transQuery, userData);
            if (orderTrans == null) {
                throw new EMCStockException("Failed to find ordered transaction.");
            }
        } else {
            orderTrans = new InventoryTransactions();
            orderTrans.setTransId(salesOrderPostLine.getTransactionNumber());
            orderTrans.setCompanyId(util.getCompanyId(InventoryTransactions.class.getName(), userData));
            orderTrans.setDirection(InventoryTransactionDirection.OUT.toString());
            orderTrans.setType(InventoryTransactionTypes.SO.toString());
            orderTrans.setRefType(InventoryTransactionsRefType.Sales_Order.toString());
            orderTrans.setRefNumber(salesOrderNo);
            orderTrans.setStatus(InventoryTransactionStatus.ORDERED.toString());
            orderTrans.setExpectedDate(salesOrderPostLine.getRequiredDate());
            orderTrans.setQuantity(salesOrderPostLine.getPickQuantity().doubleValue());
        }
        BigDecimal summaryQuantiy;
        double reserveQuantity;
        //Handle Manual registration
        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderPostRegister.class);
        query.addAnd("masterId", salesOrderPostLine.getPostMasterId());
        query.addAnd("transId", salesOrderPostLine.getTransactionNumber());
        List<SOPSalesOrderPostRegister> postRegisterList = util.executeGeneralSelectQuery(query, userData);
        for (SOPSalesOrderPostRegister postRegister : postRegisterList) {
            InventoryDimensionTable dimRecord = dimIdLocal.getDimensionRecord(salesOrderPostLine.getDimensionId(), userData);
            long dimRecordId = dimIdLocal.getDimRecordId(postRegister.getBatch(), dimRecord.getDimension1Id(), dimRecord.getDimension2Id(), dimRecord.getDimension3Id(),
                    postRegister.getWarehouse(), postRegister.getLocation(), postRegister.getPallet(), postRegister.getSerial(), userData);
            //Check Available quantity
            summaryQuantiy = new BigDecimal(postRegister.getQuantity());
            if (requiredQuantity.compareTo(summaryQuantiy) > 0) {
                reserveQuantity = summaryQuantiy.doubleValue();
                requiredQuantity = requiredQuantity.subtract(summaryQuantiy);
            } else {
                reserveQuantity = requiredQuantity.doubleValue();
                requiredQuantity = requiredQuantity.subtract(requiredQuantity);
            }
            //Reserves the stock
            reserveStock(salesOrderNo, InventoryTransactionsRefType.Sales_Order, InventoryTransactionTypes.SO, salesOrderPostLine.getItemId(),
                    dimRecordId, salesOrderPostLine.getTransactionNumber(), reserveQuantity,
                    orderTrans, false, userData);
            //Checks quantity reserved
            if (requiredQuantity.compareTo(new BigDecimal(BigInteger.ZERO)) == 0) {
                break;
            }
        }
        //Handle System registration
        if (requiredQuantity.compareTo(new BigDecimal(BigInteger.ZERO)) != 0) {
            InventoryDimensionTable dimRecord = dimIdLocal.getDimensionRecord(salesOrderPostLine.getDimensionId(), userData);
            //Find available Stock
            query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
            query.addAnd("itemId", salesOrderPostLine.getItemId());
            query.addAnd("dimension1", dimRecord.getDimension1Id());
            query.addAnd("dimension2", dimRecord.getDimension2Id());
            query.addAnd("dimension3", dimRecord.getDimension3Id());
            query.addAnd("warehouse", dimRecord.getWarehouseId());
          
            query.addFieldAggregateFunction("physicalAvailable", "SUM");
            query.addField("itemDimId");
            query.addGroupBy("itemId");
            query.addGroupBy("itemDimId");
            query.addOrHavingAggregateFunction("SUM", InventorySummary.class.getName(), "physicalAvailable", EMCQueryConditions.GREATER_THAN, 0);

            List<Object[]> summaryList = util.executeGeneralSelectQuery(query, userData);
            for (Object[] summary : summaryList) {
                summaryQuantiy = new BigDecimal((Double) summary[0]);
                //Check Available quantity
                if (requiredQuantity.compareTo(summaryQuantiy) > 0) {
                    reserveQuantity = summaryQuantiy.doubleValue();
                    requiredQuantity = requiredQuantity.subtract(summaryQuantiy);
                } else {
                    reserveQuantity = requiredQuantity.doubleValue();
                    requiredQuantity = requiredQuantity.subtract(requiredQuantity);
                }
                //Reserves the stock
                reserveStock(salesOrderNo, InventoryTransactionsRefType.Sales_Order, InventoryTransactionTypes.SO, salesOrderPostLine.getItemId(),
                        (Long) summary[1], salesOrderPostLine.getTransactionNumber(), reserveQuantity,
                        orderTrans, false, userData);
                //Checks quantity reserved
                if (requiredQuantity.compareTo(new BigDecimal(BigInteger.ZERO)) == 0) {
                    break;
                }
            }
            //Checks quantity reserved
            if (requiredQuantity.compareTo(new BigDecimal(BigInteger.ZERO)) != 0) {
                throw new EMCStockException("Not enough stock available.");
            }
        }
        return salesOrderPostLine;
    }

    /**
     * Updates the Inventory Summary table to indicate the outstanding items on a Blanket Order.
     * @param salesOrderLine Blanket Order line.
     * @param quantity Quantity to add to summary.  Negative quantities will be deducted.
     * @param userData User data.
     * @return The given Blanket Order line.
     * @throws EMCStockExceptionm, EMCEntityBeanException
     */
    @Override
    public EMCTable orderBOLine(SOPSalesOrderLines theLine, BigDecimal qty, EMCUserData userData) throws EMCStockException, EMCEntityBeanException {
        return updateBlanketOrderOnHand(RequirementsPlanningReferenceTypes.SALES_ORDER, theLine.getRecordID(), theLine.getItemId(),
                                        theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(),
                                        qty, theLine.getUom(), userData);
    }

    /**
     * Reallocates stock.
     * @param fromTransaction Transaction on which reserved quantity should be decreased.
     * @param toSalesOrderLine Sales order line to which stock should be reallocated.  May be null if stock should only be unreserved.
     * @param quantity Quantity to reallocate.
     * @param uom Unit of measure in which quantity is expressed.  This should always be in the item base UoM.
     * @param toCustomer Customer id of customer to whom stock is being reallocated.
     * @param unreserve Indicates whether stock should only be unreserved and not reallocated.
     * @param userData User data.
     * @return fromTransaction
     * @throws emc.framework.EMCEntityBeanException
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCEntityClass reallocateSOLineStock(InventoryTransactions fromTransaction, SOPSalesOrderLines toSalesOrderLine, BigDecimal quantity, String toCustomer, boolean unreserve, String salesOrderType, EMCUserData userData) throws EMCEntityBeanException, EMCStockException {
        //Unreserve full quantity
        //fromTransaction.setQuantity(0);
        trans.update(fromTransaction, userData);
        updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, fromTransaction, userData);

        //Rereserve difference between original quantity & reallocated quantity.
        fromTransaction.setQuantity(fromTransaction.getQuantity() - quantity.doubleValue());

        if (util.compareDouble(fromTransaction.getQuantity(), 0.0) == 0) {
            deleteTransactionAndOnHand(fromTransaction, userData);
        } else {
            //Rereserve
            trans.update(fromTransaction, userData);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, fromTransaction, userData);
        }

        //Get Sales Order line
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
        query.addAnd("inventTransId", fromTransaction.getTransId());

        SOPSalesOrderLines fromSalesOrderLine = (SOPSalesOrderLines) util.executeSingleResultQuery(query, userData);

        if (fromSalesOrderLine == null) {
            throw new EMCEntityBeanException("Sales order line not found");
        }

        long orderedDimId = dimIdLocal.getDimRecordId(fromSalesOrderLine.getDimension1(), fromSalesOrderLine.getDimension2(), fromSalesOrderLine.getDimension3(), fromSalesOrderLine.getWarehouse(), userData);

        //Update order transaction for from line
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
        query.addAnd("refNumber", fromSalesOrderLine.getSalesOrderNo());
        query.addAnd("refType", InventoryTransactionsRefType.Sales_Order.toString());
        query.addAnd("type", InventoryTransactionTypes.SO.toString());
        query.addAnd("itemId", fromSalesOrderLine.getItemId());
        query.addAnd("itemDimId", orderedDimId);
        query.addAnd("direction", InventoryTransactionDirection.OUT);

        InventoryTransactions fromOrderedTransaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

        if (!SalesOrderTypes.BLANKET_ORDER.toString().equals(salesOrderType)) {
            if (fromOrderedTransaction == null) {
                fromOrderedTransaction = new InventoryTransactions();
                fromOrderedTransaction.setRefNumber(fromSalesOrderLine.getSalesOrderNo());
                fromOrderedTransaction.setRefType(InventoryTransactionsRefType.Sales_Order.toString());
                fromOrderedTransaction.setType(InventoryTransactionTypes.SO.toString());
                fromOrderedTransaction.setExpectedDate(fromSalesOrderLine.getRequiredDate());
                fromOrderedTransaction.setTransId(fromSalesOrderLine.getInventTransId());
                fromOrderedTransaction.setCost(0);  //TODO:  Get price from item master bean
                fromOrderedTransaction.setStatus(InventoryTransactionStatus.ORDERED.toString());
                fromOrderedTransaction.setDirection(InventoryTransactionDirection.OUT.toString());
                fromOrderedTransaction.setItemId(fromSalesOrderLine.getItemId());
                fromOrderedTransaction.setItemDimId(orderedDimId);

                trans.insert(fromOrderedTransaction, userData);
            }

            fromOrderedTransaction.setQuantity(fromOrderedTransaction.getQuantity() + quantity.doubleValue());
            trans.update(fromOrderedTransaction, userData);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, fromOrderedTransaction, userData);
        }

        if (!unreserve) {
            //Get total already reserved and delivered against the specified order
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            query.addFieldAggregateFunction("quantity", "SUM");
            query.addAnd("refNumber", toSalesOrderLine.getSalesOrderNo());
            query.addAnd("refType", InventoryTransactionsRefType.Sales_Order.toString());
            query.openAndConditionBracket();
            query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
            query.addOr("status", InventoryTransactionStatus.DELIVERED.toString());
            query.closeConditionBracket();
            query.addAnd("type", InventoryTransactionTypes.SO.toString());
            query.addAnd("itemId", fromTransaction.getItemId());
            query.addAnd("direction", InventoryTransactionDirection.OUT);
            query.addAnd("transId", toSalesOrderLine.getInventTransId());

            Double totalUsed = (Double) util.executeSingleResultQuery(query, userData);

            if (totalUsed == null) {
                totalUsed = 0.0;
            }

            if (util.compareDouble(totalUsed + quantity.doubleValue(), toSalesOrderLine.getQuantity().doubleValue()) > 0) {
                throw new EMCEntityBeanException("You may not reserve and deliver more items than the original amount on Sales Order: " + toSalesOrderLine.getSalesOrderNo());
            }

            //Attempt to find matching transaction against Sales Order and dimensions on from transaction.
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
            query.addAnd("refNumber", toSalesOrderLine.getSalesOrderNo());
            query.addAnd("refType", InventoryTransactionsRefType.Sales_Order.toString());
            query.addAnd("type", InventoryTransactionTypes.SO.toString());
            query.addAnd("itemId", fromTransaction.getItemId());
            query.addAnd("itemDimId", fromTransaction.getItemDimId());
            query.addAnd("direction", InventoryTransactionDirection.OUT);
            query.addAnd("transId", toSalesOrderLine.getInventTransId());

            InventoryTransactions toTransaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

            if (toTransaction == null) {
                //Create new transaction.
                toTransaction = new InventoryTransactions();
                toTransaction.setRefNumber(toSalesOrderLine.getSalesOrderNo());
                toTransaction.setRefType(InventoryTransactionsRefType.Sales_Order.toString());
                toTransaction.setType(InventoryTransactionTypes.SO.toString());
                toTransaction.setExpectedDate(toSalesOrderLine.getRequiredDate());
                toTransaction.setTransId(toSalesOrderLine.getInventTransId());
                toTransaction.setCost(0);  //TODO:  Get price from item master bean
                toTransaction.setStatus(InventoryTransactionStatus.RESERVED.toString());
                toTransaction.setDirection(InventoryTransactionDirection.OUT.toString());
                toTransaction.setItemId(fromSalesOrderLine.getItemId());
                toTransaction.setItemDimId(fromTransaction.getItemDimId());
                toTransaction.setCustomerId(toCustomer);

                trans.insert(toTransaction, userData);
            } else {
                //Unreserve to transaction in full.  Reservation code checks whether total quantity
                //on transaction is available, not just difference.  Unreserve what has already been
                //reserved to ensure that the full quantity will be available.
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, toTransaction, userData);
            }

            toTransaction.setQuantity(toTransaction.getQuantity() + quantity.doubleValue());

            trans.update(toTransaction, userData);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, toTransaction, userData);

            //Find and update ordered transaction
            orderedDimId = dimIdLocal.getDimRecordId(toSalesOrderLine.getDimension1(), toSalesOrderLine.getDimension2(), toSalesOrderLine.getDimension3(), toSalesOrderLine.getWarehouse(), userData);

            query.removeAnd("status");
            query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());

            query.removeAnd("itemDimId");
            query.addAnd("itemDimId", orderedDimId);

            //Cater for more than one ordered transaction
            List<InventoryTransactions> orderedTransactions = (List<InventoryTransactions>) util.executeGeneralSelectQuery(query, userData);

            //Only use first transaction
            for (InventoryTransactions orderedTransaction : orderedTransactions) {
                orderedTransaction.setQuantity(orderedTransaction.getQuantity() - quantity.doubleValue());

                if (orderedTransaction.getQuantity() < 0) {
                    throw new EMCEntityBeanException("Over reservation is not allowed.");
                } else if (util.compareDouble(orderedTransaction.getQuantity(), 0) == 0) {
                    deleteTransactionAndOnHand(orderedTransaction, userData);
                } else {
                    trans.update(orderedTransaction, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, orderedTransaction, userData);
                }
            }
        }

        return fromTransaction;
    }
}
