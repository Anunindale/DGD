/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.creditors;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface CreditorsInventoryTransactionLogicLocal {

    public emc.entity.inventory.transactions.InventoryTransactions postInvoiceLine(emc.entity.creditors.CreditorsCreditNoteInvoiceLines invoiceLine, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    public emc.entity.inventory.transactions.InventoryTransactions postCreditNoteLine(emc.entity.creditors.CreditorsCreditNoteInvoiceLines creditNoteLine, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    public emc.entity.creditors.CreditorsCreditNoteInvoiceMaster postInvoice(emc.entity.creditors.CreditorsCreditNoteInvoiceMaster invoiceMaster, java.util.List<emc.entity.creditors.CreditorsCreditNoteInvoiceLines> invoiceLinesList, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException, emc.framework.EMCEntityBeanException;
}
