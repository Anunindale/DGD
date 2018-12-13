/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.timedoperationmanager.sop;

import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.enums.base.timedoperations.EnumTimedOperations;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCUserData;
import emc.messages.ServerBaseMessageEnum;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.ejb.Timer;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPTimedOperationManagerBean extends EMCBusinessBean implements SOPTimedOperationManagerLocal {

    @Override
    public Timer startTimer(BaseTimedOperations operation, EMCUserData userData) {
        switch (EnumTimedOperations.fromString(operation.getOperationEnumId())) {
            default:
                this.logMessage(Level.SEVERE, ServerBaseMessageEnum.TIMED_OPERATION_NOT_FOUND, userData);
                break;
        }
        return null;
    }

    @Override
    public Timer stopTimer(BaseTimedOperations operation, EMCUserData userData) {
        switch (EnumTimedOperations.fromString(operation.getOperationEnumId())) {
            default:
                this.logMessage(Level.SEVERE, ServerBaseMessageEnum.TIMED_OPERATION_NOT_FOUND, userData);
                break;
        }
        return null;
    }
}
