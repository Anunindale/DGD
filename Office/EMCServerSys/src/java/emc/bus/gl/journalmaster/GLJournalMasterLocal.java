/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.journalmaster;

import emc.bus.base.journals.superclass.JournalMasterSuperLocalInterface;
import emc.entity.gl.journals.GLJournalMaster;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface GLJournalMasterLocal extends JournalMasterSuperLocalInterface {
    /**
     * Fetches and returns the specified journal.
     * @param journalNumber Journal number.
     * @param userData User data.
     * @return The specified journal, or null, if it's not found.
     */
    public GLJournalMaster getJournalMaster(String journalNumber, EMCUserData userData);

    /**
     * Returns totals for each period on the specified journal.
     *
     * @param journalNumber
     * @param userData
     * @return Journal totals.
     */
    public List<Object[]> getJournalTotals(String journalNumber, EMCUserData userData);
}
