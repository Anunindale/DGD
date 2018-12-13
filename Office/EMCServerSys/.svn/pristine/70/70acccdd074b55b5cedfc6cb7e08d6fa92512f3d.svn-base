/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author Marius-Gert Coetzee
 */
@Local
public interface WFJobMasterLocal extends EMCEntityBeanLocalInterface {
    

    public java.lang.String evaluateWFJobLines(emc.entity.workflow.WorkFlowJobMaster theMaster, emc.framework.EMCUserData userData);

    public void allDocumentsAttached(emc.entity.workflow.WorkFlowJobMaster theMaster, emc.framework.EMCUserData userData);

}
