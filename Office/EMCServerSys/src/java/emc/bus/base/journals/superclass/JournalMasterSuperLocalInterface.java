/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.journals.superclass;

import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;

/**
 * @description : Super interface for journal local interfaces.  This prevents one from having to
 *                declare super class methods individually for all journal beans.
 *
 * @date        : 12 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public interface JournalMasterSuperLocalInterface extends EMCEntityBeanLocalInterface {

    /** This method is used to unapprove a journal. */
    public boolean unApproveJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException;

    /** This method is used to approve a journal. */
    public boolean approveJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Checks that the specified journal can be posted.  If so, it calls doPost(),
     * which is implementation-specific.
     *
     * @param journal Journal to be posted.
     * @param userData User data.
     * @return A boolean indicating whether the journal was successfully posted.
     * @throws EMCEntityBeanException
     */
    public boolean attemptPost(JournalMasterSuperClass journal, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Validates a journal.
     * @param record Journal master to validate.
     * @param userData User data.
     * @return A boolean indicating whether the journal is valid.
     */
    public boolean validateJournal(JournalMasterSuperClass record, EMCUserData userData);
}
