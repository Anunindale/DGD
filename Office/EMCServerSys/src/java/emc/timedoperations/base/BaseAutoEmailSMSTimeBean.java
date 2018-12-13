/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.timedoperations.base;

import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.framework.EMCCommandClass;
import emc.framework.EMCTimedOperation;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.base.ServerBaseMethods;
import emc.server.commandmanager.EMCCommandManagerLocal;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author rico
 */
@Stateless
public class BaseAutoEmailSMSTimeBean extends EMCTimedOperation implements BaseAutoEmailSMSTimeLocal {
    @EJB
    private EMCCommandManagerLocal commandManager;
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
        super.prepareForOperation(timer);
    }

    @Override
    public void executeTimedOperation(EMCUserData userData) {
        try {
            //Call method through command manager so that transaction log can be updated.
            EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.AUTO_EMAIL_SMS);
            commandManager.executeCommand(new EMCXMLHandler().toXML(cmd, new ArrayList(), userData), userData);
        } catch (Exception ex) {
            System.out.println("Failed to auto email/sms.");
            ex.printStackTrace();
        }
    }

}
