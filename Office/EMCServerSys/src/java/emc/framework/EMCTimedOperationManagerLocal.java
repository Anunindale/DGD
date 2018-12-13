/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.framework;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface EMCTimedOperationManagerLocal {

    public javax.ejb.Timer startTimer(emc.entity.base.timedoperations.BaseTimedOperations operation, emc.framework.EMCUserData userData);

    public javax.ejb.Timer stopTimer(emc.entity.base.timedoperations.BaseTimedOperations operation, emc.framework.EMCUserData userData);
}
