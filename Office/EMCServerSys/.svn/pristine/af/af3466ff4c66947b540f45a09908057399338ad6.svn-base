/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.creditnotes;

import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsCreditNoteLinesBean.
 *
 * @date        : 14 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCreditNoteLinesLocal extends EMCEntityBeanLocalInterface {

    /**
     * This method is used to post an Inventory transaction.  It should be called from insert(), update() and delete().
     * It is exposed, so that it can be used to generate transactions when creating a Credit Note from an Invoice.  When
     * this happens, it will not be possible to select the Credit Note master from the insert method.
     * @param line DebtorsCustomerInvoiceLines instance to post transaction for.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean postStockTransaction(DebtorsCreditNoteLines line, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns the Credit Note lines belonging to the specified Credit Note.
     * @param creditNoteNo Credit Note number.
     * @param userData User data.
     * @return A list of DebtorsCreditNoteLines instances.
     */
    public List<DebtorsCreditNoteLines> getCreditNoteLines(String creditNoteNo, EMCUserData userData);

    public boolean cancelCreditNoteLine(DebtorsCreditNoteLines line, EMCUserData userData) throws EMCEntityBeanException;
}
