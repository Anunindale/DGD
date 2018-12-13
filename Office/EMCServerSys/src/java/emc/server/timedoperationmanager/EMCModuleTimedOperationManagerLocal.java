/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.timedoperationmanager;

import emc.framework.EMCTimedOperationManagerLocal;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface EMCModuleTimedOperationManagerLocal extends EMCTimedOperationManagerLocal {

    public void startAllTimers(emc.framework.EMCUserData userData);
}
