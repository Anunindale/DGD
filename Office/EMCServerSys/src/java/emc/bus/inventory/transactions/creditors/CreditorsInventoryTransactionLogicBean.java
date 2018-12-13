/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.creditors;

import emc.bus.base.logic.EMCUOMConversionException;
import emc.bus.creditors.creditnoteinvoicelines.CreditorsCreditNoteInvoiceLinesLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.TransactionsSuperClass;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CreditorsInventoryTransactionLogicBean extends TransactionsSuperClass implements CreditorsInventoryTransactionLogicLocal {

    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private CreditorsCreditNoteInvoiceLinesLocal invoiceLinesBean;

    public CreditorsInventoryTransactionLogicBean() {
    }

    /**
     * Posts a customer invoice line when it is saved.
     * @param invoiceLine Invoice line to post.
     * @param userData User data.
     * @return The transaction that was posted.
     * @throws EMCStockException
     */
    @Override
    public InventoryTransactions postInvoiceLine(CreditorsCreditNoteInvoiceLines invoiceLine, EMCUserData userData) throws EMCStockException {
        //Select transaction if exist
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", invoiceLine.getInventoryTransRef());
        query.addAnd("refNumber", invoiceLine.getCreditNoteInvoiceNumber());
        query.addAnd("type", InventoryTransactionTypes.INV.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Invoice.toString());
//        query.addAnd("itemId", invoiceLine.getItemId());
        query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
        query.addAnd("direction", InventoryTransactionDirection.IN.toString());
        InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
        //Transaction did exist
        if (transaction != null) {
            try {
                //Unorder/Unreserve through delete
                super.deleteTransactionAndOnHand(transaction, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException(ex.toString());
            }
        }
        //If line deleted
        if (invoiceLine.getQuantity().equals(BigDecimal.ZERO)) {
            return null;
        }
        //Get Dim Id from invoice line
        long dimId = dimIdLocal.getDimRecordId(invoiceLine.getBatch(), invoiceLine.getDimension1(), invoiceLine.getDimension2(),
                invoiceLine.getDimension3(), invoiceLine.getWarehouse(), invoiceLine.getLocation(), invoiceLine.getPallet(),
                invoiceLine.getSerial(), userData);
        //Create it
        transaction = new InventoryTransactions();
        transaction.setTransId(invoiceLine.getInventoryTransRef());
        transaction.setRefNumber(invoiceLine.getCreditNoteInvoiceNumber());
        transaction.setRefType(InventoryTransactionsRefType.Invoice.toString());
        transaction.setType(InventoryTransactionTypes.INV.toString());
        transaction.setItemId(invoiceLine.getItemId());
        transaction.setItemDimId(dimId);
        transaction.setDirection(InventoryTransactionDirection.IN.toString());
        transaction.setStatus(InventoryTransactionStatus.ORDERED.toString());
        //Set actual quantity in base UOM
        double actualQty = 0;
        try {
            actualQty = uomConv.convertToItemBase(invoiceLine.getItemId(), invoiceLine.getQuantity().doubleValue(), invoiceLine.getUom(), userData);
        } catch (EMCUOMConversionException ex) {
            ex.printStackTrace();
            throw new EMCStockException("Failed to convert " + invoiceLine.getUom() + " to item base UoM.");
        }
        transaction.setQuantity(actualQty);
        //Set item cost
        transaction.setCost(itemBean.getCostPrice(invoiceLine.getItemId(), dimId, userData) * actualQty);
        try {
            //Order
            trans.insert(transaction, userData);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, transaction, userData);
        } catch (Exception ex) {
            throw new EMCStockException(ex.toString());
        }

        return transaction;
    }

    /**
     * Posts a Credit Note line when it is saved.
     * @param creditNoteLine Credit Note line to post.
     * @param userData User data.
     * @return The transaction that was posted.
     * @throws EMCStockException
     */
    @Override
    public InventoryTransactions postCreditNoteLine(CreditorsCreditNoteInvoiceLines creditNoteLine, EMCUserData userData) throws EMCStockException {
        //Select transaction if exist
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", creditNoteLine.getInventoryTransRef());
        query.addAnd("refNumber", creditNoteLine.getCreditNoteInvoiceNumber());
        query.addAnd("type", InventoryTransactionTypes.CN.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Credit_Note.toString());
        query.addAnd("itemId", creditNoteLine.getItemId());
        query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
        //Transaction did exist
        if (transaction != null) {
            try {
                //Unorder through delete
                super.deleteTransactionAndOnHand(transaction, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException(ex.toString());
            }
        }
        //If line deleted
        if (creditNoteLine.getQuantity().equals(BigDecimal.ZERO)) {
            return null;
        }
        //Get Dim Id from invoice line
        long dimId = dimIdLocal.getDimRecordId(creditNoteLine.getBatch(), creditNoteLine.getDimension1(), creditNoteLine.getDimension2(),
                creditNoteLine.getDimension3(), creditNoteLine.getWarehouse(), creditNoteLine.getLocation(), creditNoteLine.getPallet(),
                creditNoteLine.getSerial(), userData);
        //Create it
        transaction = new InventoryTransactions();
        transaction.setTransId(creditNoteLine.getInventoryTransRef());
        transaction.setRefNumber(creditNoteLine.getCreditNoteInvoiceNumber());
        transaction.setRefType(InventoryTransactionsRefType.Credit_Note.toString());
        transaction.setType(InventoryTransactionTypes.CN.toString());
        transaction.setItemId(creditNoteLine.getItemId());
        transaction.setItemDimId(dimId);
        transaction.setDirection(InventoryTransactionDirection.OUT.toString());
        //Set actual quantity in base UOM
        double actualQty = 0;
        try {
            actualQty = uomConv.convertToItemBase(creditNoteLine.getItemId(), creditNoteLine.getQuantity().doubleValue(), creditNoteLine.getUom(), userData);
        } catch (EMCUOMConversionException ex) {
            ex.printStackTrace();
            throw new EMCStockException("Failed to convert " + creditNoteLine.getUom() + " to item base UoM.");
        }
        transaction.setQuantity(actualQty);
        //Set item cost
        transaction.setCost(itemBean.getCostPrice(creditNoteLine.getItemId(), dimId, userData) * actualQty);
        try {
            //Order
            trans.insert(transaction, userData);
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, transaction, userData);
        } catch (Exception ex) {
            throw new EMCStockException(ex.toString());
        }

        return transaction;
    }

    @Override
    public CreditorsCreditNoteInvoiceMaster postInvoice(CreditorsCreditNoteInvoiceMaster invoiceMaster, List<CreditorsCreditNoteInvoiceLines> invoiceLinesList, EMCUserData userData) throws EMCStockException, EMCEntityBeanException {
        if (!invoiceMaster.isStockCreditNoteInvoice()) {
            throw new EMCEntityBeanException("Stock should not be issued against the specified Invoice.");
        }

        InventoryTransactions transaction;

        for (CreditorsCreditNoteInvoiceLines invoiceLine : invoiceLinesList) {
            //Get transaction
            long dimId = dimIdLocal.getDimRecordId(invoiceLine.getBatch(), invoiceLine.getDimension1(), invoiceLine.getDimension2(),
                    invoiceLine.getDimension3(), invoiceLine.getWarehouse(), invoiceLine.getLocation(), invoiceLine.getPallet(),
                    invoiceLine.getSerial(), userData);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            query.addAnd("transId", invoiceLine.getInventoryTransRef());
            query.addAnd("refNumber", invoiceLine.getCreditNoteInvoiceNumber());
            query.addAnd("type", InventoryTransactionTypes.INV.toString());
            query.addAnd("refType", InventoryTransactionsRefType.Invoice.toString());
            query.addAnd("itemId", invoiceLine.getItemId());
            query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            query.addAnd("itemDimId", dimId);
            query.addAnd("direction", InventoryTransactionDirection.IN.toString());

            transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

            //Delete ordered in transaction
            deleteTransactionAndOnHand(transaction, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class);
            query.addAnd("masterId", invoiceMaster.getCreditNoteInvoiceNumber());
            query.addAnd("transId", invoiceLine.getInventoryTransRef());

            List<InventoryRegister> registerLines = util.executeGeneralSelectQuery(query, userData);

            double registeredQuantity = 0;

            for (InventoryRegister registerLine : registerLines) {
                registeredQuantity += registerLine.getQuantity();

                InventoryTransactions registerTrans = new InventoryTransactions();
                util.copyFields(transaction, registerTrans, userData);

                registerTrans.setQuantity(registerLine.getQuantity());


                dimId = dimIdLocal.getDimRecordId(registerLine.getBatch(), invoiceLine.getDimension1(), invoiceLine.getDimension2(),
                        invoiceLine.getDimension3(), registerLine.getWarehouse(), registerLine.getLocation(), registerLine.getPallet(),
                        registerLine.getSerial(), userData);

                registerTrans.setItemDimId(dimId);

                trans.insert(registerTrans, userData);

                registerTrans.setStatus(InventoryTransactionStatus.RECEIVED.toString());
                registerTrans.setPhysicalDate(Functions.nowDate());

                trans.update(registerTrans, userData);
                updateOnHand(InventorySummaryUpdateOptions.RECEIVED, registerTrans, userData);


            }

            if (util.compareDouble(registeredQuantity, invoiceLine.getQuantity().doubleValue()) != 0) {
                throw new EMCEntityBeanException("Registered quantity does not match line quantity.");
            }
        }

        return invoiceMaster;
    }

    public CreditorsCreditNoteInvoiceMaster postCreditNote(CreditorsCreditNoteInvoiceMaster creditNoteMaster, List<CreditorsCreditNoteInvoiceLines> creditNoteLinesList, EMCUserData userData) throws EMCStockException, EMCEntityBeanException {
        if (!creditNoteMaster.isStockCreditNoteInvoice()) {
            throw new EMCEntityBeanException("Stock should not be issued against the specified Credit Note.");
        }

        InventoryItemMaster item;

        for (CreditorsCreditNoteInvoiceLines creditNoteLine : creditNoteLinesList) {
            item = itemBean.findItem(creditNoteLine.getItemId(), userData);

            CreditorsCreditNoteInvoiceLines invoiceLine = null;
            if (!isBlank(creditNoteMaster.getCreditNoteRefInvoiceNo())) {
                //Only attempt this if the Credit Note is related to an Invoice.  Ensure that dimensions and item id match, as well as line number.
                invoiceLine = invoiceLinesBean.getInvoiceLine(creditNoteMaster.getCreditNoteInvoiceNumber(), creditNoteLine, userData);

                if (invoiceLine != null) {
                    //At present, lines may be added to an Invoice or changed, so not all lines will
                    //have a corresponding Invoice Line.
                    if (invoiceLine.getQuantityReturned().add(creditNoteLine.getQuantity().abs()).compareTo(invoiceLine.getQuantity()) > 0) {
                        //Returning more stock than what was on Invoice.
                        throw new EMCStockException("Total quantity returned on Invoice Line (" + item == null ? creditNoteLine.getItemId() : item.getItemReference() + ")" +
                                " will exceed Invoice Line quantity.");
                    } else {
                        invoiceLine.setQuantityReturned(invoiceLine.getQuantityReturned().add(creditNoteLine.getQuantity()));

                        invoiceLinesBean.update(invoiceLine, userData);
                    }
                }
            }

            //Get transaction
            long dimId = dimIdLocal.getDimRecordId(creditNoteLine.getBatch(), creditNoteLine.getDimension1(), creditNoteLine.getDimension2(),
                    creditNoteLine.getDimension3(), creditNoteLine.getWarehouse(), creditNoteLine.getLocation(), creditNoteLine.getPallet(),
                    creditNoteLine.getSerial(), userData);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            query.addAnd("transId", creditNoteLine.getInventoryTransRef());
            query.addAnd("refNumber", creditNoteLine.getCreditNoteInvoiceNumber());
            query.addAnd("type", InventoryTransactionTypes.CN.toString());
            query.addAnd("refType", InventoryTransactionsRefType.Credit_Note.toString());
            query.addAnd("itemId", creditNoteLine.getItemId());
            query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
            query.addAnd("itemDimId", dimId);
            query.addAnd("direction", InventoryTransactionDirection.OUT.toString());

            InventoryTransactions transaction = (InventoryTransactions) util.executeSingleResultQuery(query, userData);

            //Delete ordered in transaction
            deleteTransactionAndOnHand(transaction, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class);
            query.addAnd("masterId", creditNoteMaster.getCreditNoteInvoiceNumber());
            query.addAnd("transId", creditNoteLine.getInventoryTransRef());

            List<InventoryRemoveRegister> registerLines = util.executeGeneralSelectQuery(query, userData);

            double registeredQuantity = 0;

            for (InventoryRemoveRegister registerLine : registerLines) {
//                registeredQuantity += registerLine.getQuantity();
//
//                InventoryTransactions registerTrans = new InventoryTransactions();
//                util.copyFields(transaction, registerTrans, userData);
//
//                registerTrans.setQuantity(registerLine.getQuantity());
//
//
//                    dimId = dimIdLocal.getDimRecordId(registerLine.getBatch(), invoiceLine.getDimension1(), invoiceLine.getDimension2(),
//                            invoiceLine.getDimension3(), registerLine.getWarehouse(), registerLine.getLocation(), registerLine.getPallet(),
//                            registerLine.getSerial(), userData);
//
//                registerTrans.setItemDimId(dimId);
//
//                trans.insert(registerTrans, userData);
//
//                registerTrans.setStatus(InventoryTransactionStatus.RECEIVED.toString());
//                registerTrans.setPhysicalDate(Functions.nowDate());
//
//                trans.update(registerTrans, userData);
//                updateOnHand(InventorySummaryUpdateOptions.RECEIVED, registerTrans, userData);
            }

            if (util.compareDouble(registeredQuantity, invoiceLine.getQuantity().doubleValue()) != 0) {
                throw new EMCEntityBeanException("Registered quantity does not match line quantity.");
            }
        }

        return creditNoteMaster;
    }
}
