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
public interface WFJobLinesLocal extends EMCEntityBeanLocalInterface {
    

    public void redoJobLines(java.lang.String thenumbers, java.lang.String stageGateLineNo, java.lang.String designNo, emc.framework.EMCUserData userData);

}
