/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.inventory;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryLocationLocal;
import emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal;
import emc.bus.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensionsLocal;
import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.journals.movement.MovementJournalPostCases;
import emc.bus.inventory.journals.movement.MovementJournalTxManager;
import emc.bus.inventory.journals.movement.MovementJournalTxManagerStatus;
import emc.bus.inventory.register.removeregister.InventoryRemoveRegisterLocal;
import emc.bus.inventory.stocktakelogger.InventoryStockTakeLoggerLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionsSuperClass;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryStocktakeUnreserved;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.movestock.InventoryMoveStockLines;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.entity.inventory.picking.rereservepicklist.InventoryReReservePickList;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerInventoryMessageEnum;
import emc.server.filehandeler.EMCFileHandlerLocal;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryTransactionLogicBean extends TransactionsSuperClass implements InventoryTransactionLogicLocal {

    @EJB
    private BaseJournalDefinitionLocal definitionBean;
    @EJB
    private InventoryAdditionalDimensionsLocal addDimBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryItemDimensionCombinationsLocal combinationBean;
    @EJB
    private InventoryLocationLocal locationBean;
    @EJB
    private InventoryRemoveRegisterLocal removeRegBean;
    @EJB
    private EMCFileHandlerLocal fileHandlerBean;
    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @EJB
    private InventoryStockTakeLoggerLocal stockTakeLoggerBean;

    /**
     * Handles post of a journal line during capture and update
     *
     * @param journalLine
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCTable postJournalLine(InventoryJournalLines theLine, TransactionHelper th, EMCUserData userData)
            throws EMCStockException {
        if (this.isBlank(theLine.getTransId())) {
            throw new EMCStockException("No Transaction Id found for Journal line, could not generate Stock transaction.");
        } else {
            //check Journal type speed things up for stock take generation don't want to do check below 10000 times
            if (th.isStockTakeJournal()) {
                postStockTakeLine(theLine, th, userData);
                return null;
            }
            BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(theLine.getJournalNumber(), userData);
            switch (InventoryJournalTypes.fromString(definition.getJournalType())) {
                case MOVEMENT:
                    return postMovementJournalLine(theLine, (InventoryRemoveRegister) th.getRelatedEntity(), th.getPostCase(), userData);
                case STOCKTAKE:
                    postStockTakeLine(theLine, th, userData);
                    break;
                case TRANSFER:
                    postTransferJournalLine(theLine, userData);
                    break;
            }
        }

        return null;
    }

    /**
     * Handles the post of a Journal line capture and update Note : Two
     * transactions are generated and maintained - one is going out e.g. FROM
     * the other is coming in e.g. TO the line qty must always be positive
     *
     * @param journalLine
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    private boolean postTransferJournalLine(InventoryJournalLines theLine, EMCUserData userData)
            throws EMCStockException {
        //see if from exists
        if (util.compareDouble(theLine.getQuantity(), 0) < 0) {
            throw new EMCStockException("Transfer Journal quantities must be positive");
        }
        Object orderTx = null;
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class.getName());
        qu.addAnd("transId", theLine.getTransId());
        qu.addAnd("refNumber", theLine.getJournalNumber());
        qu.addAnd("direction", InventoryTransactionDirection.OUT.toString());

        orderTx = util.executeSingleResultQuery(qu, userData);
        if (orderTx == null) {
            //create from tx
            InventoryTransactions fromTx = new InventoryTransactions();
            fromTx.setTransId(theLine.getTransId());
            fromTx.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
            fromTx.setDirection(InventoryTransactionDirection.OUT.toString());
            fromTx.setType(InventoryTransactionTypes.JR.toString());
            fromTx.setRefType(InventoryTransactionsRefType.Journal_Line.toString());
            fromTx.setRefNumber(theLine.getJournalNumber());
            fromTx.setStatus(InventoryTransactionStatus.ORDERED.toString());
            fromTx.setQuantity(theLine.getQuantity());
            fromTx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(), theLine.getLocation(), theLine.getPallet(), theLine.getSerial(), userData));
            fromTx.setItemId(theLine.getItemId());
            fromTx.setCost(theLine.getTotalCost());
            try {
                if (util.compareDouble(fromTx.getQuantity(), 0) != 0) {
                    trans.insert(fromTx, userData);
                }

            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
            }
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, fromTx, userData);
            //also create in transaction
            InventoryTransactions toTx = (InventoryTransactions) this.doClone(fromTx, userData);
            toTx.setDirection(InventoryTransactionDirection.IN.toString());
            toTx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getToBatch(), theLine.getToDimension1(), theLine.getToDimension2(), theLine.getToDimension3(), theLine.getToWarehouse(), theLine.getToLocation(), theLine.getToPallet(), theLine.getToSerial(), userData));
            toTx.setItemId(theLine.getToItemId());
            try {
                if (util.compareDouble(toTx.getQuantity(), 0) != 0) {
                    trans.insert(toTx, userData);
                }

            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
            }
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, toTx, userData);

        } else {
            InventoryTransactions tx = (InventoryTransactions) orderTx;
            //Unreserve out stock
            if (util.compareDouble(tx.getQuantity(), 0) != 0) {
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, tx, userData);
            }

            tx.setQuantity(Math.abs(theLine.getQuantity()));
            tx.setCost(theLine.getTotalCost());
            tx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(), theLine.getLocation(), theLine.getPallet(), theLine.getSerial(), userData));
            tx.setItemId(theLine.getItemId());
            //also find toTx
            qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class.getName());
            qu.addAnd("transId", theLine.getTransId());
            qu.addAnd("refNumber", theLine.getJournalNumber());
            qu.addAnd("direction", InventoryTransactionDirection.IN.toString());
            InventoryTransactions toTx = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);
            if (toTx == null) {
                throw new EMCStockException("Could not find IN Transactions for Transfer.");
            }
            toTx.setQuantity(Math.abs(theLine.getQuantity()));
            toTx.setCost(theLine.getTotalCost());
            toTx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getToBatch(), theLine.getToDimension1(), theLine.getToDimension2(), theLine.getToDimension3(), theLine.getToWarehouse(), theLine.getToLocation(), theLine.getToPallet(), theLine.getToSerial(), userData));
            toTx.setItemId(theLine.getToItemId());
            try {
                if (util.compareDouble(tx.getQuantity(), 0) != 0) {
                    trans.update(tx, userData);
                    trans.update(toTx, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, tx, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, toTx, userData);
                } else {
                    trans.delete(tx, userData);
                    trans.delete(toTx, userData);
                    EMCQuery inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                    inventSumQu.addAnd("inventoryTransRef", tx.getRecordID());
                    InventorySummary sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                    itemSumLocal.delete(sumTx, userData);
                    inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                    inventSumQu.addAnd("inventoryTransRef", toTx.getRecordID());
                    sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                    itemSumLocal.delete(sumTx, userData);
                }

            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not update Journal stock transaction " + entBex.getMessage());
            }

        }
        return true;
    }

    /**
     *
     * @param journalLine
     * @param userData
     * @return
     * @throws EMCStockException
     */
    private boolean postStockTakeLine(InventoryJournalLines theLine, TransactionHelper txH, EMCUserData userData) throws EMCStockException {
        Object orderTx = null;
        if (!txH.isStockTakeJournal()) {
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class.getName());
            qu.addAnd("transId", theLine.getTransId());
            qu.addAnd("refNumber", theLine.getJournalNumber());

            orderTx = util.executeSingleResultQuery(qu, userData);
        }

        if (orderTx == null) {
            InventoryTransactions newTx = new InventoryTransactions();
            newTx.setTransId(theLine.getTransId());
            newTx.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
            newTx.setDirection((theLine.getQuantity() - theLine.getCountQOH() > 0 ? InventoryTransactionDirection.IN.toString() : InventoryTransactionDirection.OUT.toString()));
            newTx.setType(InventoryTransactionTypes.JR.toString());
            newTx.setRefType(InventoryTransactionsRefType.Journal_Line.toString());
            newTx.setRefNumber(theLine.getJournalNumber());
            newTx.setStatus(InventoryTransactionStatus.ORDERED.toString());
            newTx.setQuantity(Math.abs(theLine.getQuantity() - theLine.getCountQOH()));
            newTx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(), theLine.getLocation(), theLine.getPallet(), theLine.getSerial(), userData));
            newTx.setItemId(theLine.getItemId());
            newTx.setCost(theLine.getTotalCost());

            try {
                if (util.compareDouble(newTx.getQuantity(), 0) != 0) {
                    trans.insert(newTx, userData);
                }

            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
            }
            updateOnHand(util.compareDouble(theLine.getQuantity() - theLine.getCountQOH(), 0) >= 0 ? InventorySummaryUpdateOptions.ORDERED_IN : InventorySummaryUpdateOptions.ORDERED_OUT,
                    newTx, userData);
            orderTx = newTx;

        } else {
            InventoryTransactions tx = (InventoryTransactions) orderTx;

            tx.setQuantity(Math.abs(theLine.getQuantity() - theLine.getCountQOH()));
            tx.setCost(theLine.getTotalCost());
            tx.setDirection((util.compareDouble(theLine.getQuantity() - theLine.getCountQOH(), 0) > 0 ? InventoryTransactionDirection.IN.toString() : InventoryTransactionDirection.OUT.toString()));
            tx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(), theLine.getLocation(), theLine.getPallet(), theLine.getSerial(), userData));
            tx.setItemId(theLine.getItemId());
            try {
                if (tx.getQuantity() != 0) {
                    trans.update(tx, userData);
                    updateOnHand(util.compareDouble(theLine.getQuantity() - theLine.getCountQOH(), 0) >= 0 ? InventorySummaryUpdateOptions.ORDERED_IN : InventorySummaryUpdateOptions.ORDERED_OUT,
                            tx, userData);
                } else {
                    trans.delete(tx, userData);
                    EMCQuery inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                    inventSumQu.addAnd("inventoryTransRef", tx.getRecordID());
                    InventorySummary sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                    itemSumLocal.delete(sumTx, userData);
                }

            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not update Journal stock transaction " + entBex.getMessage());
            }

        }

        return true;
    }

    //Used for movement journals.
    private MovementJournalTxManager getTxManager(InventoryJournalLines journalLine, EMCUserData userData) {
        MovementJournalTxManager txManager = new MovementJournalTxManager();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        query.addAnd("transId", journalLine.getTransId());
        query.addAnd("refNumber", journalLine.getJournalNumber());
        query.addAnd("refType", InventoryTransactionsRefType.Journal_Line);

        List<InventoryTransactions> transactions = (List<InventoryTransactions>) util.executeGeneralSelectQuery(query, userData);

        for (InventoryTransactions transaction : transactions) {
            if (isTransOrdered(transaction, userData)) {
                txManager.setOrderedTrans(transaction);
            } else {
                txManager.addReservedTrans(transaction);
            }
        }

        return txManager;
    }

    /**
     *
     * @param journalLine
     * @param register
     * @param postCase
     * @param userData
     * @return
     */
    private InventoryTransactions postMovementJournalLine(InventoryJournalLines journalLine, InventoryRemoveRegister register, MovementJournalPostCases postCase, EMCUserData userData) throws EMCStockException {
        try {
            MovementJournalTxManager txManager = getTxManager(journalLine, userData);
            if (postCase != null) {
                switch (postCase) {
                    case JOURNAL_ITEM_CHANGED:
                        movementJournalLineItemChanged(journalLine, txManager, userData);
                        break;
                    case JOURNAL_LINE_CAPTURED:
                        movementJournalLineCaptured(journalLine, txManager, userData);
                        break;
                    case JOURNAL_LINE_DELETED:
                        movementJournalLineDeleted(journalLine, txManager, userData);
                        break;
                    case JOURNAL_QTY_DOWN:
                        movementJournalLineQtyDecreased(journalLine, txManager, userData);
                        break;
                    case JOURNAL_QTY_UP:
                        movementJournalLineQtyIncreased(journalLine, txManager, userData);
                        break;

                    case REGISTER_CAPTURED:
                        registerLineCaptured(journalLine, register, txManager, userData);
                        break;
                    case REGISTER_DELETED:
                        registerLineDeleted(journalLine, register, txManager, userData);
                        break;
                    case REG_ITEM_CHANGED:
                        changeRegisterLineItem(journalLine, register, txManager, userData);
                        break;
                    case REG_QTY_DOWN:
                        registerQuantityDown(journalLine, register, txManager, userData);
                        break;
                    case REG_QTY_UP:
                        registerQuantityUp(journalLine, register, txManager, userData);
                        break;

                    default:
                        break;
                }

                for (InventoryTransactions transaction : txManager.getTransactionsToDelete()) {
                    transaction.setQuantity(txManager.getTransactionUnreservedQty(transaction));

                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, transaction, userData);

                    deleteTransactionAndOnHand(transaction, userData);
                }

                for (InventoryTransactions transaction : txManager.getTransactionsToInsert()) {
                    if (util.compareDouble(transaction.getQuantity(), 0) != 0) {
                        trans.insert(transaction, userData);

                        if (util.compareDouble(journalLine.getQuantity(), 0) < 0) {
                            if (transaction == txManager.getOrderedTransaction()) {
                                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, transaction, userData);
                            } else {
                                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, transaction, userData);
                            }
                        } else {
                            updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, transaction, userData);
                        }
                    }
                }

                for (InventoryTransactions transaction : txManager.getTransactionsToUpdate()) {
                    if (util.compareDouble(transaction.getQuantity(), 0) == 0) {
                        deleteTransactionAndOnHand(transaction, userData);
                    } else if (util.compareDouble(journalLine.getQuantity(), 0) < 0) {
                        if (transaction == txManager.getOrderedTransaction()) {
                            trans.update(transaction, userData);
                            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, transaction, userData);
                        } else {
                            //Check whether anything should be unreserved.  If
                            //transaction quantity is zero, it will be deleted in
                            //its entirety and there is no need to unreserve here.
                            double unreserve = txManager.getQuantityToUnreserve(transaction);
                            double reserve = transaction.getQuantity();
                            if (util.compareDouble(unreserve, 0) > 0) {
                                //Unreserve all stock.
                                transaction.setQuantity(unreserve + reserve);
                                trans.update(transaction, userData);
                                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, transaction, userData);
                                //Reserve actual quantity to be reserved.
                                transaction.setQuantity(reserve);
                                trans.update(transaction, userData);
                                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, transaction, userData);
                            } else {
                                //Check whether this transaction was reserved previously.  If so, unreserve first
                                InventoryTransactions persisted = (InventoryTransactions) util.findDetachedPersisted(transaction, userData);
                                if (persisted != null && InventoryTransactionStatus.fromString(persisted.getStatus()) == InventoryTransactionStatus.RESERVED) {
                                    double quantity = transaction.getQuantity();
                                    transaction.setQuantity(persisted.getQuantity());
                                    trans.update(transaction, userData);
                                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, transaction, userData);
                                    transaction.setQuantity(quantity);
                                }
                                trans.update(transaction, userData);
                                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, transaction, userData);
                            }
                        }
                    } else {
                        updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, transaction, userData);
                    }
                }
            }
            return txManager.getOrderedTransaction();
        } catch (EMCEntityBeanException ex) {
            throw new EMCStockException(ex.getMessage());
        }
    }

    private void movementJournalLineCaptured(InventoryJournalLines line, MovementJournalTxManager txManager, EMCUserData userData) throws EMCStockException {
        long dimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);

        orderMovementJournalLine(line.getItemId(), dimId, line.getQuantity(), line.getTransId(), line.getJournalNumber(), txManager, line.getCost(), userData);

        //Check whether we can attempt to reserve stock.
        InventoryItemDimensionGroup dimGroup = itemMasterBean.getItemDimensionGroupRecord(line.getItemId(), userData);
        InventoryDimensionTable dimTable = dimIdLocal.getDimensionRecord(dimId, userData);

        if (util.compareDouble(0, line.getQuantity()) > 0 && allActiveFieldsPopulated(dimGroup, dimTable)) {
            reserveMovementJournalLine(line.getItemId(), dimId, line.getQuantity(), line.getTransId(), line.getJournalNumber(), txManager, line.getCost(), userData);
        }
    }

    private void changeRegisterLineItem(InventoryJournalLines line, InventoryRemoveRegister register, MovementJournalTxManager txManager, EMCUserData userData) throws EMCStockException {
        InventoryRemoveRegister persistedRegister = (InventoryRemoveRegister) util.findDetachedPersisted(register, userData);

        long oldDimId = dimIdLocal.getDimRecordId(persistedRegister.getBatch(), line.getDimension1(), line.getDimension2(), line.getDimension3(), persistedRegister.getWarehouse(),
                persistedRegister.getLocation(), persistedRegister.getPallet(), persistedRegister.getSerial(), userData);

        long newDimId = dimIdLocal.getDimRecordId(register.getBatch(), line.getDimension1(), line.getDimension2(), line.getDimension3(), register.getWarehouse(),
                register.getLocation(), register.getPallet(), register.getSerial(), userData);

        if (canReserveAgainstLine(line, userData)) {
            specialLineUnreserve(line, oldDimId, persistedRegister.getQuantity(), txManager, userData);

            specialLineReserve(line, newDimId, persistedRegister.getQuantity(), txManager, userData);
        } else {
            unreserveMovementJournalLine(line.getItemId(), oldDimId, persistedRegister.getQuantity(), line, txManager, userData);

            reserveMovementJournalLine(line.getItemId(), newDimId, register.getQuantity(), line.getTransId(), line.getJournalNumber(), txManager, line.getCost(), userData);
        }
    }

    private void movementJournalLineQtyIncreased(InventoryJournalLines line, MovementJournalTxManager txManager, EMCUserData userData) throws EMCStockException {
        deleteMovementJournalRegisteredLines(line.getTransId(), line.getJournalNumber(), userData);

        InventoryTransactions orderedTx = txManager.getOrderedTransaction();

        long lineDimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);

        //Reset quantity and reorder.  Ensure that we have an ordered transaction, if it was created by the above method.
        if (orderedTx == null) {
            MovementJournalTxManager newTxManager = getTxManager(line, userData);
            orderedTx = newTxManager.getOrderedTransaction();
            txManager.setOrderedTrans(orderedTx);

            //If a reserved transaction still exists against the line, with the line dim id, we kill it.
            InventoryTransactions lineResTrans = txManager.getReservedTransaction(line.getItemId(), lineDimId, userData);

            if (lineResTrans != null) {
                double unreserveQty = lineResTrans.getQuantity();
                lineResTrans.setQuantity(0);
                txManager.setTransactionStatus(lineResTrans, unreserveQty, MovementJournalTxManagerStatus.DELETE);
            }
        }

        if (orderedTx != null) {
            orderedTx.setQuantity(0);
        }

        orderMovementJournalLine(line.getItemId(), lineDimId, line.getQuantity(), line.getTransId(), line.getJournalNumber(), txManager, line.getCost(), userData);
    }

    private void movementJournalLineQtyDecreased(InventoryJournalLines line, MovementJournalTxManager txManager, EMCUserData userData) throws EMCStockException {
        deleteMovementJournalRegisteredLines(line.getTransId(), line.getJournalNumber(), userData);

        InventoryTransactions orderedTx = txManager.getOrderedTransaction();

        long lineDimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);

        //Reset quantity and reorder.  Ensure that we have an ordered transaction, if it was created by the above method.
        if (orderedTx == null) {
            MovementJournalTxManager newTxManager = getTxManager(line, userData);
            orderedTx = newTxManager.getOrderedTransaction();
            txManager.setOrderedTrans(orderedTx);

            //If a reserved transaction still exists against the line, with the line dim id, we kill it.
            InventoryTransactions lineResTrans = txManager.getReservedTransaction(line.getItemId(), lineDimId, userData);

            if (lineResTrans != null) {
                double unreserveQty = lineResTrans.getQuantity();
                lineResTrans.setQuantity(0);
                txManager.setTransactionStatus(lineResTrans, unreserveQty, MovementJournalTxManagerStatus.DELETE);
            }
        }

        if (orderedTx != null) {
            orderedTx.setQuantity(0);
        }

        orderMovementJournalLine(line.getItemId(), lineDimId, line.getQuantity(), line.getTransId(), line.getJournalNumber(), txManager, line.getCost(), userData);
    }

    private void movementJournalLineDeleted(InventoryJournalLines line, MovementJournalTxManager txManager, EMCUserData userData) throws EMCStockException {
        deleteMovementJournalRegisteredLines(line.getTransId(), line.getJournalNumber(), userData);

        InventoryTransactions orderedTx = txManager.getOrderedTransaction();

        //Reset quantity and reorder.  Ensure that we have an ordered transaction, if it was created by the above method.
        if (orderedTx == null) {
            MovementJournalTxManager newTxManager = getTxManager(line, userData);
            orderedTx = newTxManager.getOrderedTransaction();
            txManager.setOrderedTrans(orderedTx);

            //If a reserved transaction still exists against the line, with the line dim id, we kill it.
            long lineDimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);

            InventoryTransactions lineResTrans = txManager.getReservedTransaction(line.getItemId(), lineDimId, userData);

            if (lineResTrans != null) {
                double unreserveQty = lineResTrans.getQuantity();
                lineResTrans.setQuantity(0);
                txManager.setTransactionStatus(lineResTrans, unreserveQty, MovementJournalTxManagerStatus.DELETE);
            }
        }

        if (orderedTx != null) {
            orderedTx.setQuantity(0);

            txManager.setTransactionStatus(orderedTx, 0, MovementJournalTxManagerStatus.DELETE);
        }
    }

    private InventoryTransactions orderMovementJournalLine(String itemId, long dimId, double quantity, String transId, String journalNumber, MovementJournalTxManager txManager, double cost, EMCUserData userData) {
        InventoryTransactions orderedTransaction = txManager.getOrderedTransaction();

        if (orderedTransaction == null) {
            orderedTransaction = new InventoryTransactions();
            txManager.setOrderedTrans(orderedTransaction);
            txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.INSERT);
        } else {
            txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.UPDATE);
        }

        orderedTransaction.setTransId(transId);
        orderedTransaction.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
        orderedTransaction.setDirection((quantity > 0 ? InventoryTransactionDirection.IN.toString() : InventoryTransactionDirection.OUT.toString()));
        orderedTransaction.setType(InventoryTransactionTypes.JR.toString());
        orderedTransaction.setRefType(InventoryTransactionsRefType.Journal_Line.toString());
        orderedTransaction.setRefNumber(journalNumber);
        orderedTransaction.setStatus(InventoryTransactionStatus.ORDERED.toString());
        orderedTransaction.setItemId(itemId);
        orderedTransaction.setItemDimId(dimId);
        orderedTransaction.setQuantity(orderedTransaction.getQuantity() + Math.abs(quantity));

        if (util.checkUserData(5, true, userData)) {
            //Should always be empty except for Batch Consolidations
            List<InventoryTransactions> reservedTx = txManager.getReservedTransactions();

            for (InventoryTransactions reserved : reservedTx) {
                if (!MovementJournalTxManagerStatus.DELETE.equals(txManager.getTransactionStatus(reserved))) {
                    orderedTransaction.setQuantity(orderedTransaction.getQuantity() - Math.abs(reserved.getQuantity()));
                }
            }
        }

        //double cost = itemMasterBean.getCostPrice(null, itemId, dimId, userData);

        orderedTransaction.setCost(cost * orderedTransaction.getQuantity());

        return orderedTransaction;
    }

    private void unreserveMovementJournalLine(String itemId, long dimId, double quantityToUnreserve, InventoryJournalLines line, MovementJournalTxManager txManager, EMCUserData userData) throws EMCStockException {
        InventoryTransactions orderedTransaction = txManager.getOrderedTransaction();

        if (orderedTransaction == null) {
            //Get dimId for ordered trans.
            long lineDimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);
            //We order zero items, just to get a hold of an ordered transaction to update.
            orderedTransaction = orderMovementJournalLine(itemId, lineDimId, 0, line.getTransId(), line.getJournalNumber(), txManager, line.getCost(), userData);
            txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.INSERT);
        } else {
            if (txManager.getTransactionStatus(orderedTransaction) == MovementJournalTxManagerStatus.IGNORE) {
                txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.UPDATE);
            }
        }

        InventoryTransactions reservedTransaction = txManager.getReservedTransaction(itemId, dimId, userData);

        //double resCost = itemMasterBean.getCostPrice(null, itemId, dimId, userData);
        //double orderCost = itemMasterBean.getCostPrice(null, itemId, orderedTransaction.getItemDimId(), userData);

        reservedTransaction.setQuantity(reservedTransaction.getQuantity() - Math.abs(quantityToUnreserve));
        reservedTransaction.setCost(line.getCost() * reservedTransaction.getQuantity());

        orderedTransaction.setQuantity(orderedTransaction.getQuantity() + Math.abs(quantityToUnreserve));
        orderedTransaction.setCost(line.getCost() * orderedTransaction.getQuantity());

        if (util.compareDouble(reservedTransaction.getQuantity(), 0) == 0) {
            double unreserveQty = quantityToUnreserve;
            txManager.setTransactionStatus(reservedTransaction, unreserveQty, MovementJournalTxManagerStatus.DELETE);
        } else {
            txManager.setTransactionStatus(reservedTransaction, 0, MovementJournalTxManagerStatus.UPDATE);
            txManager.setQuantityToUnreserve(reservedTransaction, Math.abs(quantityToUnreserve));
        }
    }

    private void reserveMovementJournalLine(String itemId, long dimId, double quantity, String transId, String journalNumber, MovementJournalTxManager txManager, double cost, EMCUserData userData) throws EMCStockException {
        InventoryTransactions orderedTransaction = txManager.getOrderedTransaction();


        if (orderedTransaction != null && txManager.getTransactionStatus(orderedTransaction) == MovementJournalTxManagerStatus.IGNORE) {
            txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.UPDATE);
        }

        InventoryTransactions reservedTransaction = txManager.getReservedTransaction(itemId, dimId, userData);

        if (reservedTransaction == null) {
            reservedTransaction = new InventoryTransactions();
            reservedTransaction.setTransId(transId);
            reservedTransaction.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
            reservedTransaction.setDirection(InventoryTransactionDirection.OUT.toString());
            reservedTransaction.setType(InventoryTransactionTypes.JR.toString());
            reservedTransaction.setRefType(InventoryTransactionsRefType.Journal_Line.toString());
            reservedTransaction.setRefNumber(journalNumber);
            reservedTransaction.setStatus(InventoryTransactionStatus.RESERVED.toString());
            reservedTransaction.setItemId(itemId);
            reservedTransaction.setItemDimId(dimId);

            txManager.addReservedTrans(reservedTransaction);
            txManager.setTransactionStatus(reservedTransaction, 0, MovementJournalTxManagerStatus.INSERT);
        } else {
            txManager.setTransactionStatus(reservedTransaction, 0, MovementJournalTxManagerStatus.UPDATE);
        }

        //double resCost = itemMasterBean.getCostPrice(null, itemId, dimId, userData);
        //double orderCost = itemMasterBean.getCostPrice(null, itemId, orderedTransaction.getItemDimId(), userData);

        reservedTransaction.setQuantity(reservedTransaction.getQuantity() + Math.abs(quantity));
        reservedTransaction.setCost(cost * reservedTransaction.getQuantity());

        if (orderedTransaction != null) {
            orderedTransaction.setQuantity(orderedTransaction.getQuantity() - Math.abs(quantity));
            orderedTransaction.setCost(cost * orderedTransaction.getQuantity());

            if (util.compareDouble(orderedTransaction.getQuantity(), 0) == 0) {
                if (txManager.getTransactionStatus(orderedTransaction) == MovementJournalTxManagerStatus.INSERT) {
                    txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.IGNORE);
                } else {
                    if (txManager.getTransactionStatus(orderedTransaction) == MovementJournalTxManagerStatus.UPDATE) {
                        txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.DELETE);
                    }

                }
            } else {
                if (txManager.getTransactionStatus(orderedTransaction) == MovementJournalTxManagerStatus.IGNORE) {
                    txManager.setTransactionStatus(orderedTransaction, 0, MovementJournalTxManagerStatus.UPDATE);
                }

            }
        }
    }

    private boolean registerLineCaptured(InventoryJournalLines line, InventoryRemoveRegister register, MovementJournalTxManager manager, EMCUserData userData) throws EMCStockException {
        long dimId = dimIdLocal.getDimRecordId(register.getBatch(), line.getDimension1(), line.getDimension2(), line.getDimension3(), register.getWarehouse(),
                register.getLocation(), register.getPallet(), register.getSerial(), userData);

        if (canReserveAgainstLine(line, userData)) {
            specialLineReserve(line, dimId, register.getQuantity(), manager, userData);
        } else {
            reserveMovementJournalLine(line.getItemId(), dimId, register.getQuantity(), line.getTransId(), line.getJournalNumber(), manager, line.getCost(), userData);
        }

        return true;
    }

    private boolean registerQuantityUp(InventoryJournalLines line, InventoryRemoveRegister register, MovementJournalTxManager manager, EMCUserData userData) throws EMCStockException {
        long dimId = dimIdLocal.getDimRecordId(register.getBatch(), line.getDimension1(), line.getDimension2(), line.getDimension3(), register.getWarehouse(),
                register.getLocation(), register.getPallet(), register.getSerial(), userData);

        InventoryRemoveRegister persistedRegister = (InventoryRemoveRegister) util.findDetachedPersisted(register, userData);

        double quantity = register.getQuantity() - persistedRegister.getQuantity();

        //TODO check for reservation against line.
        if (canReserveAgainstLine(line, userData)) {
            specialLineReserve(line, dimId, quantity, manager, userData);
        } else {
            reserveMovementJournalLine(line.getItemId(), dimId, quantity, line.getTransId(), line.getJournalNumber(), manager, line.getCost(), userData);
        }
        return true;
    }

    private boolean registerQuantityDown(InventoryJournalLines line, InventoryRemoveRegister register, MovementJournalTxManager manager, EMCUserData userData) throws EMCStockException {
        long dimId = dimIdLocal.getDimRecordId(register.getBatch(), line.getDimension1(), line.getDimension2(), line.getDimension3(), register.getWarehouse(),
                register.getLocation(), register.getPallet(), register.getSerial(), userData);

        InventoryRemoveRegister persistedRegister = (InventoryRemoveRegister) util.findDetachedPersisted(register, userData);

        double quantity = register.getQuantity() - persistedRegister.getQuantity();

        if (canReserveAgainstLine(line, userData)) {
            specialLineUnreserve(line, dimId, quantity, manager, userData);
        } else {
            unreserveMovementJournalLine(line.getItemId(), dimId, quantity, line, manager, userData);
        }

        return true;
    }

    private boolean registerLineDeleted(InventoryJournalLines line, InventoryRemoveRegister register, MovementJournalTxManager manager, EMCUserData userData) throws EMCStockException {
        long dimId = dimIdLocal.getDimRecordId(register.getBatch(), line.getDimension1(), line.getDimension2(), line.getDimension3(), register.getWarehouse(),
                register.getLocation(), register.getPallet(), register.getSerial(), userData);

        double quantity = register.getQuantity();

        if (canReserveAgainstLine(line, userData)) {
            specialLineUnreserve(line, dimId, quantity, manager, userData);
        } else {
            unreserveMovementJournalLine(line.getItemId(), dimId, quantity, line, manager, userData);
        }

        return true;
    }

    private void movementJournalLineItemChanged(InventoryJournalLines line, MovementJournalTxManager txManager, EMCUserData userData) throws EMCStockException {
        deleteMovementJournalRegisteredLines(line.getTransId(), line.getJournalNumber(), userData);

        InventoryTransactions orderedTransaction = txManager.getOrderedTransaction();

        //Lines with 0 qty will not generate transaction
        if (orderedTransaction != null) {
            //Clear ordered transaction quantity, then order again
            orderedTransaction.setQuantity(0);
        }

        long dimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);

        orderMovementJournalLine(line.getItemId(), dimId, line.getQuantity(), line.getTransId(), line.getJournalNumber(), txManager, line.getCost(), userData);
    }

    /**
     * Checks whether the specified transaction is an ordered out transaction.
     */
    private boolean isTransOrdered(InventoryTransactions trans, EMCUserData userData) {
        return InventoryTransactionStatus.fromString(trans.getStatus()).equals(InventoryTransactionStatus.ORDERED);
    }

    private boolean canReserveAgainstLine(InventoryJournalLines line, EMCUserData userData) {
        InventoryItemDimensionGroup dimGroup = itemMasterBean.getItemDimensionGroupRecord(line.getItemId(), userData);

        long dimensionId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);
        InventoryDimensionTable dimTable = dimIdLocal.getDimensionRecord(dimensionId, userData);

        return allActiveFieldsPopulated(dimGroup, dimTable);
    }

    /**
     * This method is used for lines which can be fully reserved based on the
     * journal line dimensions alone.
     */
    private void specialLineReserve(InventoryJournalLines line, long registerDimId, double quantity, MovementJournalTxManager txManager, EMCUserData userData) {
        //Ensure that we have a dummy ordered trans, otherwise txManager falls over
        if (txManager.getOrderedTransaction() == null) {
            txManager.setOrderedTrans(new InventoryTransactions());
        }

        long lineDimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);

        InventoryTransactions lineResTrans = txManager.getReservedTransaction(line.getItemId(), lineDimId, userData);

        InventoryTransactions regResTrans = txManager.getReservedTransaction(line.getItemId(), registerDimId, userData);

        if (regResTrans == null) {
            regResTrans = new InventoryTransactions();
            regResTrans.setTransId(line.getTransId());
            regResTrans.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
            regResTrans.setDirection(InventoryTransactionDirection.OUT.toString());
            regResTrans.setType(InventoryTransactionTypes.JR.toString());
            regResTrans.setRefType(InventoryTransactionsRefType.Journal_Line.toString());
            regResTrans.setRefNumber(line.getJournalNumber());
            regResTrans.setStatus(InventoryTransactionStatus.RESERVED.toString());
            regResTrans.setItemId(line.getItemId());
            regResTrans.setItemDimId(registerDimId);

            txManager.addReservedTrans(regResTrans);
            txManager.setTransactionStatus(regResTrans, 0, MovementJournalTxManagerStatus.INSERT);
        } else {
            txManager.setTransactionStatus(regResTrans, 0, MovementJournalTxManagerStatus.UPDATE);
        }

        //double regCost = itemMasterBean.getCostPrice(null, line.getItemId(), registerDimId, userData);
        //double lineCost = itemMasterBean.getCostPrice(null, line.getItemId(), lineDimId, userData);

        regResTrans.setQuantity(regResTrans.getQuantity() + Math.abs(quantity));
        regResTrans.setCost(line.getCost() * regResTrans.getQuantity());

        lineResTrans.setQuantity(lineResTrans.getQuantity() - Math.abs(quantity));
        lineResTrans.setCost(line.getCost() * lineResTrans.getQuantity());

        if (util.compareDouble(lineResTrans.getQuantity(), 0) == 0) {
            double unreserveQty = quantity;
            txManager.setTransactionStatus(lineResTrans, unreserveQty, MovementJournalTxManagerStatus.DELETE);
        } else {
            txManager.setTransactionStatus(lineResTrans, 0, MovementJournalTxManagerStatus.UPDATE);
        }
    }

    /**
     * This method is used for lines which can be fully reserved based on the
     * journal line dimensions alone.
     */
    private void specialLineUnreserve(InventoryJournalLines line, long registerDimId, double quantity, MovementJournalTxManager txManager, EMCUserData userData) {
        //Ensure that we have a dummy ordered trans, otherwise txManager falls over
        if (txManager.getOrderedTransaction() == null) {
            txManager.setOrderedTrans(new InventoryTransactions());
        }

        long lineDimId = dimIdLocal.getDimRecordId(line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), userData);

        InventoryTransactions lineResTrans = txManager.getReservedTransaction(line.getItemId(), lineDimId, userData);

        InventoryTransactions regResTrans = txManager.getReservedTransaction(line.getItemId(), registerDimId, userData);

        if (lineResTrans == null) {
            lineResTrans = new InventoryTransactions();
            lineResTrans.setTransId(line.getTransId());
            lineResTrans.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
            lineResTrans.setDirection(InventoryTransactionDirection.OUT.toString());
            lineResTrans.setType(InventoryTransactionTypes.JR.toString());
            lineResTrans.setRefType(InventoryTransactionsRefType.Journal_Line.toString());
            lineResTrans.setRefNumber(line.getJournalNumber());
            lineResTrans.setStatus(InventoryTransactionStatus.RESERVED.toString());
            lineResTrans.setItemId(line.getItemId());
            lineResTrans.setItemDimId(lineDimId);

            txManager.addReservedTrans(lineResTrans);
            txManager.setTransactionStatus(lineResTrans, 0, MovementJournalTxManagerStatus.INSERT);
        } else {
            txManager.setTransactionStatus(lineResTrans, 0, MovementJournalTxManagerStatus.UPDATE);
        }

        //double regCost = itemMasterBean.getCostPrice(null, line.getItemId(), registerDimId, userData);
        //double lineCost = itemMasterBean.getCostPrice(null, line.getItemId(), lineDimId, userData);

        regResTrans.setQuantity(regResTrans.getQuantity() + Math.abs(quantity));
        regResTrans.setCost(line.getCost() * regResTrans.getQuantity());

        lineResTrans.setQuantity(lineResTrans.getQuantity() - Math.abs(quantity));
        lineResTrans.setCost(line.getCost() * lineResTrans.getQuantity());

        if (util.compareDouble(lineResTrans.getQuantity(), 0) == 0) {
            double unreserveQty = quantity;
            txManager.setTransactionStatus(regResTrans, unreserveQty, MovementJournalTxManagerStatus.DELETE);
        } else {
            txManager.setTransactionStatus(regResTrans, 0, MovementJournalTxManagerStatus.UPDATE);
        }
    }

    /**
     * Returns a boolean indicating whether the given dimension table record has
     * values for all the active fields in the dimension group.
     *
     * @return A boolean indicating whether the given dimension table record has
     * values for all the active fields in the dimension group.
     */
    private boolean allActiveFieldsPopulated(InventoryItemDimensionGroup dimGroup, InventoryDimensionTable dimTable) {
        if (dimGroup.getBatchNumberActive() && isBlank(dimTable.getBatchId())
                || dimGroup.getDim1Active() && isBlank(dimTable.getDimension1Id())
                || dimGroup.getDim2Active() && isBlank(dimTable.getDimension2Id())
                || dimGroup.getDim3Active() && isBlank(dimTable.getDimension3Id())
                || dimGroup.getLocationActive() && isBlank(dimTable.getLocationId())
                || dimGroup.getPalletIdActive() && isBlank(dimTable.getPalletId())
                || dimGroup.getSerialNumberActive() && isBlank(dimTable.getItemSerialId())
                || dimGroup.getWarehouseActive() && isBlank(dimTable.getWarehouseId())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Handles Transfer Journal post entire Journal
     *
     * @param iJournalMast
     * @param journalLines
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    private boolean postTransferJournal(InventoryJournalMaster iJournalMast, List<InventoryJournalLines> journalLines, EMCUserData userData) throws EMCStockException {
        double count = 0;
        double size = Double.valueOf(journalLines.size());
        for (InventoryJournalLines theLine : journalLines) {
            count++;
            if (util.compareDouble(count % 10, 0) == 0) {
                System.out.println("Done " + count + " Percentage done " + 100.0 * (count / size));
                try {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException("Failed to flush to database:" + ex.getMessage());
                }
            }

            //Zero Lines will not have any transactions
            if (util.compareDouble(theLine.getQuantity(), 0) == 0) {
                continue;
            }

            if (!validateTransferJournalDimenstions(theLine, userData)) {
                InventoryItemMaster item = itemMasterBean.findItem(theLine.getItemId(), userData);

                throw new EMCStockException("Invalid dimensions for item " + (item == null ? theLine.getItemId() : item.getItemReference())
                        + (isBlank(theLine.getDimension1()) ? "" : ", config " + theLine.getDimension1())
                        + (isBlank(theLine.getDimension3()) ? "" : ", colour " + theLine.getDimension3())
                        + (isBlank(theLine.getDimension2()) ? "" : ", size " + theLine.getDimension2()));
            }

            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class);
            qu.addAnd("transId", theLine.getTransId());
            qu.addAnd("refNumber", theLine.getJournalNumber());
            qu.addAnd("direction", InventoryTransactionDirection.OUT.toString());
            InventoryTransactions fromTx = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);
            //also find toTx
            qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class);
            qu.addAnd("transId", theLine.getTransId());
            qu.addAnd("refNumber", theLine.getJournalNumber());
            qu.addAnd("direction", InventoryTransactionDirection.IN.toString());
            InventoryTransactions toTx = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);
            if (fromTx == null || toTx == null) {
                throw new EMCStockException("Could not find stock transactions for the Transfer.");
            }
            //update from Tx
            fromTx.setPhysicalDate(theLine.getLineDate());
            fromTx.setFinancialDate(theLine.getLineDate());
            fromTx.setStatus(InventoryTransactionStatus.SOLD.toString());
            //N&L Width
            InventoryDimensionTable fromDim = dimIdLocal.getDimensionRecord(fromTx.getItemDimId(), userData);
            if (fromDim == null) {
                throw new EMCStockException("From Transaction dimension Id does not exist.");
            }
            long fromWidthDim = this.dimIdLocal.getDimRecordId(fromDim.getBatchId(), fromDim.getDimension1Id(), fromDim.getDimension2Id(), fromDim.getDimension3Id(),
                    null, null, null, fromDim.getItemSerialId(), userData);
            EMCQuery dimq = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions.class.getName());
            dimq.addAnd("itemId", fromTx.getItemId());
            dimq.addAnd("dimensionId", fromWidthDim);
            InventoryAdditionalDimensions fromAddDim = (InventoryAdditionalDimensions) util.executeSingleResultQuery(dimq, userData);
            //END N&L Width
            //update to Tx
            toTx.setPhysicalDate(theLine.getLineDate());
            toTx.setFinancialDate(theLine.getLineDate());
            toTx.setStatus(InventoryTransactionStatus.PURCHASED.toString());
            //N&L Width
            InventoryAdditionalDimensions newAddDim = null;
            if (fromAddDim != null) {
                //see if to exist if it does do nothing
                InventoryDimensionTable toDim = dimIdLocal.getDimensionRecord(toTx.getItemDimId(), userData);
                if (toDim == null) {
                    throw new EMCStockException("To Transaction dimensionId does not exist");
                }
                long toWidthDim = this.dimIdLocal.getDimRecordId(toDim.getBatchId(), toDim.getDimension1Id(), toDim.getDimension2Id(), toDim.getDimension3Id(),
                        null, null, null, toDim.getItemSerialId(), userData);
                dimq = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions.class.getName());
                dimq.addAnd("itemId", toTx.getItemId());
                dimq.addAnd("dimensionId", toWidthDim);
                InventoryAdditionalDimensions toAddDim = (InventoryAdditionalDimensions) util.executeSingleResultQuery(dimq, userData);
                if (toAddDim != null) {
                    //updata? Do nothing for now need user input?
                } else {
                    newAddDim = new InventoryAdditionalDimensions();
                    newAddDim.setItemId(toTx.getItemId());
                    newAddDim.setDimensionId(toWidthDim);
                    newAddDim.setWidth(fromAddDim.getWidth());
                    newAddDim.setWidthUOM(fromAddDim.getWidthUOM());
                }
            }
            //END N&L Width
            try {
                //N&L width mod
                if (newAddDim != null) {
                    addDimBean.insert(newAddDim, userData);
                }
                //end N&L width mod 
                trans.update(fromTx, userData);
                trans.update(toTx, userData);
                updateOnHand(InventorySummaryUpdateOptions.DELIVERED, fromTx, userData);
                updateOnHand(InventorySummaryUpdateOptions.RECEIVED, toTx, userData);

            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not update inventory transaction " + entBex.getMessage());
            }
        }
        return true;
    }

    /**
     * Handles movement journal posting of entire journal
     *
     * @param iJournalMast
     * @param journalLines
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    private boolean postMovementJournal(InventoryJournalMaster iJournalMast, List<InventoryJournalLines> journalLines, EMCUserData userData) throws EMCStockException {

        double count = 0;
        double size = Double.valueOf(journalLines.size());
        for (InventoryJournalLines theLine : journalLines) {
            count++;
            if (util.compareDouble(count % 10, 0) == 0) {
                System.out.println("Done " + count + " Percentage done " + 100.0 * (count / size));
                try {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException("Failed to flush to database:" + ex.getMessage());
                }
            }

            //Zero Lines will not have any transactions
            if (util.compareDouble(theLine.getQuantity(), 0) == 0) {
                continue;
            }

            List<InventoryRegister> registerdLines = new ArrayList();
            List<InventoryRemoveRegister> removeRegisteredLines = new ArrayList();
            //if registerd lines create transaction for each registered line
            //select based on the sign
            if (util.compareDouble(theLine.getQuantity(), 0) >= 0) {
                EMCQuery serialLines = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.register.InventoryRegister.class.getName());
                serialLines.addAnd("masterId", iJournalMast.getJournalNumber());
                serialLines.addAnd("transId", theLine.getTransId());
                serialLines.addAnd("type", RegisterFromTypeEnum.JRN.toString());
                registerdLines = util.executeGeneralSelectQuery(serialLines, userData);
            } else {
                EMCQuery serialLines = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.register.InventoryRemoveRegister.class.getName());
                serialLines.addAnd("masterId", iJournalMast.getJournalNumber());
                serialLines.addAnd("transId", theLine.getTransId());
                serialLines.addAnd("type", RegisterFromTypeEnum.JRN.toString());
                removeRegisteredLines = util.executeGeneralSelectQuery(serialLines, userData);
            }
            //find Journal line tx  
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class.getName());
            long dimId = dimIdLocal.getDimRecordId(theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(), userData);
            qu.addAnd("transId", theLine.getTransId());
            qu.addAnd("itemDimId", dimId);
            qu.addAnd("itemId", theLine.getItemId());
            qu.addAnd("refNumber", theLine.getJournalNumber());
            //TODO Remove this branch and work only on reserved transactions.  This code is included for backward compatibility.
            qu.openConditionBracket(EMCQueryBracketConditions.AND);
            qu.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            qu.addOr("status", InventoryTransactionStatus.RESERVED.toString());
            qu.closeConditionBracket();

            if (registerdLines != null && registerdLines.size() > 0) {
                System.out.println("Number of Registered Lines = " + registerdLines.size());
                InventoryTransactions jourTx = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);
                if (jourTx == null) {
                    throw new EMCStockException("No Transaction found for Journal line: " + theLine.getLineNo());
                }
                InventorySummaryUpdateOptions onHandOpt;
                double totalPosted = 0.0;
                for (InventoryRegister curReg : registerdLines) {
                    InventoryTransactions newTx = (InventoryTransactions) this.doClone(jourTx, userData);
                    long currentDimId = dimIdLocal.getDimRecordId(curReg.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), curReg.getWarehouse(),
                            curReg.getLocation(), curReg.getPallet(), curReg.getSerial(), userData);
                    newTx.setCompanyId(jourTx.getCompanyId());
                    newTx.setItemDimId(currentDimId);
                    newTx.setQuantity(Math.abs(curReg.getQuantity()));
                    totalPosted += curReg.getQuantity();
                    newTx.setCost(newTx.getQuantity() * jourTx.getCost() / jourTx.getQuantity());
                    //create order transaction
                    try {
                        trans.insert(newTx, userData);
                        updateOnHand(newTx.getQuantity() >= 0 ? InventorySummaryUpdateOptions.ORDERED_IN : InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, newTx, userData);

                    } catch (EMCEntityBeanException entBex) {
                        throw new EMCStockException("Could not insert inventory transaction " + entBex.getMessage());
                    }
                    //Now post it away
                    newTx.setPhysicalDate(theLine.getLineDate());
                    newTx.setFinancialDate(theLine.getLineDate());

                    if (util.compareDouble(theLine.getQuantity(), 0) < 0) {
                        newTx.setStatus(InventoryTransactionStatus.SOLD.toString());
                        onHandOpt = InventorySummaryUpdateOptions.DELIVERED;
                    } else {
                        newTx.setStatus(InventoryTransactionStatus.PURCHASED.toString());
                        onHandOpt = InventorySummaryUpdateOptions.RECEIVED;
                    }
                    try {
                        trans.update(newTx, userData);
                        updateOnHand(onHandOpt, newTx, userData);

                    } catch (EMCEntityBeanException entBex) {
                        throw new EMCStockException("Could not update inventory transaction " + entBex.getMessage());
                    }
                    //remove registered line

                }
                //remove journal tx
                //delete tx

                if (util.compareDouble(totalPosted, jourTx.getQuantity()) != 0) {
                    throw new EMCStockException("Line qty not equal to registered qty line no:" + theLine.getLineNo() + " item:" + theLine.getItemId());
                }
                try {
                    //find transaction summary
                    EMCQuery inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                    inventSumQu.addAnd("inventoryTransRef", jourTx.getRecordID());
                    InventorySummary sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                    itemSumLocal.delete(sumTx, userData);
                    trans.delete(jourTx, userData);
                } catch (EMCEntityBeanException entBex) {
                    throw new EMCStockException("Could not remove journal transaction " + entBex.getMessage());
                }

            } else {
                if (removeRegisteredLines != null && removeRegisteredLines.size() > 0) {
                    InventorySummaryUpdateOptions onHandOpt;
                    double totalPosted = 0.0;
                    for (InventoryRemoveRegister curReg : removeRegisteredLines) {
                        long currentDimId = dimIdLocal.getDimRecordId(curReg.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), curReg.getWarehouse(),
                                curReg.getLocation(), curReg.getPallet(), curReg.getSerial(), userData);

                        qu.removeAnd("itemDimId");
                        qu.addAnd("itemDimId", currentDimId);

                        InventoryTransactions regTrans = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);

                        //Now post it away
                        regTrans.setPhysicalDate(theLine.getLineDate());
                        regTrans.setFinancialDate(theLine.getLineDate());

                        if (util.compareDouble(theLine.getQuantity(), 0) < 0) {
                            regTrans.setStatus(InventoryTransactionStatus.SOLD.toString());
                            onHandOpt = InventorySummaryUpdateOptions.DELIVERED;
                        } else {
                            regTrans.setStatus(InventoryTransactionStatus.PURCHASED.toString());
                            onHandOpt = InventorySummaryUpdateOptions.RECEIVED;
                        }
                        try {
                            trans.update(regTrans, userData);
                            updateOnHand(onHandOpt, regTrans, userData);

                        } catch (EMCEntityBeanException entBex) {
                            throw new EMCStockException("Could not update inventory transaction " + entBex.getMessage());
                        }

                        totalPosted += Math.abs(curReg.getQuantity());
                    }
                    //Check that everything has been posted.
                    if (util.compareDouble(totalPosted, Math.abs(theLine.getQuantity())) != 0) {
                        throw new EMCStockException("Registered quantity not equal to Journal Line quantity. Line no:" + theLine.getLineNo() + " item:" + referenceBean.checkItemReference(theLine.getItemId(), userData)[1]);
                    }
                } else {
                    InventoryTransactions jourTx = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);
                    InventorySummaryUpdateOptions onHandOpt;
                    if (jourTx != null) {
                        //now update the journal
                        jourTx.setPhysicalDate(theLine.getLineDate());
                        jourTx.setFinancialDate(theLine.getLineDate());
                        if (util.compareDouble(theLine.getQuantity(), 0) < 0) {
                            jourTx.setStatus(InventoryTransactionStatus.SOLD.toString());
                            onHandOpt = InventorySummaryUpdateOptions.DELIVERED;
                        } else {
                            jourTx.setStatus(InventoryTransactionStatus.PURCHASED.toString());
                            onHandOpt = InventorySummaryUpdateOptions.RECEIVED;
                        }
                        try {
                            trans.update(jourTx, userData);
                            updateOnHand(onHandOpt, jourTx, userData);

                        } catch (EMCEntityBeanException entBex) {
                            throw new EMCStockException("Could not update inventory transaction " + entBex.getMessage());
                        }
                    } else {
                        throw new EMCStockException("No Transaction found for Journal line: " + theLine.getLineNo());
                    }
                }

            }
        }
        return true;
    }

    /**
     * Post and entire stock take Journal
     *
     * @param iJournalMast
     * @param journalLines
     * @param userData
     * @return
     * @throws EMCStockException
     */
    private boolean postStockTakeJournal(InventoryJournalMaster iJournalMast, List<InventoryJournalLines> journalLines, boolean actualPosting, EMCUserData userData) throws EMCStockException {
        //for each Journal line update the qty
        double count = 0;
        boolean delOrigJournalTx = true;
        List<InventoryTransactions> unreserved = new ArrayList();
        double size = Double.valueOf(journalLines.size());
        List<String> itemList = new ArrayList<String>();
        List<String> combinationList = new ArrayList<String>();
        String combinationKey;
        List<String> locationList = new ArrayList<String>();
        //Stock check Query
        EMCQuery checkStock = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        checkStock.addFieldAggregateFunction("physicalAvailable", "SUM");
        EMCQuery checkStockQc = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        checkStockQc.addFieldAggregateFunction("qcAvailable", "SUM");
        EMCQuery checkStockRec = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        checkStockRec.addFieldAggregateFunction("recAvailable", "SUM");
        EMCQuery resvStock = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        resvStock.addFieldAggregateFunction("physicalReserved", "SUM");
        EMCQuery grabStock = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        grabStock.addAnd("physicalReserved", 0, EMCQueryConditions.GREATER_THAN);
        grabStock.addOrderBy("createdDate", InventorySummary.class.getName(), EMCQueryOrderByDirections.DESC);
        for (InventoryJournalLines theLine : journalLines) {
            delOrigJournalTx = true; //set this true per journal line
            count++;
            if (util.compareDouble(count % 10, 0) == 0) {
                try {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException("Failed to flush and detatch: " + ex.getMessage());
                }
                System.out.println("Done " + count + " Percentage done " + 100.0 * (count / size));
            }
            //Update Journal Line Qty
            theLine.setQuantity(theLine.getCountedQuantity() - theLine.getCountQOH());
            theLine.setTotalCost(theLine.getQuantity() * theLine.getCost());
            try {
                journalLinesBean.doUpdate(theLine, userData);
            } catch (Exception ex) {
                throw new EMCStockException("Failed to update journal line: " + ex.getMessage());
            }
            //Select Journal Line Transactions
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class);
            qu.addAnd("transId", theLine.getTransId());
            qu.addAnd("itemDimId", dimIdLocal.getDimRecordId(theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(), userData));
            qu.addAnd("itemId", theLine.getItemId());
            qu.addAnd("refNumber", theLine.getJournalNumber());
            qu.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            InventoryTransactions jourTx = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);
            //Fetch Register Lines
            EMCQuery serialLines = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.register.InventoryStocktakeRegister.class.getName());
            serialLines.addAnd("masterId", iJournalMast.getJournalNumber());
            serialLines.addAnd("transId", theLine.getTransId());
            serialLines.addAnd("type", RegisterFromTypeEnum.JRN.toString());
            List<InventoryStocktakeRegister> registerdStockLines = util.executeGeneralSelectQuery(serialLines, userData);
            //Detatch Selected Entities
            //util.detachEntities(userData);
            //For Each Register Line
            System.out.println("Number of Registered Lines = " + registerdStockLines.size());
            InventorySummaryUpdateOptions onHandOpt;
            for (InventoryStocktakeRegister curReg : registerdStockLines) {
                //Delete Stock Take Log Record
                try {
                    stockTakeLoggerBean.removeStockTakeRecord(theLine.getItemId(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), curReg.getWarehouse(), curReg.getLocation(), curReg.getBatch(), curReg.getSerial(), curReg.getPallet(), userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException("Failed to delete stock take record: " + ex.getMessage());
                }
                //Update Register Line Qty
                curReg.setQuantity(curReg.getQuantity() - curReg.getOnHandQty());
                //No Updates needed
                if (util.compareDouble(curReg.getQuantity(), 0d) == 0) {
                    continue;
                } else {
                    //Create Journal Tx if not found
                    if (jourTx == null) {
                        //probably hit a scenario where the in's and outs balance.
                        jourTx = new InventoryTransactions();
                        jourTx.setTransId(theLine.getTransId());
                        jourTx.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
                        jourTx.setDirection((theLine.getQuantity() - theLine.getCountQOH() > 0 ? InventoryTransactionDirection.IN.toString() : InventoryTransactionDirection.OUT.toString()));
                        jourTx.setType(InventoryTransactionTypes.JR.toString());
                        jourTx.setRefType(InventoryTransactionsRefType.Journal_Line.toString());
                        jourTx.setRefNumber(theLine.getJournalNumber());
                        jourTx.setStatus(InventoryTransactionStatus.ORDERED.toString());
                        jourTx.setQuantity(theLine.getQuantity());
                        jourTx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), theLine.getWarehouse(), theLine.getLocation(), theLine.getPallet(), theLine.getSerial(), userData));
                        jourTx.setItemId(theLine.getItemId());
                        jourTx.setCost(theLine.getTotalCost());
                        delOrigJournalTx = false; //set this false once for the journal line
                    }
                    //Create Ajustment Trans
                    long currentDimId = dimIdLocal.getDimRecordId(curReg.getBatch(), theLine.getDimension1(), theLine.getDimension2(), theLine.getDimension3(), curReg.getWarehouse(),
                            curReg.getLocation(), curReg.getPallet(), curReg.getSerial(), userData);
                    InventoryTransactions newTx = (InventoryTransactions) this.doClone(jourTx, userData);
                    newTx.setCompanyId(jourTx.getCompanyId());
                    newTx.setItemDimId(currentDimId);
                    newTx.setQuantity(Math.abs(curReg.getQuantity()));
                    newTx.setDirection(curReg.getQuantity() > 0 ? InventoryTransactionDirection.IN.toString() : InventoryTransactionDirection.OUT.toString());
                    newTx.setCost(newTx.getQuantity() * theLine.getCost());
                    //Check Stock Level
                    if (newTx.getDirection().equals(InventoryTransactionDirection.OUT.toString())) {
                        EMCQuery toUse = null;
                        //Determine QC, REC or STD
                        if (util.checkObjectsEqual(curReg.getLocation(), InventoryLocationsEnum.QUALITY_CHECK.toString())) {
                            if (isAvailableInWarehouse(curReg.getWarehouse(), curReg.getLocation(), userData)) {
                                toUse = checkStock;
                            } else {
                                toUse = checkStockQc;
                            }
                        } else if (util.checkObjectsEqual(curReg.getLocation(), InventoryLocationsEnum.RECEIVING_AREA.toString())) {
                            if (isAvailableInWarehouse(curReg.getWarehouse(), curReg.getLocation(), userData)) {
                                toUse = checkStock;
                            } else {
                                toUse = checkStockRec;
                            }
                        } else {
                            toUse = checkStock;
                        }
                        //Update Query
                        toUse.removeAnd("itemId");
                        toUse.addAnd("itemId", newTx.getItemId());
                        toUse.removeAnd("itemDimId");
                        toUse.addAnd("itemDimId", newTx.getItemDimId());
                        //Select Available
                        Object qRes = util.executeSingleResultQuery(toUse, userData);
                        double availQty = 0;
                        if (qRes != null) {
                            availQty = Double.parseDouble(qRes.toString());
                        }
                        //Not enough physical available
                        if (!(util.compareDouble(availQty - newTx.getQuantity(), 0) >= 0)) {
                            //See if there is enough reserved
                            resvStock.removeAnd("itemId");
                            resvStock.removeAnd("itemDimId");
                            resvStock.addAnd("itemId", newTx.getItemId());
                            resvStock.addAnd("itemDimId", newTx.getItemDimId());
                            qRes = util.executeSingleResultQuery(resvStock, userData);
                            double resQty = 0;
                            if (qRes != null) {
                                resQty = Double.parseDouble(qRes.toString());
                            }
                            if (util.compareDouble(availQty + resQty - newTx.getQuantity(), 0) >= 0) {
                                //enough reserved Grab it.
                                double needed = newTx.getQuantity() - availQty;
                                grabStock.removeAnd("itemId");
                                grabStock.removeAnd("itemDimId");
                                grabStock.addAnd("itemDimId", newTx.getItemDimId());
                                grabStock.addAnd("itemId", newTx.getItemId());
                                List<InventorySummary> reservedStock = util.executeGeneralSelectQuery(grabStock, userData);
                                for (InventorySummary curResSum : reservedStock) {
                                    InventoryTransactions curResTx = (InventoryTransactions) util.findPersisted(InventoryTransactions.class, curResSum.getInventoryTransRef(), userData);
                                    if (curResTx == null) {
                                        throw new EMCStockException("Failed to find transaction for summary with recordID:" + curResSum.getRecordID());
                                    }
                                    double curTxQty = curResTx.getQuantity();
                                    InventoryDimensionTable resDim = (InventoryDimensionTable) util.findPersisted(InventoryDimensionTable.class, curResTx.getItemDimId(), userData);
                                    long orderDimId = this.dimIdLocal.getDimRecordId(resDim.getDimension1Id(), resDim.getDimension2Id(), resDim.getDimension3Id(), resDim.getWarehouseId(), userData);
                                    //Find ORdered Trans
                                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                                    query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
                                    query.addAnd("refNumber", curResTx.getRefNumber());
                                    query.addAnd("refType", curResTx.getRefType());
                                    query.addAnd("type", curResTx.getType());
                                    query.addAnd("itemId", curResTx.getItemId());
                                    query.addAnd("itemDimId", orderDimId);
                                    query.addAnd("direction", InventoryTransactionDirection.OUT);
                                    InventoryTransactions orderedTransaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
                                    //Unreserver Trans
                                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, curResTx, userData);

                                    double rereserved = 0;

                                    if (util.compareDouble(needed, curTxQty) >= 0) {
                                        if (orderedTransaction == null) {
                                            curResTx.setQuantity(curTxQty);
                                            curResTx.setStatus(InventoryTransactionStatus.ORDERED.toString());
                                            curResTx.setItemDimId(orderDimId);
                                            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, curResTx, userData);
                                            try {
                                                trans.update(curResTx, userData);
                                            } catch (Exception ex) {
                                                throw new EMCStockException("Failed to update previously reserved transaction. " + ex.getMessage());
                                            }
                                        } else {
                                            orderedTransaction.setQuantity(orderedTransaction.getQuantity() + curTxQty);
                                            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, orderedTransaction, userData);
                                            try {
                                                trans.update(orderedTransaction, userData);
                                                super.deleteTransactionAndOnHand(curResTx, userData);
                                            } catch (Exception ex) {
                                                throw new EMCStockException("Failed to update previously reserved transaction. " + ex.getMessage());
                                            }
                                        }
                                        needed -= curTxQty;
                                    } else {
                                        //now reserve the qty that is left
                                        curResTx.setQuantity(curTxQty - needed);
                                        updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, curResTx, userData);

                                        rereserved = curTxQty - needed;
                                        try {
                                            trans.update(curResTx, userData);
                                        } catch (Exception exc) {
                                            throw new EMCStockException("Failed to insert new on Order transaction during unreserving of stock. " + exc.getMessage());
                                        }
                                        if (orderedTransaction == null) {
                                            //insert new on Order Transaction with the qty that is outstanding
                                            orderedTransaction = (InventoryTransactions) this.doClone(curResTx, userData);
                                            //set the status of the ordered transaction to ordered not RESERVED
                                            orderedTransaction.setStatus(InventoryTransactionStatus.ORDERED.toString());
                                            orderedTransaction.setQuantity(needed);
                                            orderedTransaction.setItemDimId(this.dimIdLocal.getDimRecordId(resDim.getDimension1Id(), resDim.getDimension2Id(), resDim.getDimension3Id(), resDim.getWarehouseId(), userData));
                                            try {
                                                trans.insert(orderedTransaction, userData);
                                            } catch (Exception ex) {
                                                throw new EMCStockException("Failed to insert new on Order transaction during unreserving of stock. " + ex.getMessage());
                                            }
                                            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, orderedTransaction, userData);
                                        } else {
                                            orderedTransaction.setQuantity(orderedTransaction.getQuantity() + needed);
                                            try {
                                                trans.update(orderedTransaction, userData);
                                            } catch (Exception ex) {
                                                throw new EMCStockException("Failed to insert new on Order transaction during unreserving of stock. " + ex.getMessage());
                                            }
                                            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, orderedTransaction, userData);
                                        }
                                        needed = 0;
                                    }

                                    //Check if stock came from a journal
                                    if (InventoryTransactionsRefType.Journal_Line.toString().equals(curResTx.getRefType())) {
                                        //Delete the registration lines for the unreserved stock
                                        EMCQuery regQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class);
                                        regQuery.addAnd("masterId", curResTx.getRefNumber());
                                        regQuery.addAnd("transId", curResTx.getTransId());
                                        regQuery.addAnd("warehouse", resDim.getWarehouseId());
                                        regQuery.addAnd("location", resDim.getLocationId());
                                        regQuery.addAnd("batch", resDim.getBatchId());
                                        regQuery.addAnd("serial", resDim.getItemSerialId());
                                        regQuery.addAnd("pallet", resDim.getPalletId());
                                        InventoryRemoveRegister registration = (InventoryRemoveRegister) util.executeSingleResultQuery(regQuery, userData);

                                        //Stock Re-Reserved - Reduce Register Qty else delete
                                        if (util.compareDouble(rereserved, 0) > 0) {
                                            registration.setQuantity(rereserved);
                                            try {
                                                removeRegBean.updateWithoutTx(registration, userData);
                                            } catch (EMCEntityBeanException ex) {
                                                throw new EMCStockException("Failed to update registration line for unreserved stock:" + ex.getMessage());
                                            }
                                        } else {
                                            try {
                                                removeRegBean.deleteWithoutTx(registration, userData);
                                            } catch (EMCEntityBeanException ex) {
                                                throw new EMCStockException("Failed to delete registration line for unreserved stock:" + ex.getMessage());
                                            }
                                        }
                                    }

                                    unreserved.add(curResTx);

                                    if (util.compareDouble(needed, 0) == 0) {
                                        break; //got enough
                                    }
                                }

                            } else {
                                //Not enough stock even in reserved
                                throw new EMCStockException("Not enough stock available:" + referenceBean.checkItemReference(newTx.getItemId(), userData)[1]);
                            }
                        }
                    }
                    //Save Transaction and update Stock Levels
                    try {
                        trans.insert(newTx, userData);
                        updateOnHand(newTx.getDirection().equals(InventoryTransactionDirection.IN.toString()) ? InventorySummaryUpdateOptions.ORDERED_IN : InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, newTx, userData);
                    } catch (EMCEntityBeanException entBex) {
                        throw new EMCStockException("Could not insert inventory transaction " + entBex.getMessage());
                    }

                    newTx.setPhysicalDate(theLine.getLineDate());
                    newTx.setFinancialDate(theLine.getLineDate());
                    if (newTx.getDirection().equals(InventoryTransactionDirection.OUT.toString())) {
                        newTx.setStatus(InventoryTransactionStatus.SOLD.toString());
                        onHandOpt = InventorySummaryUpdateOptions.DELIVERED;
                    } else {
                        newTx.setStatus(InventoryTransactionStatus.PURCHASED.toString());
                        onHandOpt = InventorySummaryUpdateOptions.RECEIVED;
                    }
                    try {
                        trans.update(newTx, userData);
                        updateOnHand(onHandOpt, newTx, userData);
                    } catch (EMCEntityBeanException entBex) {
                        throw new EMCStockException("Could not update inventory transaction " + entBex.getMessage());
                    }
                    //Location Last Counted Date
                    if (!locationList.contains(curReg.getLocation())) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
                        query.addAnd("locationId", curReg.getLocation());
                        query.addAnd("warehouseId", curReg.getWarehouse());
                        InventoryLocation location = (InventoryLocation) util.executeSingleResultQuery(query, userData);
                        if (location != null) {
                            location.setLastCountedDate(Functions.nowDate());
                            try {
                                locationBean.update(location, userData);
                            } catch (EMCEntityBeanException ex) {
                                throw new EMCStockException(ex.toString());
                            }
                            locationList.add(location.getLocationId());
                        }
                    }
                }
            }
            //Delete Journal Line Tx
            if (delOrigJournalTx && jourTx != null && jourTx.getRecordID() != 0) { //belt and braces check for deletion
                try {
                    //find transaction summary
                    EMCQuery inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                    inventSumQu.addAnd("inventoryTransRef", jourTx.getRecordID());
                    InventorySummary sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                    itemSumLocal.delete(sumTx, userData);
                    trans.delete(jourTx, userData);
                } catch (EMCEntityBeanException entBex) {
                    throw new EMCStockException("Could not remove journal transaction " + entBex.getMessage());
                }
            }
            //Item Last Counted Date
            if (!itemList.contains(theLine.getItemId())) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
                query.addAnd("itemId", theLine.getItemId());
                InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
                if (item != null) {
                    item.setLastCountedDate(Functions.nowDate());
                    try {
                        itemMasterBean.update(item, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException(ex.toString());
                    }
                }
            }
            //Combination Last Counted Date
            combinationKey = theLine.getItemId() + theLine.getDimension1() + theLine.getDimension2() + theLine.getDimension3();
            if (!combinationList.contains(combinationKey)) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
                query.addAnd("itemId", theLine.getItemId());
                query.addAnd("dimension1Id", theLine.getDimension1());
                query.addAnd("dimension2Id", theLine.getDimension2());
                query.addAnd("dimension3Id", theLine.getDimension3());
                InventoryItemDimensionCombinations combination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(query, userData);
                if (combination != null) {
                    combination.setLastCountedDate(Functions.nowDate());
                    try {
                        combinationBean.update(combination, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException(ex.toString());
                    }
                }
            }
        }

        if (actualPosting) {
            List<String> unreservedWarnings = new ArrayList<String>();
            InventoryDimensionTable dimTable;
            InventoryItemMaster item;
            List<InventoryStocktakeUnreserved> unreservedList = new ArrayList<InventoryStocktakeUnreserved>();
            InventoryStocktakeUnreserved unreservedRec;
            for (InventoryTransactions tx : unreserved) {
                unreservedRec = new InventoryStocktakeUnreserved();
                unreservedRec.setReferenceJournal(iJournalMast.getJournalNumber());
                unreservedRec.setReferenceType(tx.getRefType());
                unreservedRec.setReferenceId(tx.getRefNumber());
                unreservedRec.setItemId(tx.getItemId());
                unreservedRec.setDimensionId(tx.getItemDimId());
                unreservedRec.setUniqueIdentifier(unreservedRec.getReferenceJournal() + unreservedRec.getReferenceType() + unreservedRec.getReferenceId() + unreservedRec.getItemId() + unreservedRec.getDimensionId());
                unreservedList.add(unreservedRec);

                dimTable = dimIdLocal.getDimensionRecord(tx.getItemDimId(), userData);
                item = itemMasterBean.findItem(tx.getItemId(), userData);

                unreservedWarnings.add("Transaction for " + tx.getRefType() + " number:" + tx.getRefNumber() + ", Item: " + item.getItemReference()
                        + ", Config: " + dimTable.getDimension1Id() + ", Color: " + dimTable.getDimension3Id() + ", Size: " + dimTable.getDimension2Id()
                        + ", Quantity:" + util.round(tx.getQuantity(), 2) + ", was unreserved.");
            }
            if (!unreservedWarnings.isEmpty()) {
                try {
                    fileHandlerBean.attachFileToRecord(iJournalMast, "Unreserved Stock", unreservedWarnings, "UnreservedStock_" + iJournalMast.getJournalNumber() + ".txt", userData);
                    Logger.getLogger("emc").log(Level.INFO, "A list of the unreserved stock was attached to the Stock Take Journal Master record.", userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
            }
            if (!unreservedList.isEmpty()) {
                util.insertDirect(InventoryStocktakeUnreserved.class, unreservedList, userData);

                EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, InventoryStocktakeUnreserved.class);
                query.addAnd("referenceJournal", iJournalMast.getJournalNumber());

                commandManager.setTransactionFailQuery(registry.getTransactionKey(), query.toString());

            }
        }
        return true;
    }

    /**
     * Post journal Master
     *
     * @param iJournalMast
     * @param userData
     * @return
     */
    @Override
    public EMCTable postInventJournal(InventoryJournalMaster iJournalMast, boolean actualPosting, EMCUserData userData) throws EMCStockException {

        EMCQuery q = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        q.addAnd("journalNumber", iJournalMast.getJournalNumber());
        List<InventoryJournalLines> journalLines = util.executeGeneralSelectQuery(q, userData);

        //check Journal type
        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(iJournalMast, userData);
        switch (InventoryJournalTypes.fromString(definition.getJournalType())) {
            case MOVEMENT:
                postMovementJournal(iJournalMast, journalLines, userData);
                break;
            case STOCKTAKE:
                postStockTakeJournal(iJournalMast, journalLines, actualPosting, userData);
                break;
            case TRANSFER:
                postTransferJournal(iJournalMast, journalLines, userData);
                break;
        }

        return null;
    }

    /**
     * Moves Stock Around
     *
     * @param MSMaster
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCTable postMoveStockLine(InventoryMoveStockMaster MSMaster, EMCUserData userData) throws EMCStockException {
        return this.postMoveStockLine(MSMaster, null, userData);
    }

    /**
     * Moves Stock Around - Overloaded version to allow move to different
     * warehouse as well.
     *
     * @param MSMaster
     * @param warehouse
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCTable postMoveStockLine(InventoryMoveStockMaster MSMaster, String toWarehouse, EMCUserData userData) throws EMCStockException {
        try {
            InventoryTransactions outTrans = createMovementTrans(MSMaster.getItemId(), MSMaster.getDimensionId(), MSMaster.getQuantity(), MSMaster.getTransId(), InventoryTransactionDirection.OUT, userData);
            outTrans.setTransId(MSMaster.getTransId());

            //Order out transaction.  The transaction should go through the regular flow.
            trans.insert(outTrans, userData);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, outTrans, userData);

            //Reserve out transaction
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, outTrans, userData);

            //Set QC status in userdata for return.
            userData.setUserData(10, MSMaster.getQCStatus());

            //Deliver out transaction
            updateOnHand(InventorySummaryUpdateOptions.DELIVERED, outTrans, userData);
            outTrans.setStatus(InventoryTransactionStatus.DELIVERED.toString());
            outTrans.setPhysicalDate(Functions.nowDate());

            trans.update(outTrans, userData);

            //Get dimension for new ordered transaction
            InventoryDimensionTable originalDims = dimIdLocal.getDimensionRecord(MSMaster.getDimensionId(), userData);

            String warehouse = toWarehouse == null ? originalDims.getWarehouseId() : toWarehouse;

            //Due to split, there may be multiple in transactions.
            List<InventoryTransactions> inTransactions = new ArrayList<InventoryTransactions>();

            if (!MSMaster.isSplit()) {
                long newDimId = dimIdLocal.getDimRecordId(originalDims.getBatchId(), originalDims.getDimension1Id(), originalDims.getDimension2Id(), originalDims.getDimension3Id(), warehouse, MSMaster.getToLocation(), originalDims.getPalletId(), originalDims.getItemSerialId(), userData);

                InventoryTransactions inTrans = createMovementTrans(MSMaster.getItemId(), newDimId, MSMaster.getQuantity(), MSMaster.getTransId(), InventoryTransactionDirection.IN, userData);

                inTransactions.add(inTrans);
            } else {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockLines.class.getName());
                query.addAnd("masterId", MSMaster.getRecordID());
                List<InventoryMoveStockLines> linesList = util.executeGeneralSelectQuery(query, userData);

                for (InventoryMoveStockLines line : linesList) {
                    long newDimId = dimIdLocal.getDimRecordId(originalDims.getBatchId(), originalDims.getDimension1Id(), originalDims.getDimension2Id(), originalDims.getDimension3Id(), warehouse, line.getToLocation(), originalDims.getPalletId(), originalDims.getItemSerialId(), userData);

                    InventoryTransactions inTrans = createMovementTrans(MSMaster.getItemId(), newDimId, line.getQuantity(), MSMaster.getTransId(), InventoryTransactionDirection.IN, userData);

                    inTransactions.add(inTrans);
                }
            }

            for (InventoryTransactions inTrans : inTransactions) {
                inTrans.setTransId(MSMaster.getTransId());

                //Order in transaction
                trans.insert(inTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, inTrans, userData);

                //Receive in transaction
                updateOnHand(InventorySummaryUpdateOptions.RECEIVED, inTrans, userData);
                inTrans.setStatus(InventoryTransactionStatus.RECEIVED.toString());
                inTrans.setPhysicalDate(Functions.nowDate());

                trans.update(inTrans, userData);
            }

            logMessage(Level.INFO, ServerInventoryMessageEnum.MOVE_SUCCESS, userData);

            return MSMaster;
        } catch (EMCEntityBeanException ex) {

            throw new EMCStockException(ex.getMessage());
        }
    }

    @Override
    public EMCTable postMoveReservedStockLine(InventoryMoveStockMaster MSMaster, EMCUserData userData) throws EMCStockException {
        if (MSMaster.isSplit()) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockLines.class);
            query.addAnd("masterId", MSMaster.getRecordID());
            List<InventoryMoveStockLines> linesList = util.executeGeneralSelectQuery(query, userData);
            for (InventoryMoveStockLines line : linesList) {
                doMoveReservedStock(MSMaster.getItemId(), MSMaster.getDimensionId(), line.getQuantity(), line.getToLocation(), MSMaster.getTransId(), MSMaster.getQCStatus(), userData);
            }
        } else {
            doMoveReservedStock(MSMaster.getItemId(), MSMaster.getDimensionId(), MSMaster.getQuantity(), MSMaster.getToLocation(), MSMaster.getTransId(), MSMaster.getQCStatus(), userData);
        }
        return MSMaster;
    }

    private void doMoveReservedStock(String itemId, long msDimensionId, double quantity, String toLocal, String transId, String qcStatus, EMCUserData userData) throws EMCStockException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("itemId", itemId);
        query.addAnd("itemDimId", msDimensionId);
        query.addAnd("status", "RESERVED");
        query.addAnd("closedFlag", false);

        EMCQuery nested = new EMCQuery(enumQueryTypes.SELECT, InventoryPickingListLines.class);
        nested.addAnd("issued", false);
        nested.addAnd("cancelled", false);
        nested.addAnd("itemId", itemId);
        nested.addField("transId");
        nested.setSelectDistinctAll(true);
        List<String> notTransId = util.executeGeneralSelectQuery(nested, userData);

        if (notTransId != null && !notTransId.isEmpty()) {
            query.addAndInList("transId", InventoryTransactions.class.getName(), notTransId, true, true);
        }

        List<InventoryTransactions> transList = util.executeGeneralSelectQuery(query, userData);
        if (transList == null || transList.isEmpty()) {
            throw new EMCStockException("Failed to find any reserved stock transactions.");
        }

        BigDecimal requiredQty = BigDecimal.ZERO;
        BigDecimal availableQty = BigDecimal.ZERO;

        long dimensionId;

        InventoryDimensionTable originalDims = dimIdLocal.getDimensionRecord(msDimensionId, userData);

        requiredQty = util.getBigDecimal(quantity);

        dimensionId = dimIdLocal.getDimRecordId(originalDims.getBatchId(), originalDims.getDimension1Id(), originalDims.getDimension2Id(), originalDims.getDimension3Id(), originalDims.getWarehouseId(), toLocal, originalDims.getPalletId(), originalDims.getItemSerialId(), userData);


        for (InventoryTransactions transRec : transList) {
            availableQty = util.getBigDecimal(transRec.getQuantity());

            if (availableQty.compareTo(requiredQty) <= 0) {
                //Unreserve
                try {
                    deleteTransactionAndOnHand(transRec, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                //Move Stock out
                InventoryTransactions outTrans = createMovementTrans(itemId, msDimensionId, transRec.getQuantity(), transId, InventoryTransactionDirection.OUT, userData);
                try {
                    trans.insert(outTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, outTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, outTrans, userData);
                userData.setUserData(10, qcStatus);
                updateOnHand(InventorySummaryUpdateOptions.DELIVERED, outTrans, userData);
                outTrans.setStatus(InventoryTransactionStatus.DELIVERED.toString());
                outTrans.setPhysicalDate(Functions.nowDate());
                try {
                    trans.update(outTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                //Move Stock In
                InventoryTransactions inTrans = createMovementTrans(itemId, dimensionId, transRec.getQuantity(), transId, InventoryTransactionDirection.IN, userData);
                try {
                    trans.insert(inTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, inTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.RECEIVED, inTrans, userData);
                inTrans.setStatus(InventoryTransactionStatus.RECEIVED.toString());
                inTrans.setPhysicalDate(Functions.nowDate());
                try {
                    trans.update(inTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }

                query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                query.addAnd("itemId", itemId);
                query.addAnd("itemDimId", dimensionId);
                query.addAnd("transId", transRec.getTransId());
                query.addAnd("refNumber", transRec.getRefNumber());
                InventoryTransactions reservedTrans = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
                if (reservedTrans == null) {
                    //Reserved New Stock
                    reservedTrans = (InventoryTransactions) doClone(transRec, userData);
                    reservedTrans.setItemDimId(dimensionId);
                    try {
                        trans.insert(reservedTrans, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException(ex.getMessage());
                    }
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTrans, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTrans, userData);
                } else {
                    reservedTrans.setQuantity(reservedTrans.getQuantity() + transRec.getQuantity());
                    try {
                        trans.update(reservedTrans, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException(ex.getMessage());
                    }
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTrans, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTrans, userData);
                }
            } else {
                //Unreserve
                try {
                    deleteTransactionAndOnHand(transRec, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                //Rereserve Unmoved
                InventoryTransactions reservedTrans = (InventoryTransactions) doClone(transRec, userData);
                reservedTrans.setQuantity(availableQty.subtract(requiredQty).doubleValue());
                try {
                    trans.insert(reservedTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTrans, userData);
                //Move Stock out
                InventoryTransactions outTrans = createMovementTrans(itemId, msDimensionId, requiredQty.doubleValue(), transId, InventoryTransactionDirection.OUT, userData);
                try {
                    trans.insert(outTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, outTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, outTrans, userData);
                userData.setUserData(10, qcStatus);
                updateOnHand(InventorySummaryUpdateOptions.DELIVERED, outTrans, userData);
                outTrans.setStatus(InventoryTransactionStatus.DELIVERED.toString());
                outTrans.setPhysicalDate(Functions.nowDate());
                try {
                    trans.update(outTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                //Move Stock In
                InventoryTransactions inTrans = createMovementTrans(itemId, dimensionId, requiredQty.doubleValue(), transId, InventoryTransactionDirection.IN, userData);
                try {
                    trans.insert(inTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, inTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.RECEIVED, inTrans, userData);
                inTrans.setStatus(InventoryTransactionStatus.RECEIVED.toString());
                inTrans.setPhysicalDate(Functions.nowDate());
                try {
                    trans.update(inTrans, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException(ex.getMessage());
                }
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                query.addAnd("itemId", itemId);
                query.addAnd("itemDimId", dimensionId);
                query.addAnd("transId", transRec.getTransId());
                query.addAnd("refNumber", transRec.getRefNumber());
                reservedTrans = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
                if (reservedTrans == null) {
                    //Reserved New Stock
                    reservedTrans = (InventoryTransactions) doClone(transRec, userData);
                    reservedTrans.setQuantity(requiredQty.doubleValue());
                    reservedTrans.setItemDimId(dimensionId);
                    try {
                        trans.insert(reservedTrans, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException(ex.getMessage());
                    }
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTrans, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTrans, userData);
                } else {
                    reservedTrans.setQuantity(reservedTrans.getQuantity() + requiredQty.doubleValue());
                    try {
                        trans.update(reservedTrans, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException(ex.getMessage());
                    }
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTrans, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTrans, userData);
                }
            }
            requiredQty = requiredQty.subtract(availableQty);
            if (requiredQty.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }
        if (requiredQty.compareTo(BigDecimal.ZERO) > 0) {
            throw new EMCStockException("Not enought stock available");
        }
    }

    /**
     * This method creates an ordered transaction with the specified direction
     * for a stock movement.
     */
    private InventoryTransactions createMovementTrans(String itemId, long dimId, double moveQuantity, String transId, InventoryTransactionDirection direction, EMCUserData userData) {
        InventoryTransactions transaction = new InventoryTransactions();
        transaction.setItemId(itemId);
        transaction.setItemDimId(dimId);

        transaction.setQuantity(moveQuantity);

        double cost = itemMasterBean.getCostPrice(itemId, dimId, userData);
        transaction.setCost(cost * transaction.getQuantity());

        transaction.setRefNumber(transId);
        transaction.setTransId(transId);
        transaction.setDirection(direction.toString());
        transaction.setRefType(InventoryTransactionsRefType.Simple_Movement.toString());
        transaction.setType(InventoryTransactionTypes.MO.toString());
        transaction.setStatus(InventoryTransactionStatus.ORDERED.toString());

        return transaction;
    }

    /**
     * Handles the post of WO picking list
     *
     * @param lineToPick
     * @param txH
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    private EMCTable postWOPickingList(InventoryPickingListLines lineToPick, TransactionHelper txH, EMCUserData userData) throws EMCStockException {
        long dimId = this.dimIdLocal.getDimRecordId(lineToPick.getBatch(), lineToPick.getDimension1(),
                lineToPick.getDimension2(), lineToPick.getDimension3(), lineToPick.getWarehouse(),
                lineToPick.getLocation(), lineToPick.getPallet(), lineToPick.getSerial(), userData);

        //Fetch Reserved Tx
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        query.addAnd("transId", lineToPick.getTransId());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("type", InventoryTransactionTypes.WO.toString());
        query.addAnd("itemDimId", dimId);
        query.addAnd("refType", InventoryTransactionsRefType.Works_Order.toString());
        InventoryTransactions reservedTx = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

        if (reservedTx == null) {
            throw new EMCStockException(getMessage(ServerInventoryMessageEnum.PL_TRANS_NOT_FOUND, userData, lineToPick.getLineNum(), referenceBean.checkItemReference(lineToPick.getItemId(), userData)[1], lineToPick.getDimension1(), lineToPick.getDimension3(), lineToPick.getDimension2()));
        }

        //ensure enough stock is available if over issued and reserve extra stock
        if (util.compareDouble(lineToPick.getIssueQty(), reservedTx.getQuantity()) == 1) {
            double reserve = reservedTx.getQuantity() + (lineToPick.getIssueQty() - reservedTx.getQuantity());
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTx, userData); //clear stock reservation
            reservedTx.setQuantity(reserve);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTx, userData); //reserve it again
        }

        //create new delivered transaction
        InventoryTransactions deliveredTx = new InventoryTransactions();
        deliveredTx.setTransId(reservedTx.getTransId());
        deliveredTx.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
        deliveredTx.setDirection(InventoryTransactionDirection.OUT.toString());
        deliveredTx.setType(InventoryTransactionTypes.WO.toString());
        deliveredTx.setRefType(InventoryTransactionsRefType.Picking_List_Lines.toString());
        deliveredTx.setRefNumber(reservedTx.getRefNumber());
        deliveredTx.setStatus(InventoryTransactionStatus.DELIVERED.toString());
        deliveredTx.setItemDimId(reservedTx.getItemDimId());
        deliveredTx.setItemId(reservedTx.getItemId());
        deliveredTx.setPhysicalDate(new Date());
        //get Cost price
        double cost = itemMasterBean.getCostPrice(reservedTx.getItemId(), reservedTx.getItemDimId(), userData);
        deliveredTx.setCost(lineToPick.getIssueQty() * cost);
        deliveredTx.setQuantity(lineToPick.getIssueQty());

        try {
            trans.insert(deliveredTx, userData);
        } catch (EMCEntityBeanException ex) {
            throw new EMCStockException(ex.getMessage());
        }
        updateOnHand(InventorySummaryUpdateOptions.DELIVERED, deliveredTx, userData);

        //delete reserved Tx if fully delivered or reduce reserved qty if not
        if (util.compareDouble(lineToPick.getIssueQty(), reservedTx.getQuantity()) == 0) {
            try {
                deleteTransactionAndOnHand(reservedTx, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException(ex.getMessage());
            }
        } else if (util.compareDouble(lineToPick.getIssueQty(), reservedTx.getQuantity()) == -1) {
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTx, userData);//clear stock reservation
            reservedTx.setQuantity(reservedTx.getQuantity() - lineToPick.getIssueQty());
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTx, userData);//reserve it again
        }

        return lineToPick;
    }

    /**
     * Updates the transactions according to the stock issues on the picking
     * list.
     *
     * @param woBom
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCTable postPickingList(InventoryPickingListLines lineToPick, TransactionHelper txH, EMCUserData userData) throws EMCStockException {
        switch (txH.getPickType()) {
            case WO:
                return postWOPickingList(lineToPick, txH, userData);
            case SO:
                return postSOPickingList(lineToPick, txH, userData);
            default:
                throw new EMCStockException("Picking Post does not support this type of order");
        }
    }

    /**
     * Re-reserves the stock no the pick list line with the new values
     *
     * @param lineToPick
     * @param txH
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public EMCTable postReReservePickList(InventoryReReservePickList lineToPick, TransactionHelper txH, EMCUserData userData) throws EMCStockException, EMCEntityBeanException {
        long dimId = this.dimIdLocal.getDimRecordId(lineToPick.getFromBatch(), lineToPick.getDimension1(),
                lineToPick.getDimension2(), lineToPick.getDimension3(), lineToPick.getFromWarehouse(),
                lineToPick.getFromLocation(), lineToPick.getFromPallet(), lineToPick.getFromSerial(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        query.addAnd("transId", lineToPick.getTransId());
        query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("type", InventoryTransactionTypes.WO.toString());
        query.addAnd("itemDimId", dimId);
        List<InventoryTransactions> transList = util.executeGeneralSelectQuery(query, userData);
        if (transList == null || transList.isEmpty()) {
            throw new EMCStockException("No transaction found for line no. " + lineToPick.getPickListLine() + ".");
        } else {
            InventoryTransactions newTx = null;
            for (InventoryTransactions oldTrans : transList) {
                if (newTx == null) {
                    newTx = (InventoryTransactions) doClone(oldTrans, userData);
                }
                oldTrans.setQuantity(0);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, oldTrans, userData);
                trans.update(oldTrans, userData);
                deleteTransactionAndOnHand(oldTrans, userData);
            }
            dimId = this.dimIdLocal.getDimRecordId(lineToPick.getToBatch(), lineToPick.getDimension1(),
                    lineToPick.getDimension2(), lineToPick.getDimension3(), lineToPick.getToWarehouse(),
                    lineToPick.getToLocation(), lineToPick.getToPallet(), lineToPick.getToSetial(), userData);
            newTx.setItemDimId(dimId);
            newTx.setQuantity(lineToPick.getQuantity());
            trans.insert(newTx, userData);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, newTx, userData);
        }
        return lineToPick;
    }

    private void deleteMovementJournalRegisteredLines(String transId, String journalNumber, EMCUserData userData) throws EMCStockException {
        //Batch Consolidation Manages resistration internaly so no deletion is required
        if (util.checkUserData(5, true, userData)) {
            return;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
        query.addAnd("transId", transId);
        query.addAnd("masterId", journalNumber);

        List<InventoryRemoveRegister> registerRecords = (List<InventoryRemoveRegister>) util.executeGeneralSelectQuery(query, userData);

        try {
            for (InventoryRemoveRegister registerRecord : registerRecords) {
                removeRegBean.delete(registerRecord, userData);
            }
        } catch (EMCEntityBeanException ex) {
            throw new EMCStockException(ex.getMessage());
        }

        logMessage(Level.WARNING, ServerInventoryMessageEnum.JRREGDELETED, userData);
    }

    /**
     * Cancels (unreserves) the specified Picking List line.
     *
     * @param pickingListMaster Picking List master to which the line belongs.
     * @param pickingListLine Line to cancel.
     * @param userData User data.
     * @return The line that has been cancelled.
     * @throws EMCEntityBeanException, EMCStockException
     */
    @Override
    public EMCTable cancelPickingListLine(InventoryPickingListMaster pickingListMaster, InventoryPickingListLines pickingListLine, EMCUserData userData) throws EMCEntityBeanException, EMCStockException {

        return pickingListLine;
    }

    private EMCTable postSOPickingList(InventoryPickingListLines lineToPick, TransactionHelper txH, EMCUserData userData) throws EMCStockException {
        long dimId = this.dimIdLocal.getDimRecordId(lineToPick.getBatch(), lineToPick.getDimension1(),
                lineToPick.getDimension2(), lineToPick.getDimension3(), lineToPick.getWarehouse(),
                lineToPick.getLocation(), lineToPick.getPallet(), lineToPick.getSerial(), userData);

        //Fetch Reserved Tx
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", lineToPick.getTransId());
        query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("type", InventoryTransactionTypes.SO.toString());
        query.addAnd("itemDimId", dimId);
        query.addAnd("refType", InventoryTransactionsRefType.Sales_Order.toString());
        InventoryTransactions reservedTx = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

        if (reservedTx == null) {
            if (!isBlank(lineToPick.getRefTransaction())) {
                Logger.getLogger("emc").log(Level.WARNING, "Item " + referenceBean.checkItemReference(lineToPick.getItemId(), userData)[1]
                        + " has not been assembled yet.", userData);
                return null;
            }
            throw new EMCStockException(getMessage(ServerInventoryMessageEnum.PL_TRANS_NOT_FOUND, userData, lineToPick.getLineNum(), referenceBean.checkItemReference(lineToPick.getItemId(), userData)[1], lineToPick.getDimension1(), lineToPick.getDimension3(), lineToPick.getDimension2()));
        }

        if (util.compareDouble(reservedTx.getQuantity(), lineToPick.getIssueQty()) < 0) {
            if (!isBlank(lineToPick.getRefTransaction())) {
                Logger.getLogger("emc").log(Level.WARNING, "Item " + referenceBean.checkItemReference(lineToPick.getItemId(), userData)[1]
                        + " has not been assembled yet.", userData);
                throw new EMCStockException("Item " + referenceBean.checkItemReference(lineToPick.getItemId(), userData)[1]
                        + " has not been assembled yet.");
            }

            //ensure enough stock is available if over issued and reserve extra stock
            if (util.compareDouble(lineToPick.getIssueQty(), reservedTx.getQuantity()) == 1) {
                double reserve = reservedTx.getQuantity() + (lineToPick.getIssueQty() - reservedTx.getQuantity());
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTx, userData); //clear stock reservation
                reservedTx.setQuantity(reserve);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTx, userData); //reserve it again
            }
        }

        //create new delivered transaction
        InventoryTransactions deliveredTx = new InventoryTransactions();
        deliveredTx.setTransId(reservedTx.getTransId());
        deliveredTx.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
        deliveredTx.setDirection(InventoryTransactionDirection.OUT.toString());
        deliveredTx.setType(InventoryTransactionTypes.SO.toString());
        deliveredTx.setRefType(InventoryTransactionsRefType.Picking_List_Lines.toString());
        deliveredTx.setRefNumber(reservedTx.getRefNumber());
        deliveredTx.setStatus(InventoryTransactionStatus.DELIVERED.toString());
        deliveredTx.setItemDimId(reservedTx.getItemDimId());
        deliveredTx.setItemId(reservedTx.getItemId());
        deliveredTx.setPhysicalDate(new Date());
        //get Cost price
        double cost = itemMasterBean.getCostPrice(reservedTx.getItemId(), reservedTx.getItemDimId(), userData);
        deliveredTx.setCost(lineToPick.getIssueQty() * cost);
        deliveredTx.setQuantity(lineToPick.getIssueQty());

        try {
            trans.insert(deliveredTx, userData);
        } catch (EMCEntityBeanException ex) {
            throw new EMCStockException(ex.getMessage());
        }
        updateOnHand(InventorySummaryUpdateOptions.DELIVERED, deliveredTx, userData);

        //delete reserved Tx if fully delivered or reduce reserved qty if not
        if (util.compareDouble(lineToPick.getIssueQty(), reservedTx.getQuantity()) == 0) {
            try {
                deleteTransactionAndOnHand(reservedTx, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException(ex.getMessage());
            }
        } else if (util.compareDouble(lineToPick.getIssueQty(), reservedTx.getQuantity()) == -1) {
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTx, userData); //clear stock reservation
            reservedTx.setQuantity(reservedTx.getQuantity() - lineToPick.getIssueQty());
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTx, userData); //reserve it again
        }
        return lineToPick;
    }

    private boolean validateTransferJournalDimenstions(InventoryJournalLines theLine, EMCUserData userData) {
        //Check To Warehouse
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
        query.addAnd("warehouseId", theLine.getToWarehouse());
        if (!util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The To Warehouse entered does not exist.", userData);
            return false;
        }

        //Check To Location
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
        query.addAnd("warehouseId", theLine.getToWarehouse());
        query.addAnd("locationId", theLine.getToLocation());
        if (!util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The To Location entered does not exist in the " + theLine.getToWarehouse() + " warehouse.", userData);
            return false;
        }

        return true;
    }
}
