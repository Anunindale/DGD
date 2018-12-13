/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 */
package emc.bus.inventory.transactions;

import emc.bus.inventory.transactions.creditors.CreditorsInventoryTransactionLogicLocal;
import emc.bus.inventory.transactions.debtors.DebtorsInvTransactionLogicLocal;
import emc.bus.inventory.transactions.inventory.InventoryTransactionLogicLocal;
import emc.bus.inventory.transactions.pop.POPTransactionsLogicBeanLocal;
import emc.bus.inventory.transactions.sop.SOPTransactionLogicLocal;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.entity.inventory.picking.rereservepicklist.InventoryReReservePickList;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderPostLines;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class ProcessStockTransaction extends TransactionsSuperClass implements ProcessStockTransactionLocal {

    @EJB
    private POPTransactionsLogicBeanLocal popLocalTrans;
    @EJB
    private InventoryTransactionLogicLocal inventoryLocalTrans;
    @EJB
    private SOPTransactionLogicLocal sopLocalTrans;
    @EJB
    private DebtorsInvTransactionLogicLocal debtorsLocalTrans;
    @EJB
    private CreditorsInventoryTransactionLogicLocal creditorsLocalTrans;

    public ProcessStockTransaction() {
    }

    /**
     * This method is used to generate an InventoryTransaction record and will
     * also update on hand it may encapsulate other record updates as well
     *
     * @param recordToGenerateFor - This is the record that will be used to
     * generate the stock transaction
     * @param txH - should the recordToGenFor need more info
     * @param userData
     * @return EMCTable - normally the stock transaction that was generated,
     * null if not supported or fails
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCTable post(EMCTable recordToGenerateFor, TransactionHelper txH, EMCUserData userData) throws EMCStockException {
        if (recordToGenerateFor == null || txH == null) {
            throw new EMCStockException("Post method requires non null parameters");
        }
        try {
            switch (txH.getType()) {
                case POP_POST_POLINE:
                    if (recordToGenerateFor instanceof emc.entity.pop.POPPurchaseOrderLines) {
                        return popLocalTrans.postPOLine((POPPurchaseOrderLines) recordToGenerateFor, txH.isPOPsetQtyToZero(), userData);
                    }
                    break;
                case POP_POSTMASTER:
                    if (recordToGenerateFor instanceof emc.entity.pop.posting.POPPurchasePostMaster) {
                        return popLocalTrans.postPOPostMaster((POPPurchasePostMaster) recordToGenerateFor, userData);
                    }
                    break;
                case POP_POSTMASTER_RETURN:
                    if (recordToGenerateFor instanceof emc.entity.pop.posting.POPPurchasePostMaster) {
                        return popLocalTrans.postReturnToSupplier((POPPurchasePostMaster) recordToGenerateFor, txH.isPOPReturnStockLaterFlag(), userData);
                    }
                    break;
                case POP_UPDATE_BLANCKET_ORDER:
                    if (recordToGenerateFor instanceof POPPurchaseOrderLines) {
                        return popLocalTrans.updateBlanketOrderOutStanding((POPPurchaseOrderLines) recordToGenerateFor, txH.getPOPBlancketOrderQty(), userData);
                    }
                    break;
                case IVENT_POST_JRLINE: //Inventory Journal Line
                    if (recordToGenerateFor instanceof emc.entity.inventory.journals.InventoryJournalLines) {
                        return inventoryLocalTrans.postJournalLine((InventoryJournalLines) recordToGenerateFor, txH, userData);
                    }
                    break;
//                case IVENT_UNRESERVE_JRLINE:
//                    if (recordToGenerateFor instanceof emc.entity.inventory.journals.InventoryJournalLines) {
//                        return inventoryLocalTrans.unreserveMovementJournalLine((InventoryJournalLines) recordToGenerateFor, (InventoryRemoveRegister) txH.getRelatedEntity(), false, userData);
//                    }
//                    break;
//                case IVENT_UNRESERVE_AND_REDORDER_JRLINE:
//                    if (recordToGenerateFor instanceof emc.entity.inventory.journals.InventoryJournalLines) {
//                        return inventoryLocalTrans.unreserveMovementJournalLine((InventoryJournalLines) recordToGenerateFor, (InventoryRemoveRegister) txH.getRelatedEntity(), true, userData);
//                    }
//                    break;
                case IVENT_POST_JRMASTER: //Inventory Journal Master
                    if (recordToGenerateFor instanceof emc.entity.inventory.journals.InventoryJournalMaster) {
                        return inventoryLocalTrans.postInventJournal((InventoryJournalMaster) recordToGenerateFor, txH.isPostJournal(), userData);
                    }
                    break;
                case IVENT_MOVEMENT_LOCATION://Inventory Move Stock
                    if (recordToGenerateFor instanceof emc.entity.inventory.movestock.InventoryMoveStockMaster) {
                        return inventoryLocalTrans.postMoveStockLine((InventoryMoveStockMaster) recordToGenerateFor, userData);
                    }
                    break;
                case IVENT_RESERVED_MOVEMENT_LOCATION://Inventory Move Stock
                    if (recordToGenerateFor instanceof emc.entity.inventory.movestock.InventoryMoveStockMaster) {
                        return inventoryLocalTrans.postMoveReservedStockLine((InventoryMoveStockMaster) recordToGenerateFor, userData);
                    }
                    break;
                case IVENT_POST_PICK_LIST:
                    if (recordToGenerateFor instanceof InventoryPickingListLines) {
                        return inventoryLocalTrans.postPickingList((InventoryPickingListLines) recordToGenerateFor, txH, userData);
                    }
                    break;
                case IVENT_RE_RESERVE_PICK_LIST:
                    if (recordToGenerateFor instanceof InventoryReReservePickList) {
                        return inventoryLocalTrans.postReReservePickList((InventoryReReservePickList) recordToGenerateFor, txH, userData);
                    }
                    break;
                case IVENT_CANCEL_PICK_LIST_LINE:
                    if (recordToGenerateFor instanceof InventoryPickingListLines) {
                        return inventoryLocalTrans.cancelPickingListLine((InventoryPickingListMaster) txH.getRelatedEntity(), (InventoryPickingListLines) recordToGenerateFor, userData);
                    }
                    break;
                //Sales Orders
                case SOP_ORDER_SOLINE:
                    if (recordToGenerateFor instanceof emc.entity.sop.SOPSalesOrderLines) {
                        return sopLocalTrans.orderSOLineStock((SOPSalesOrderLines) recordToGenerateFor, userData);
                    }
                    break;
                case SOP_RESERVE_SOLINE:
                    if (recordToGenerateFor instanceof emc.entity.sop.SOPSalesOrderPostLines) {
                        return sopLocalTrans.reserveSOLineStock((SOPSalesOrderPostLines) recordToGenerateFor, txH.getSalesOrderNo(), txH.getSalesORderType(), txH.isPicklist(), userData);
                    }
                    break;
                case SOP_ORDER_BLANKET_ORDER_LINE:
                    if (recordToGenerateFor instanceof emc.entity.sop.SOPSalesOrderLines) {
                        return sopLocalTrans.orderBOLine((SOPSalesOrderLines) recordToGenerateFor, txH.getSalesBOQuantity(), userData);
                    }
                    break;
                case SOP_REALLOCATE_LINE:
                    if (recordToGenerateFor instanceof emc.entity.inventory.transactions.InventoryTransactions) {
                        return sopLocalTrans.reallocateSOLineStock((InventoryTransactions) recordToGenerateFor, (SOPSalesOrderLines) txH.getRelatedEntity(), txH.getQuantity(), txH.getCustomer(), txH.isUnreserve(), txH.getSalesORderType(), userData);
                    }
                    break;
                //Debtors
                case DEBTORS_POST_INVOICE_LINE:
                    if (recordToGenerateFor instanceof DebtorsCustomerInvoiceLines) {
                        return debtorsLocalTrans.postCustomerInvoiceLine((DebtorsCustomerInvoiceLines) recordToGenerateFor, userData);
                    }
                    break;
                case DEBTORS_POST_INVOICE:
                    if (recordToGenerateFor instanceof DebtorsCustomerInvoiceMaster) {
                        return debtorsLocalTrans.postCustomerInvoice((DebtorsCustomerInvoiceMaster) recordToGenerateFor, userData);
                    }
                    break;
                case DEBTORS_CANCEL_INVOICE_LINE:
                    if (recordToGenerateFor instanceof DebtorsCustomerInvoiceLines) {
                        return debtorsLocalTrans.cancelCustomerInvoiceLine((DebtorsCustomerInvoiceLines) recordToGenerateFor, userData);
                    }
                    break;
                case DEBTORS_POST_CREDIT_NOTE_LINE:
                    if (recordToGenerateFor instanceof DebtorsCreditNoteLines) {
                        return debtorsLocalTrans.postCreditNoteLine((DebtorsCreditNoteLines) recordToGenerateFor, userData);
                    }
                    break;
                case DEBTORS_POST_CREDIT_NOTE:
                    if (recordToGenerateFor instanceof DebtorsCreditNoteMaster) {
                        return debtorsLocalTrans.postCreditNote((DebtorsCreditNoteMaster) recordToGenerateFor, txH.isGenerateNewSTKNumbers(), userData);
                    }
                    break;
                case DEBTORS_CANCEL_CREDIT_NOTE_LINE:
                    if (recordToGenerateFor instanceof DebtorsCreditNoteLines) {
                        return debtorsLocalTrans.cancelCreditNoteLine((DebtorsCreditNoteLines) recordToGenerateFor, userData);
                    }
                    break;
                //Creditors
                case CREDITORS_POST_INVOICE_LINE:
                    if (recordToGenerateFor instanceof CreditorsCreditNoteInvoiceLines) {
                        return creditorsLocalTrans.postInvoiceLine((CreditorsCreditNoteInvoiceLines) recordToGenerateFor, userData);
                    }
                    break;
                case CREDITORS_POST_CREDIT_NOTE_LINE:
                    if (recordToGenerateFor instanceof CreditorsCreditNoteInvoiceLines) {
                        return creditorsLocalTrans.postCreditNoteLine((CreditorsCreditNoteInvoiceLines) recordToGenerateFor, userData);
                    }
                    break;
                case CREDITORS_POST_INVOICE:
                    if (recordToGenerateFor instanceof CreditorsCreditNoteInvoiceMaster) {
                        return creditorsLocalTrans.postInvoice((CreditorsCreditNoteInvoiceMaster) recordToGenerateFor, txH.getCreditorsLinesList(), userData);
                    }
                    break;
                case CREDITORS_POST_CREDIT_NOTE:
//                    if (recordToGenerateFor instanceof CreditorsCreditNoteInvoiceMaster) {
//                        return creditorsLocalTrans.postCreditNote(CreditorsCreditNoteInvoiceMaster) recordToGenerateFor, txH.getCreditorsLinesList(), userData);
//                    }
//                    break;
                default:
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EMCStockException(ex.getMessage());
        }
        Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a matching posting routine for inventory transaction", userData);
        return null;
    }

    /**
     * This method is used to validate the post process
     *
     * @param recordToGenerateFor - This is the record that will be used to
     * generate the stock transaction
     * @param relatedRecord - should the recordToGenFor need a related record
     * for instance header of post line
     * @param userData
     * @return EMCTable - normally the stock transaction that was generated,
     * null if not supported or fails
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable validatePost(EMCTable recordToGenerateFor, EMCTable relatedRecord, EMCUserData userData) throws EMCStockException {
        try {
            //PO Lines 
            if (recordToGenerateFor instanceof emc.entity.pop.POPPurchaseOrderLines) {
            }
            //PO Post Master
            if (recordToGenerateFor instanceof emc.entity.pop.posting.POPPurchasePostMaster) {
            }
            //Inventory Journal Master
            if (recordToGenerateFor instanceof emc.entity.inventory.journals.InventoryJournalMaster) {
                inventoryLocalTrans.postInventJournal((InventoryJournalMaster) recordToGenerateFor, false, userData);
                throw new EMCStockException("VALIDATION_PASSED");
            }
            if (recordToGenerateFor instanceof emc.entity.inventory.journals.InventoryJournalLines) {
            }
        } catch (Exception ex) {
            throw new EMCStockException(ex.getMessage());
        }
        Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a matching posting routine for inventory transaction", userData);
        return null;
    }
}
