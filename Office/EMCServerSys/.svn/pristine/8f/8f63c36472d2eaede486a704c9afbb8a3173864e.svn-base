/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow;

import emc.entity.workflow.WorkFlowJobMaster;
import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author Marius-Gert Coetzee
 */
@Local
public interface WFMasterLocal extends EMCEntityBeanLocalInterface {

    public void copyWF(emc.entity.workflow.WorkFlowMaster theMaster, java.lang.String newWFId, emc.framework.EMCUserData userData);

    public WorkFlowJobMaster createJob(emc.entity.workflow.WorkFlowMaster theMaster, java.lang.String newJobId, emc.framework.EMCUserData userData);

    public String evaluateWFLines(emc.entity.workflow.WorkFlowMaster theMaster, emc.framework.EMCUserData userData);

    public void allDocumentsAttached(emc.entity.workflow.WorkFlowMaster theMaster, emc.framework.EMCUserData userData);
}
