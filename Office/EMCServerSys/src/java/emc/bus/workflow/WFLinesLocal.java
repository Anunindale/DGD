/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface WFLinesLocal extends EMCEntityBeanLocalInterface {
    

    public void copyWFLine(emc.entity.workflow.WorkFlowLines theLine, java.lang.String WFId, emc.framework.EMCUserData userData);

}
