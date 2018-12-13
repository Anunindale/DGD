/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.numbersequences;

import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Local
public interface BaseNumberSequenceLogicLocal {
    /**
     *
     * @param refTable
     * @param refField
     * @param record
     * @param userData
     * @return - position 0 the number seq, position 1 the recordId of the number seq
     * default/error return value is a arraylist with null in pos 0,1
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getNextAvailableSequenceNumber(String refTable, String refField, Object record, EMCUserData userData);

    public void returnSeqNum(String refTable, String refField, String seqNum, EMCUserData userData);

    public Object generateNextNumber(emc.entity.base.numbersequences.BaseNumberSequence numberSequence, emc.framework.EMCUserData userData);

    public boolean sequenceExists(String refTable, String refField, emc.framework.EMCUserData userData);

    public boolean isManualEntryAllowed(String refTable, String refField, Object record, emc.framework.EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public java.util.List<java.util.List> getNextAvailableSequenceNumber(java.lang.String refTable, java.lang.String refField, java.lang.Object record, int increaseBy, emc.framework.EMCUserData userData);
}
