/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.timedoperationmanager.base;

import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.enums.base.timedoperations.EnumTimedOperations;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCUserData;
import emc.timedoperations.base.BaseAutoEmailSMSTimeLocal;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseTimedOperationsManagerBean extends EMCBusinessBean implements BaseTimedOperationsManagerLocal {

    @EJB
    private BaseAutoEmailSMSTimeLocal autoEmailSMSBean;

    @Override
    public Timer startTimer(BaseTimedOperations operation, EMCUserData userData) {
        switch (EnumTimedOperations.fromString(operation.getOperationEnumId())) {
            case AUTO_EMAIL_SMS:
                return autoEmailSMSBean.startTimer(operation, userData);
            default:
                this.logMessage(Level.SEVERE, "Failed to find an implementation for enum: " + operation.getOperationEnumId() + " during execution of loading of timed operations.", userData);
                break;
        }
        return null;
    }

    @Override
    public Timer stopTimer(BaseTimedOperations operation, EMCUserData userData) {
        switch (EnumTimedOperations.fromString(operation.getOperationEnumId())) {
            case AUTO_EMAIL_SMS:
                return autoEmailSMSBean.stopTimer(operation, userData);
            default:
                this.logMessage(Level.SEVERE, "Failed to find an implementation for enum: " + operation.getOperationEnumId() + " during execution of loading of timed operations.", userData);
                break;
        }
        return null;
    }
}
