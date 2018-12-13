/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.journals;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsJournalLinesBean.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsJournalLinesLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a list containing the journal lines for the specified journal.
     * @param journalNumber Journal number.
     * @param userData User data.
     * @return A list containing the journal lines for the specified journal.
     */
    public java.util.List<emc.entity.debtors.journals.DebtorsJournalLines> getJournalLines(java.lang.String journalNumber, emc.framework.EMCUserData userData);

}
