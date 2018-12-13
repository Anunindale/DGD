/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.framework;

import emc.bus.base.parameters.BaseParametersLocal;
import emc.entity.base.BaseParameters;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class EMCMultiProcessingBean extends EMCBusinessBean implements EMCMultiProcessingLocal {

    private static Integer maxThreads = null;
    private static Integer reservePerClient = null;
    private static int reservedThreads = 0;
    
    @EJB
    private BaseParametersLocal baseParamBean;

    @Override
    public int reserveThreads(int threadCount, EMCUserData userData) {
        if (maxThreads == null || reservePerClient == null) {
            setThreadVariables(userData);
        }
        if (reservedThreads < maxThreads) {
            if (threadCount == 0 || threadCount > reservePerClient) {
                threadCount = reservePerClient;
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.WARNING, "Only " + reservePerClient + " threads are allowed per client.", userData);
                }
            }
            if (reservedThreads + threadCount > maxThreads) {
                threadCount = maxThreads - reservedThreads;
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.WARNING, "Only " + threadCount + " are available for reservation.", userData);
                }
            }
            reservedThreads += threadCount;
            return threadCount;
        } else {
            return 0;
        }
    }

    @Override
    public void unreserveThreads(int threadCount, EMCUserData userData) {
        reservedThreads -= threadCount;
    }

    @Override
    public void setThreadVariables(EMCUserData userData) {
        BaseParameters baseParam = baseParamBean.getBaseParameters(userData);
        if (baseParam == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Base Parameters are not set up.", userData);
            maxThreads = 0;
            reservePerClient = 0;
            return;
        }
        maxThreads = baseParam.getMaxMultiProcessingThread();
        reservePerClient = baseParam.getReservePerClient();
        reservedThreads = 0;
    }
}
