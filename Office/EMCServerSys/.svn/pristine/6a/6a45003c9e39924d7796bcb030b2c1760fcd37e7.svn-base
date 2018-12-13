/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.debtors;

import emc.bus.base.logic.EMCUOMConversionException;
import emc.bus.base.numbersequences.BaseNumberSequenceLogicLocal;
import emc.bus.debtors.creditnoteregister.DebtorsCreditNoteRegisterLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteLinesLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceLinesLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.TransactionsSuperClass;
import emc.entity.base.numbersequences.BaseAvailableSequenceNumbers;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.debtors.creditnotes.DebtorsCreditNoteReturnOptions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerProductionMessageEnum;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Transaction logic bean for Inventory transactions originating from the Debtors module.
 *
 * @date        : 24 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsInvTransactionLogicBean extends TransactionsSuperClass implements DebtorsInvTransactionLogicLocal {

    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private DebtorsCustomerInvoiceLinesLocal invoiceLinesBean;
    @EJB
    private DebtorsCreditNoteLinesLocal creditNoteLinesBean;
    @EJB
    private DebtorsCreditNoteRegisterLocal creditNoteRegisterBean;
    @EJB
    private BaseNumberSequenceLogicLocal numberSeqBean;

    /** Creates a new instance of DebtorsInvTransactionLogicBean */
    public DebtorsInvTransactionLogicBean() {
    }

    /**
     * Posts a Credit Note line when it is saved.
     * @param creditNoteLine Credit Note line to post.
     * @param userData User data.
     * @return The transaction that was posted.
     * @throws EMCEntityBeanException
     * @throws EMCStockException
     */
    public InventoryTransactions postCreditNoteLine(DebtorsCreditNoteLines creditNoteLine, EMCUserData userData) throws EMCEntityBeanException, EMCStockException {
        InventoryTransactions transaction;
        long dimId = dimIdLocal.getDimRecordId(creditNoteLine.getBatch(), creditNoteLine.getDimension1(), creditNoteLine.getDimension2(),
                creditNoteLine.getDimension3(), creditNoteLine.getWarehouse(), creditNoteLine.getLocation(), creditNoteLine.getPallet(),
                creditNoteLine.getSerial(), userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", creditNoteLine.getInventTransId());
        query.addAnd("refNumber", creditNoteLine.getInvCNNumber());
        query.addAnd("type", InventoryTransactionTypes.CN.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Credit_Note.toString());
        //query.addAnd("itemId", creditNoteLine.getItemId());
        query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
        //Do not include item or dim id, as item or dimensions may have been changed
        query.addAnd("direction", InventoryTransactionDirection.IN.toString());

        transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

        if (transaction == null) {
            //Create new transaction
            transaction = new InventoryTransactions();
            transaction.setTransId(creditNoteLine.getInventTransId());
            transaction.setRefNumber(creditNoteLine.getInvCNNumber());
            transaction.setType(InventoryTransactionTypes.CN.toString());
            transaction.setRefType(InventoryTransactionsRefType.Credit_Note.toString());
            transaction.setStatus(InventoryTransactionStatus.ORDERED.toString());
            transaction.setDirection(InventoryTransactionDirection.IN.toString());
            transaction.setExpectedDate(creditNoteLine.getCreatedDate());
        }

        //Ensure that correct item, dim id and quantity is always reflected on transaction
        transaction.setItemId(creditNoteLine.getItemId());
        transaction.setItemDimId(dimId);
        //Credit Note quantity is negative.  
        transaction.setQuantity(new BigDecimal(-1).multiply(creditNoteLine.getQuantity()).doubleValue());
        //TODO:  Should we set a cost on in transactions?  See purchasing.

        //If recordID is zero, assume that a new transactions was created; do not try to delete transaction.
        if (creditNoteLine.getQuantity().compareTo(BigDecimal.ZERO) == 0 && transaction.getRecordID() != 0) {
            deleteTransactionAndOnHand(transaction, userData);
            return transaction;
        } else {
            if (transaction.getRecordID() == 0) {
                trans.insert(transaction, userData);
            } else {
                trans.update(transaction, userData);
            }
        }

        updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, transaction, userData);

        return transaction;
    }

    /**
     * Posts a customer invoice line when it is saved.
     * @param invoiceLine Invoice line to post.
     * @param userData User data.
     * @return The transaction that was posted.
     * @throws EMCEntityBeanException
     * @throws EMCStockException
     */
    public InventoryTransactions postCustomerInvoiceLine(DebtorsCustomerInvoiceLines invoiceLine, EMCUserData userData) throws EMCEntityBeanException, EMCStockException {
        InventoryTransactions transaction;
        long dimId = dimIdLocal.getDimRecordId(invoiceLine.getBatch(), invoiceLine.getDimension1(), invoiceLine.getDimension2(),
                invoiceLine.getDimension3(), invoiceLine.getWarehouse(), invoiceLine.getLocation(), invoiceLine.getPallet(),
                invoiceLine.getSerial(), userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", invoiceLine.getInventTransId());
        query.addAnd("refNumber", invoiceLine.getInvCNNumber());
        query.addAnd("type", InventoryTransactionTypes.INV.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Invoice.toString());
        query.addAnd("itemId", invoiceLine.getItemId());
        query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
        //Do not include dim id, as line dimensions may be changed
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());

        transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

        if (transaction != null) {

            //Unreserve
            //transaction.setQuantity(0);
            transaction.setCost(0);

            trans.update(transaction, userData);

            super.updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, transaction, userData);
            super.deleteTransactionAndOnHand(transaction, userData);
        }

        //If line deleted
        if (invoiceLine.getQuantity().equals(BigDecimal.ZERO)) {
            return null;
        }

        //Create new trans
        transaction = new InventoryTransactions();
        transaction.setTransId(invoiceLine.getInventTransId());
        transaction.setRefNumber(invoiceLine.getInvCNNumber());
        transaction.setRefType(InventoryTransactionsRefType.Invoice.toString());
        transaction.setType(InventoryTransactionTypes.INV.toString());
        transaction.setItemId(invoiceLine.getItemId());
        transaction.setItemDimId(dimId);
        transaction.setDirection(InventoryTransactionDirection.OUT.toString());

        double actualQty = 0;
        try {
            //Get actual quantity in base UOM
            actualQty = uomConv.convertToItemBase(invoiceLine.getItemId(), invoiceLine.getQuantity().doubleValue(), invoiceLine.getUom(), userData);
        } catch (EMCUOMConversionException ex) {
            ex.printStackTrace();
            throw new EMCEntityBeanException("Failed to convert " + invoiceLine.getUom() + " to item base UoM.");
        }

        transaction.setQuantity(invoiceLine.getQuantity().doubleValue());

        //Get item cost
        transaction.setCost(itemBean.getCostPrice(invoiceLine.getItemId(), dimId, userData) * invoiceLine.getQuantity().doubleValue());

        trans.insert(transaction, userData);
        updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, transaction, userData);

        //Reserve
        updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, transaction, userData);

        return transaction;
    }

    /**
     * This method is used to update stock transactions when posting an invoice.
     * @param invoiceMaster Invoice to post.
     * @param userData User data.
     * @return The invoice that was posted.
     * @throws EMCStockException
     * @throws EMCEntityBeanException
     */
    public DebtorsCustomerInvoiceMaster postCustomerInvoice(DebtorsCustomerInvoiceMaster invoiceMaster, EMCUserData userData) throws EMCStockException, EMCEntityBeanException {
        if (!invoiceMaster.isInvoiceStock()) {
            throw new EMCEntityBeanException("Stock should not be issued against the specified Invoice.");
        }

        List<DebtorsCustomerInvoiceLines> invoiceLines = invoiceLinesBean.getInvoiceLines(invoiceMaster.getInvCNNumber(), userData);

        for (DebtorsCustomerInvoiceLines invoiceLine : invoiceLines) {
            InventoryItemMaster item = itemBean.findItem(invoiceLine.getItemId(), userData);

            if (item == null) {
                throw new EMCEntityBeanException("Item does not exist - " + invoiceLine.getItemId());
            } else {
                if (InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                    //Do not create transactions for service items.
                    continue;
                }
            }

            //Get transaction
            long dimId = dimIdLocal.getDimRecordId(invoiceLine.getBatch(), invoiceLine.getDimension1(), invoiceLine.getDimension2(),
                    invoiceLine.getDimension3(), invoiceLine.getWarehouse(), invoiceLine.getLocation(), invoiceLine.getPallet(),
                    invoiceLine.getSerial(), userData);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            query.addAnd("transId", invoiceLine.getInventTransId());
            query.addAnd("refNumber", invoiceLine.getInvCNNumber());
            query.addAnd("type", InventoryTransactionTypes.INV.toString());
            query.addAnd("refType", InventoryTransactionsRefType.Invoice.toString());
            query.addAnd("itemId", invoiceLine.getItemId());
            query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
            query.addAnd("itemDimId", dimId);
            query.addAnd("direction", InventoryTransactionDirection.OUT.toString());

            InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

            transaction.setStatus(InventoryTransactionStatus.SOLD.toString());
            transaction.setPhysicalDate(Functions.nowDate());

            trans.update(transaction, userData);

            updateOnHand(InventorySummaryUpdateOptions.DELIVERED, transaction, userData);
        }

        return invoiceMaster;
    }

    /**
     * This method is used to update stock transactions when posting a Credit Note.
     * @param creditNoteMaster Credit Note to post.
     * @param userData User data.
     * @param generateNewSTKNumbers Indicates whether new batch numbers should be generated
     * if the credit note is returning goods into stock.
     * @return The Credit Note that was posted.
     * @throws EMCStockException
     * @throws EMCEntityBeanException
     */
    public DebtorsCreditNoteMaster postCreditNote(DebtorsCreditNoteMaster creditNoteMaster, boolean generateNewSTKNumbers, EMCUserData userData) throws EMCStockException, EMCEntityBeanException {
        if (!creditNoteMaster.isInvoiceStock()) {
            throw new EMCEntityBeanException("Stock should not be issued against the specified Credit Note.");
        }

        DebtorsCreditNoteReturnOptions returnOption = DebtorsCreditNoteReturnOptions.fromString(creditNoteMaster.getReturnOption());
        List<String> batchNumbers = null;
        List<String> batchNumberRecIds = null;

        List<DebtorsCreditNoteLines> creditNoteLines = creditNoteLinesBean.getCreditNoteLines(creditNoteMaster.getInvCNNumber(), userData);

        for (DebtorsCreditNoteLines creditNoteLine : creditNoteLines) {

            InventoryItemMaster item = itemBean.findItem(creditNoteLine.getItemId(), userData);

            if (item == null) {
                throw new EMCEntityBeanException("Item does not exist - " + creditNoteLine.getItemId());
            } else {
                if (InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                    //Do not create transactions for service items.
                    continue;
                }
            }
            DebtorsCustomerInvoiceLines invoiceLine = null;
            if (!isBlank(creditNoteMaster.getRefInvoiceNo())) {
                //Only attempt this if the Credit Note is related to an Invoice.  Ensure that dimensions and item id match, as well as line number.
                invoiceLine = invoiceLinesBean.getInvoiceLine(creditNoteMaster.getRefInvoiceNo(), creditNoteLine, userData);

                if (invoiceLine != null) {
                    //At present, lines may be added to an Invoice or changed, so not all lines will
                    //have a corresponding Invoice Line.
                    if (invoiceLine.getQuantityReturned().add(creditNoteLine.getQuantity().abs()).compareTo(invoiceLine.getQuantity()) > 0) {
                        //Returning more stock than what was on Invoice.
                        throw new EMCStockException("Total quantity returned on Invoice Line (" + item == null ? creditNoteLine.getItemId() : item.getItemReference() + ")" +
                                " will exceed Invoice Line quantity.");
                    } else {
                        invoiceLine.setQuantityReturned(invoiceLine.getQuantityReturned().add(creditNoteLine.getQuantity()));

                        //Indicates the transactions should not be posted.  Check in update() on invoice lines bean.
                        userData.setUserData(7, true);
                        invoiceLinesBean.update(invoiceLine, userData);
                    }
                }
            }

            //Get transaction
            long dimId = dimIdLocal.getDimRecordId(creditNoteLine.getBatch(), creditNoteLine.getDimension1(), creditNoteLine.getDimension2(),
                    creditNoteLine.getDimension3(), creditNoteLine.getWarehouse(), creditNoteLine.getLocation(), creditNoteLine.getPallet(),
                    creditNoteLine.getSerial(), userData);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            query.addAnd("transId", creditNoteLine.getInventTransId());
            query.addAnd("refNumber", creditNoteLine.getInvCNNumber());
            query.addAnd("type", InventoryTransactionTypes.CN.toString());
            query.addAnd("refType", InventoryTransactionsRefType.Credit_Note.toString());
            query.addAnd("itemId", creditNoteLine.getItemId());
            query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            query.addAnd("itemDimId", dimId);
            query.addAnd("direction", InventoryTransactionDirection.IN.toString());

            InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

            //Delete ordered in transaction
            deleteTransactionAndOnHand(transaction, userData);

            List<DebtorsCreditNoteRegister> registerLines = creditNoteRegisterBean.getRegisterLines(creditNoteLine.getInvCNNumber(), creditNoteLine.getInventTransId(), userData);

            double registeredQuantity = 0;

            System.out.println("Generate new numbers: " + generateNewSTKNumbers);
            //Batch numbers should only be generated for the STK return option
          
            Long invoiceTrans;
            for (DebtorsCreditNoteRegister registerLine : registerLines) {
                registeredQuantity += registerLine.getQuantity();

                InventoryTransactions registerTrans = new InventoryTransactions();
                util.copyFields(transaction, registerTrans, userData);

                registerTrans.setQuantity(Math.abs(registerLine.getQuantity()));

                if (returnOption == DebtorsCreditNoteReturnOptions.STK && generateNewSTKNumbers) {
                    String nextBatch = batchNumbers.remove(0);
                    //Use new batch numbers in List
                    dimId = dimIdLocal.getDimRecordId(nextBatch, creditNoteLine.getDimension1(), creditNoteLine.getDimension2(),
                            creditNoteLine.getDimension3(), registerLine.getWarehouse(), registerLine.getLocation(), registerLine.getPallet(),
                            registerLine.getSerial(), userData);

                    //Update register record with new batch
                    registerLine.setNewBatch(nextBatch);

                    creditNoteRegisterBean.doUpdate(registerLine, userData);
                } else {
                    //Use batch on register records (always use for factory shop)
                    dimId = dimIdLocal.getDimRecordId(registerLine.getBatch(), creditNoteLine.getDimension1(), creditNoteLine.getDimension2(),
                            creditNoteLine.getDimension3(), registerLine.getWarehouse(), registerLine.getLocation(), registerLine.getPallet(),
                            registerLine.getSerial(), userData);
                }
                registerTrans.setItemDimId(dimId);

                if (invoiceLine != null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                    query.addAnd("refNumber", creditNoteMaster.getSalesOrderNo());
                    query.addAnd("status", InventoryTransactionStatus.DELIVERED.toString());
                    query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
                    query.addAnd("itemId", creditNoteLine.getItemId());
                    query.addAnd("itemDimId", dimIdLocal.getDimRecordId(registerLine.getBatch(), invoiceLine.getDimension1(), invoiceLine.getDimension2(),
                            invoiceLine.getDimension3(), invoiceLine.getWarehouse(), invoiceLine.getLocation(), invoiceLine.getPallet(),
                            registerLine.getSerial(), userData));
                    query.addField("recordID");
                    invoiceTrans = (Long) util.executeSingleResultQuery(query, userData);
                    if (invoiceTrans != null) {
                        registerTrans.setRefTransId(invoiceTrans);
                    }
                }

                trans.insert(registerTrans, userData);

                registerTrans.setStatus(InventoryTransactionStatus.RECEIVED.toString());
                registerTrans.setPhysicalDate(Functions.nowDate());

                trans.update(registerTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.RECEIVED, registerTrans, userData);

                if (returnOption == DebtorsCreditNoteReturnOptions.FSTR) {
                    //Issue stock to factory shop.
                    InventoryTransactions issueTrans = new InventoryTransactions();
                    util.copyFields(registerTrans, issueTrans, userData);

                    issueTrans.setDirection(InventoryTransactionDirection.OUT.toString());

                    //Order transaction
                    issueTrans.setStatus(InventoryTransactionStatus.ORDERED.toString());

                    trans.insert(issueTrans, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, issueTrans, userData);

                    //Do a 'fake' unreserve so that the reservation logic can see the stock that just came in.
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, issueTrans, userData);

                    //Reserve transaction
                    issueTrans.setStatus(InventoryTransactionStatus.RESERVED.toString());

                    trans.update(issueTrans, userData);
                    updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, issueTrans, userData);

                    //Deliver transaction
                    issueTrans.setStatus(InventoryTransactionStatus.DELIVERED.toString());

                    trans.update(issueTrans, userData);
                    updateOnHand(InventorySummaryUpdateOptions.DELIVERED, issueTrans, userData);
                }
            }

            if (util.compareDouble(registeredQuantity, creditNoteLine.getQuantity().doubleValue()) != 0) {
                throw new EMCEntityBeanException("Registered quantity does not match line quantity.");
            }

            if (returnOption == DebtorsCreditNoteReturnOptions.STK && generateNewSTKNumbers) {
                //Update used sequence numbers
                EMCQuery numQuery = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                numQuery.addAndInList("recordID", batchNumberRecIds, false, false);
                numQuery.addSet("status", 2);//set it to used
                commandManager.setTransactionSucceedQuery(registry.getTransactionKey(), numQuery.toString());
                //load query to use on fail
                EMCQuery fQuery = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                fQuery.addAndInList("recordID", batchNumberRecIds, false, false);
                fQuery.addSet("status", 0);//set it to available
                commandManager.setTransactionFailQuery(registry.getTransactionKey(), fQuery.toString());
            }
        }

        return creditNoteMaster;
    }

    @Override
    public DebtorsCustomerInvoiceLines cancelCustomerInvoiceLine(DebtorsCustomerInvoiceLines invoiceLine, EMCUserData userData) throws EMCEntityBeanException, EMCStockException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", invoiceLine.getInventTransId());
        query.addAnd("refNumber", invoiceLine.getInvCNNumber());
        query.addAnd("type", InventoryTransactionTypes.INV.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Invoice.toString());
        query.addAnd("itemId", invoiceLine.getItemId());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());

        InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

        if (transaction != null) {
            super.updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, transaction, userData);
            super.deleteTransactionAndOnHand(transaction, userData);
        }

        return invoiceLine;
    }

    @Override
    public DebtorsCreditNoteLines cancelCreditNoteLine(DebtorsCreditNoteLines invoiccreditNoteLineeLine, EMCUserData userData) throws EMCEntityBeanException, EMCStockException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", invoiccreditNoteLineeLine.getInventTransId());
        query.addAnd("refNumber", invoiccreditNoteLineeLine.getInvCNNumber());
        query.addAnd("type", InventoryTransactionTypes.INV.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Credit_Note.toString());
        query.addAnd("itemId", invoiccreditNoteLineeLine.getItemId());
        query.addAnd("direction", InventoryTransactionDirection.IN.toString());

        InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

        if (transaction != null) {
            super.updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, transaction, userData);
            super.deleteTransactionAndOnHand(transaction, userData);
        }

        return invoiccreditNoteLineeLine;
    }
}
