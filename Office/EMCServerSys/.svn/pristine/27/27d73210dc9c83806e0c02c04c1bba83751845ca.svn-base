/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.autoemailsms;

import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface BaseAutoEmailSMSLocal {

    public boolean fireAutoSend(emc.framework.EMCUserData userData);

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void updateActivity(emc.entity.workflow.WorkFlowActivity act, emc.framework.EMCQuery templateQuery, emc.framework.EMCQuery recipientQuery, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
