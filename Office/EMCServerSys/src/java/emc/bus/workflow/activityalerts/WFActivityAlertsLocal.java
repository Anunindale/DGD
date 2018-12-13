/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow.activityalerts;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface WFActivityAlertsLocal extends EMCEntityBeanLocalInterface {

    public void sendActivityAlert(emc.framework.EMCUserData userData);
}
