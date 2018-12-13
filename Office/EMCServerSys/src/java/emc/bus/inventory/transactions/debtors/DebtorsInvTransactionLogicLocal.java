/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.transactions.debtors;

import emc.bus.inventory.transactions.EMCStockException;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsTransactionsBean.
 *
 * @date        : 24 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsInvTransactionLogicLocal {

    /**
     * Posts a customer invoice line when it is saved.
     * @param invoiceLine Invoice line to post.
     * @param userData User data.
     * @return The transaction that was posted.
     * @throws EMCEntityBeanException
     * @throws EMCStockException
     */
    public InventoryTransactions postCustomerInvoiceLine(DebtorsCustomerInvoiceLines invoiceLine, EMCUserData userData) throws EMCEntityBeanException, EMCStockException;

    /**
     * This method is used to update stock transactions when posting an invoice.
     * @param invoiceMaster Invoice to post.
     * @param userData User data.
     * @return The invoice that was posted.
     * @throws EMCStockException
     * @throws EMCEntityBeanException
     */
    public DebtorsCustomerInvoiceMaster postCustomerInvoice(emc.entity.debtors.DebtorsCustomerInvoiceMaster invoiceMaster, emc.framework.EMCUserData userData) throws EMCStockException, EMCEntityBeanException;

    /**
     * Posts a Credit Note line when it is saved.
     * @param creditNoteLine Credit Note line to post.
     * @param userData User data.
     * @return The transaction that was posted.
     * @throws EMCEntityBeanException
     * @throws EMCStockException
     */
    public emc.entity.inventory.transactions.InventoryTransactions postCreditNoteLine(emc.entity.debtors.DebtorsCreditNoteLines creditNoteLine, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException, emc.bus.inventory.transactions.EMCStockException;

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
    public DebtorsCreditNoteMaster postCreditNote(DebtorsCreditNoteMaster creditNoteMaster, boolean generateNewSTKNumbers, EMCUserData userData) throws EMCStockException, EMCEntityBeanException;

    public emc.entity.debtors.DebtorsCustomerInvoiceLines cancelCustomerInvoiceLine(emc.entity.debtors.DebtorsCustomerInvoiceLines invoiceLine, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException, emc.bus.inventory.transactions.EMCStockException;

    public emc.entity.debtors.DebtorsCreditNoteLines cancelCreditNoteLine(emc.entity.debtors.DebtorsCreditNoteLines invoiccreditNoteLineeLine, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException, emc.bus.inventory.transactions.EMCStockException;
}
