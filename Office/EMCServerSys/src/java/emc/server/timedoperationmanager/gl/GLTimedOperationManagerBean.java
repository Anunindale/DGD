/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.timedoperationmanager.gl;

import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.enums.base.timedoperations.EnumTimedOperations;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCUserData;
import emc.messages.ServerBaseMessageEnum;
import emc.timedoperations.gl.GLConsolidationTimedOperationLocal;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;

/**
 *
 * @author riaan
 */
@Stateless
public class GLTimedOperationManagerBean extends EMCBusinessBean implements GLTimedOperationManagerLocal {

    @EJB
    private GLConsolidationTimedOperationLocal consolidationOperation;

    /** Creates a new instance of GLTimedOperationManagerBean. */
    public GLTimedOperationManagerBean() {
        
    }

    @Override
    public Timer startTimer(BaseTimedOperations operation, EMCUserData userData) {
        switch (EnumTimedOperations.fromString(operation.getOperationEnumId())) {
            case GL_CONSOLIDATION:
                return consolidationOperation.startTimer(operation, userData);
            default:
                this.logMessage(Level.SEVERE, ServerBaseMessageEnum.TIMED_OPERATION_NOT_FOUND, userData);
                break;
        }
        return null;
    }

    @Override
    public Timer stopTimer(BaseTimedOperations operation, EMCUserData userData) {
        switch (EnumTimedOperations.fromString(operation.getOperationEnumId())) {
            case GL_CONSOLIDATION:
                return consolidationOperation.stopTimer(operation, userData);
            default:
                this.logMessage(Level.SEVERE, ServerBaseMessageEnum.TIMED_OPERATION_NOT_FOUND, userData);
                break;
        }
        return null;
    }
}
