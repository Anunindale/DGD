/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.batchprocess;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface BaseBatchProcessLocal extends EMCEntityBeanLocalInterface {

    public java.util.List addBatchProcess(emc.framework.EMCCommandClass cmd, java.util.List<java.lang.Object> data, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void runBatchProcess(java.lang.String cmd, emc.framework.EMCUserData userData);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public boolean executeTheBatch(java.lang.String toDo, emc.framework.EMCUserData userData);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public java.lang.Object updateInNewTx(emc.entity.base.batchprocess.BaseBatchProcess record, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void logBatchRecordMessages(long recordID, EMCUserData userData);
}
