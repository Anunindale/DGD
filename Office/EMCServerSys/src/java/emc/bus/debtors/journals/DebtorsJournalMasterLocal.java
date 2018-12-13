/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.journals;

import emc.bus.base.journals.superclass.JournalMasterSuperLocalInterface;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsJournalMasterBean.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsJournalMasterLocal extends JournalMasterSuperLocalInterface {

    /** 
     * Returns the journal with the specified journal number.
     * 
     * @param journalNumber Journal number to look for.
     * @param userData  User data.
     * 
     * @return A DebtorsJournalMaster instance, or null, if none is found.
     */
    public DebtorsJournalMaster getDebtorsJournalMaster(String journalNumber, EMCUserData userData);
}
