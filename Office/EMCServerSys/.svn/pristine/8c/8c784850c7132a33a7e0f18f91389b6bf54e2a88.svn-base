/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.framework;

import emc.entity.base.timedoperations.BaseTimedOperations;
import javax.ejb.Local;
import javax.ejb.Timeout;
import javax.ejb.Timer;

/**
 *
 * @author rico
 */
@Local
public interface EMCTimedOperationLocal {

    public Timer startTimer(BaseTimedOperations operation, EMCUserData userData);

    public Timer stopTimer(BaseTimedOperations operation, EMCUserData userData);

    @Timeout
    public void handleTimeout(Timer timer);
}
