/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.framework;

import emc.bus.base.timedoperations.BaseTimedOperationLocal;
import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.enums.base.timedoperations.EnumTimedOperationParameters;
import emc.enums.enumQueryTypes;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Timer;

/**
 *
 * @author rico
 */
public class EMCTimedOperation extends EMCBusinessBean {

    @Resource
    protected SessionContext ctx;
    @EJB
    private BaseTimedOperationLocal timedOperationBean;

    /**
     * Does a calculation to determine the start of the operation.
     * No need to overwrite normally.
     * @param operation
     * @return
     */
    protected Date startDateTime(BaseTimedOperations operation) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(operation.getStartExecutionDate());

        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(operation.getStartExecutionTime());

        Calendar retCal = Calendar.getInstance();
        retCal.set(dateCal.get(Calendar.YEAR), dateCal.get(Calendar.MONTH), dateCal.get(Calendar.DATE),
                timeCal.get(Calendar.HOUR_OF_DAY), timeCal.get(Calendar.MINUTE), timeCal.get(Calendar.SECOND));

        return retCal.getTime();
    }

    /**
     * Does a calculation of the duration of the timer.
     * No need to overwrite normally.
     * @param operation
     * @return
     */
    protected long elapseDuration(BaseTimedOperations operation) {
        long idleTime = 0;
        idleTime += operation.getIdleDurationHour() * 60 * 60 * 1000;
        idleTime += operation.getIdleDurationMin() * 60 * 1000;
        idleTime += operation.getIdleDurationSec() * 1000;
        return idleTime;
    }

    protected HashMap<EnumTimedOperationParameters, Object> populateParameterMap(BaseTimedOperations operation) {
        HashMap<EnumTimedOperationParameters, Object> param = new HashMap<EnumTimedOperationParameters, Object>();
        param.put(EnumTimedOperationParameters.COMPANY_ID, operation.getCompanyId());
        param.put(EnumTimedOperationParameters.OPERATION_ID, operation.getOperationEnumId());
        return param;
    }

    private EMCUserData getUserData(String companyId) {
        //Create Temp User Data
        EMCUserData userData = new EMCUserData();
        userData.setCompanyId(companyId);
        userData.setUserName("emc");
        userData.setUserData(new ArrayList());
        return userData;
    }

    private void setLastExecutedStartDateTime(String operationId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, BaseTimedOperations.class);
        query.addAnd("operationEnumId", operationId);
        query.addSet("lastExecutedDate", Functions.nowDateString("yyyy-MM-dd"));
        query.addSet("lastExecutedTime", Functions.nowDateString("HH:mm"));
        util.executeUpdateInNewTransaction(query, userData);
    }

    private void setLastExecutedEndDateTime(String operationId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, BaseTimedOperations.class);
        query.addAnd("operationEnumId", operationId);
        query.addSet("lastExecutedEndDate", Functions.nowDateString("yyyy-MM-dd"));
        query.addSet("lastExecutedEndTime", Functions.nowDateString("HH:mm"));
        util.executeUpdateInNewTransaction(query, userData);
    }

    private boolean validateFiring(String operationId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseTimedOperations.class);
        query.addAnd("operationEnumId", operationId);
        BaseTimedOperations operation = (BaseTimedOperations) util.executeSingleResultQuery(query, userData);
        if (operation != null) {
            Calendar dateCal = Calendar.getInstance();
            Calendar timeCal = Calendar.getInstance();
            Calendar timeStampCal = Calendar.getInstance();
            if (!isBlank(operation.getStartExecutionDate()) && !isBlank(operation.getStartExecutionTime())) {
                //Check if operation should be started.
                dateCal.setTime(operation.getStartExecutionDate());
                timeCal.setTime(operation.getStartExecutionTime());
                timeStampCal.set(dateCal.get(Calendar.YEAR), dateCal.get(Calendar.MONTH), dateCal.get(Calendar.DATE),
                        timeCal.get(Calendar.HOUR_OF_DAY), timeCal.get(Calendar.MINUTE), timeCal.get(Calendar.SECOND));
                timeStampCal.set(Calendar.MILLISECOND, timeCal.get(Calendar.MILLISECOND));
                if (timeStampCal.getTime().after(Functions.nowDate())) {
                    return false;
                }
            }

            if (isBlank(operation.getLastExecutedDate()) || isBlank(operation.getLastExecutedTime())) {
                //Operation has not fired before
                return true;
            }
            dateCal.setTime(operation.getLastExecutedDate());

            timeCal.setTime(operation.getLastExecutedTime());

            timeStampCal.set(dateCal.get(Calendar.YEAR), dateCal.get(Calendar.MONTH), dateCal.get(Calendar.DATE),
                    timeCal.get(Calendar.HOUR_OF_DAY), timeCal.get(Calendar.MINUTE), timeCal.get(Calendar.SECOND));
            timeStampCal.set(Calendar.MILLISECOND, timeCal.get(Calendar.MILLISECOND));

            long timestamp = timeStampCal.getTime().getTime();

//            System.out.println(operationId);
//            System.out.println("Operation: " + (timestamp + elapseDuration(operation)));
//            System.out.println("System:    " + Functions.nowDate().getTime());

            if (operation.getFireOnStartup() || (timestamp + elapseDuration(operation)) < Functions.nowDate().getTime()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Always call this method from within handleTimeout
     *
     * Constructs User Data from Timer
     * Set Last Executed Date On Operation
     * Validate Timed Operation Fireing
     * Calls executeTimedOperatiopn if validation passed
     *
     * @param timer
     */
    public void prepareForOperation(Timer timer) {
        HashMap<EnumTimedOperationParameters, Object> param = (HashMap<EnumTimedOperationParameters, Object>) timer.getInfo();
        //Constructs User Data from Timer
        EMCUserData userData = getUserData((String) param.get(EnumTimedOperationParameters.COMPANY_ID));
        //Validate Timed Operation Fireing
        if (validateFiring((String) param.get(EnumTimedOperationParameters.OPERATION_ID), userData)) {
            //Set Last Executed Date On Operation
            setLastExecutedStartDateTime((String) param.get(EnumTimedOperationParameters.OPERATION_ID), userData);
            //Execute Operation
            executeTimedOperation(userData);
            //Set Last Executed Date On Operation
            setLastExecutedEndDateTime((String) param.get(EnumTimedOperationParameters.OPERATION_ID), userData);
        }
    }

    /**
     * Execute the Operation that the Timer is set up for.
     * This is called from within prepareForOperation
     *
     * @param userData
     */
    public void executeTimedOperation(EMCUserData userData) {
    }
}
