/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.transactions.logic.debtors;

import emc.bus.gl.transactions.logic.GLTransactionException;
import emc.bus.gl.transactions.logic.posthelpers.debtors.DebtorsCreditNotePostHelper;
import emc.bus.gl.transactions.logic.posthelpers.debtors.DebtorsCustomerInvoicePostHelper;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsGLTransactionLogicBean extends EMCBusinessBean implements DebtorsGLTransactionLogicLocal {

    /** Creates a new instance of DebtorsGLTransactionLogicBean */
    public DebtorsGLTransactionLogicBean() {
        
    }

    /**
     * Posts GL transactions for customer invoices.
     * @param postHelper Post helper containing invoice information.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws GLTransactionException
     */
    public boolean postCustomerInvoice(DebtorsCustomerInvoicePostHelper postHelper, EMCUserData userData) throws GLTransactionException {
        return true;
    }

    /**
     * Posts GL transactions for credit notes.
     * @param postHelper Post helper containing credit note information.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws GLTransactionException
     */
    public boolean postCreditNote(DebtorsCreditNotePostHelper postHelper, EMCUserData userData) {
        return true;
    }
}
