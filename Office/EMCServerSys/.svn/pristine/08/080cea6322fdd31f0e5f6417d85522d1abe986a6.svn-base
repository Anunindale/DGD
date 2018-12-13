/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.journals;

import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.helpers.inventory.JournalGeneratorHelper;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryJournalMasterLocal extends EMCEntityBeanLocalInterface {

    public boolean approveJournal(java.lang.String journalNumber, emc.framework.EMCUserData userData) throws EMCEntityBeanException;

    public boolean validateJournal(InventoryJournalMaster record, EMCUserData userData);

    public boolean postJournal(InventoryJournalMaster record, EMCUserData userData) throws EMCEntityBeanException;

    public boolean unApproveJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException;

    public boolean generateTransferJournal(JournalGeneratorHelper helper, EMCUserData userData) throws EMCEntityBeanException;

    public boolean postJournal(java.lang.String journalNumber, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
