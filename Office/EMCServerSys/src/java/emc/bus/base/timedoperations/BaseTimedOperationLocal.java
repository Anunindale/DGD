/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.timedoperations;

import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author wikus
 */
@Local
public interface BaseTimedOperationLocal extends EMCEntityBeanLocalInterface {

    public void startTimedOperation(String operationId, EMCUserData userData) throws EMCEntityBeanException;

    public void stopTimedOperation(String operationId, EMCUserData userData) throws EMCEntityBeanException;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateInNewTx(BaseTimedOperations record, EMCUserData userData) throws EMCEntityBeanException;
}
