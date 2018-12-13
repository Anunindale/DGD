/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.timedoperations.workflow;

import emc.bus.base.onlineusers.OnlineUsersLocal;
import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.framework.EMCCommandClass;
import emc.framework.EMCTimedOperation;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.workflow.ServerWorkFlowMethods;
import emc.server.commandmanager.EMCCommandManagerLocal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author wikus
 */
@Stateless
public class WFActivityAlertBean extends EMCTimedOperation implements WFActivityAlertLocal {

    @EJB
    private EMCCommandManagerLocal managerBean;
    @EJB
    private OnlineUsersLocal onlineUsersBean;
    private TimerService timerService;

    @Override
    public Timer startTimer(BaseTimedOperations operation, EMCUserData userData) {
        timerService = ctx.getTimerService();
        Timer timer = (Timer) timerService.createTimer(startDateTime(operation), elapseDuration(operation), populateParameterMap(operation));
        return timer;
    }

    @Override
    public Timer stopTimer(BaseTimedOperations operation, EMCUserData userData) {
        timerService = ctx.getTimerService();
        Iterator<Timer> it = timerService.getTimers().iterator();
        Timer timer = null;
        while (it.hasNext()) {
            timer = it.next();
            timer.cancel();
        }
        return timer;
    }

    @Timeout
    @Override
    public void handleTimeout(Timer timer) {
        prepareForOperation(timer);
    }

    @Override
    public void executeTimedOperation(EMCUserData userData) {
        onlineUsersBean.addMessageOnlySession(userData);

        EMCXMLHandler xml = new EMCXMLHandler();

        EMCCommandClass cmd = new EMCCommandClass(ServerWorkFlowMethods.SEND_ACTIVITY_REMINDER);

        List toSend = new ArrayList();

        managerBean.executeCommand(xml.toXML(cmd, toSend, userData), userData);
        
        onlineUsersBean.removeMessageOnlyUser(userData);
    }
}
