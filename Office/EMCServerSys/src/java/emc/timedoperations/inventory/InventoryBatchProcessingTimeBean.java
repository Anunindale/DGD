/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.timedoperations.inventory;

import emc.bus.base.batchprocess.BaseBatchProcessLocal;
import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.framework.EMCTimedOperation;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryBatchProcessingTimeBean extends EMCTimedOperation implements InventoryBatchProcessingTimeLocal {

    @EJB
    private BaseBatchProcessLocal processingBean;
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

    @Override
    public void handleTimeout(Timer timer) {
        prepareForOperation(timer);
    }

    @Override
    public void executeTimedOperation(EMCUserData userData) {
        processingBean.runBatchProcess(ServerInventoryMethods.UPDATE_EXPLODED_REQUIREMENTS.toString(), userData);
    }
}
