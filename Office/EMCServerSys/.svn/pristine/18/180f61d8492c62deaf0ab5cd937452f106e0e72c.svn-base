/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface WFEvaluateLinesLocal {

    public void evaluateLines(double firstLine, double nextLine, 
            emc.enums.enumWFPrimaryIndicators piIndicator, 
            javax.swing.tree.DefaultMutableTreeNode root, 
            emc.entity.workflow.WorkFlowMaster theMaster, 
            emc.framework.EMCUserData userData);

    public java.lang.String encodeWFTree(javax.swing.tree.DefaultMutableTreeNode root);


    public void evaluateWFLine(emc.entity.workflow.WorkFlowLines theLine, emc.framework.EMCUserData userData);

   

}
