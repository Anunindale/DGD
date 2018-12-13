/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.creditnotes;

import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCEntityBeanProtectedInterface;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsInvCNTotalsHelper;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsCreditNoteMasterBean.
 *
 * @date        : 14 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCreditNoteMasterLocal extends EMCEntityBeanLocalInterface, EMCEntityBeanProtectedInterface {

    /**
     * Creates a Credit Note from the specified Invoice.
     * @param invoiceNo Invoice number of Invoice to use when creating a Credit Note.
     * @param useStock Indicates whether the new Credit Note should.
     * @param userData User data.
     * @return The Credit Note master record that was created.
     * @throws EMCEntityBeanException
     */
    public DebtorsCreditNoteMaster createCreditNote(String invoiceNo, boolean useStock, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns the specified Credit Note Master.
     * @param creditNoteNo Credit Note No to search for.
     * @param userData User data.
     * @return The specified Credit Note Master, or null, if nothing is found.
     */
    public emc.entity.debtors.DebtorsCreditNoteMaster getCreditNote(java.lang.String creditNoteNo, emc.framework.EMCUserData userData);

    /**
     * Approves the specified Credit Note.
     * @param creditNoteId Id of Credit Note to approve.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean approveCreditNote(java.lang.String creditNoteId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Returns the total of the lines on the specified Credit Note.
     * @param creditNoteNo Credit Note number to sum on.
     * @param userData User data.
     * @return The total of the lines on the specified Credit Note.
     */
    public java.math.BigDecimal getCreditNoteTotal(java.lang.String creditNoteNo, emc.framework.EMCUserData userData);

    /**
     * Posts the specified Credit Note.
     * @param creditNoteNo Credit Note number of Credit Note to post.
     * @param generateNewSTKNumbers Indicates whether new batch numbers should be generated
     * if the credit note is returning goods into stock.
     * @param userData User data.
     * @return A boolean indicating whether the CreditNote was posted succesfully.
     * @throws EMCEntityBeanException
     */
    public boolean postCreditNote(String CreditNote, boolean generateNewSTKNumbers, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns the total of the lines on the specified Credit Note.
     * @param creditNoteNo Credit Note number to sum on.
     * @param userData User data.
     * @return The total of the lines on the specified Credit Note.
     */
    public BigDecimal getCreditNoteTotalVAT(String creditNoteNo, EMCUserData userData);

    /**
     * This method posts all approved Credit Notes.
     * @param userData User data.
     * @return A list containing the numbers and Return Options of all posted Credit Notes.
     */
    public List<String[]> postAllApproved(EMCUserData userData);

    /**
     * Returns totals for the specified Credit Note.
     * @param creditNoteNumber Credit Note to select on.
     * @param userData User data.
     * @return Totals for the specified Credit Note.
     */
    public DebtorsInvCNTotalsHelper getCreditNoteTotalsHelper(String creditNoteNumber, EMCUserData userData);

    /**
     * Gets total discount on an Credit Note.
     * @param creditNoteNo Credit Note number.
     * @param userData User data.
     * @return Total discount on the specified Credit Note.
     */
    public BigDecimal getDiscountTotal(String creditNoteNo, EMCUserData userData);

    public boolean cancelCreditNote(String invCNNumber, EMCUserData userData) throws EMCEntityBeanException;
}
