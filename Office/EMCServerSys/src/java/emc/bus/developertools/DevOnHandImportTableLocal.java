/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DevOnHandImportTableLocal extends EMCEntityBeanLocalInterface {

    public void finishImport(java.lang.String journalNumber, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public emc.entity.inventory.journals.InventoryJournalLines insertJournalLine(emc.entity.inventory.journals.InventoryJournalLines theRecord, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public emc.entity.inventory.register.InventoryRegister insertRegister(emc.entity.inventory.register.InventoryRegister theRegister, emc.entity.developertools.DevOnHandImportTable serialBatchRec, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void finishImportSize(java.lang.String journalNumber, emc.framework.EMCUserData userData);

    public void finishImportElastic(java.lang.String journalNumber, emc.framework.EMCUserData userData);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public emc.entity.inventory.journals.InventoryJournalMaster insertJournalMaster(emc.entity.inventory.journals.InventoryJournalMaster theRecord, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
