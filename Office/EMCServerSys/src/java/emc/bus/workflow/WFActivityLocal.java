/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow;

import emc.entity.workflow.WorkFlowActivity;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author rico
 */
@Local
public interface WFActivityLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.workflow.WorkFlowActivity copyJobLineToActivity(java.lang.String ActivityNo, emc.entity.workflow.WorkFlowJobLines theLine, emc.framework.EMCUserData userData);

    public void copyJobLineToActivityPersist(java.lang.String ActivityNo, emc.entity.workflow.WorkFlowJobLines theLine, emc.framework.EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertInNewTx(WorkFlowActivity activity, EMCUserData userData) throws EMCEntityBeanException;

    public boolean createNewActivity(WorkFlowActivity sourceActivity, EMCUserData userData) throws EMCEntityBeanException;

    public boolean launchWorkFlow(String workFlow, String jobDescription, String sourceTable, long sourceRecordID, String manager, EMCUserData userData) throws EMCEntityBeanException;

    public boolean reassignActivity(long actRecId, String empId, EMCUserData userData) throws EMCEntityBeanException;
}
