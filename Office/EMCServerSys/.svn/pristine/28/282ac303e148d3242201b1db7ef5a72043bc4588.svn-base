/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.journallines;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface GLJournalLinesLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a List containing the lines on the specified journal.
     * @param journalNumber Journal number.
     * @param userData User data.
     * @return A List containing the lines on the specified journal.
     */
    public java.util.List<emc.entity.gl.journals.GLJournalLines> getJournalLines(java.lang.String journalNumber, emc.framework.EMCUserData userData);
}
