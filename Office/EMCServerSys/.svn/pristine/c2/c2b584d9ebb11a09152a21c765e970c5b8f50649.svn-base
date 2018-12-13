/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.treccards;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface TRECTrecCardsMasterLocal extends EMCEntityBeanLocalInterface {

    public java.util.List<java.lang.String> exportTRECData(emc.framework.EMCUserData userData);

    public boolean importTRECData(java.util.List<java.lang.String> theData, boolean override, emc.framework.EMCUserData userData);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void deleteCurrentTRECDate(emc.framework.EMCUserData userData);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertMasterRecord(emc.entity.trec.TRECTrecCardsMaster master, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertLinesRecord(emc.entity.trec.TRECTrecCardsLines lines, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
