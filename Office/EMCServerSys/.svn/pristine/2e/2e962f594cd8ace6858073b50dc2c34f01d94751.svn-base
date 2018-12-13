/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface EvaluateJobLinesLocal {

    public void evaluateLines(double firstLine, double nextLine, emc.enums.enumWFPrimaryIndicators piIndicator, javax.swing.tree.DefaultMutableTreeNode root, emc.entity.workflow.WorkFlowJobMaster theMaster, emc.framework.EMCUserData userData);

    public boolean evaluateWFLine(emc.entity.workflow.WorkFlowJobLines theLine, emc.framework.EMCUserData userData);

    public java.lang.String encodeWFTree(javax.swing.tree.DefaultMutableTreeNode root, emc.entity.workflow.WorkFlowJobMaster theMaster, java.util.Date expCompl, EMCUserData userData);

    

}
