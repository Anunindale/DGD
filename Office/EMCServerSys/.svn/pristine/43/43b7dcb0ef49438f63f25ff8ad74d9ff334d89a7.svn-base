/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.timedoperations;

import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.enums.base.timedoperations.EnumTimedOperationStatus;
import emc.enums.base.timedoperations.EnumTimedOperations;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.server.timedoperationmanager.EMCModuleTimedOperationManagerLocal;
import emc.tables.EMCTable;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseTimedOperationBean extends EMCEntityBean implements BaseTimedOperationLocal {

    @EJB
    private EMCModuleTimedOperationManagerLocal timedBeanManager;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (valid) {
            BaseTimedOperations record = (BaseTimedOperations) theRecord;
            if (fieldNameToValidate.equals("operationEnumId")) {
                record.setTheModule(EnumTimedOperations.fromString(record.getOperationEnumId()).getModule());
                return record;
            } else if (fieldNameToValidate.equals("idleDurationHour") || fieldNameToValidate.equals("idleDurationMin") || fieldNameToValidate.equals("idleDurationSec")) {
                while (record.getIdleDurationSec() >= 60) {
                    record.setIdleDurationMin(record.getIdleDurationMin() + 1);
                    record.setIdleDurationSec(record.getIdleDurationSec() - 60);
                }
                while (record.getIdleDurationMin() >= 60) {
                    record.setIdleDurationHour(record.getIdleDurationHour() + 1);
                    record.setIdleDurationMin(record.getIdleDurationMin() - 60);
                }
                return record;
            } else if (fieldNameToValidate.equals("startExecutionDate")) {
                if (!isBlank(record.getStartExecutionDate())) {
                    if (dateHandler.compareDatesIgnoreTime(record.getStartExecutionDate(), Functions.nowDate(), userData) < 0) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The start date may not be before today.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("startExecutionTime")) {
                if (!isBlank(record.getStartExecutionDate()) && !isBlank(record.getStartExecutionTime())) {
                    if (dateHandler.compareDatesIgnoreTime(record.getStartExecutionDate(), Functions.nowDate(), userData) == 0) {
                        Calendar startDate = Calendar.getInstance();
                        startDate.setTime(record.getStartExecutionDate());

                        Calendar startTime = Calendar.getInstance();
                        startTime.setTime(record.getStartExecutionTime());

                        startDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
                        startDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
                        startDate.set(Calendar.SECOND, startTime.get(Calendar.SECOND));

                        Calendar now = Calendar.getInstance();

                        if (startDate.before(now)) {
                            Logger.getLogger("emc").log(Level.SEVERE, "The start time may not be before now.", userData);
                            valid = false;
                        }

                    }
                }
            }
        }
        return valid;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);
        if (valid) {
            valid = doSaveValidation(vobject, userData);
        }
        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);
        if (valid) {
            valid = doSaveValidation(vobject, userData);
        }
        return valid;
    }

    private boolean doSaveValidation(EMCTable theRecord, EMCUserData userData) {
        BaseTimedOperations record = (BaseTimedOperations) theRecord;
        if (record.getIdleDurationHour() == 0 && record.getIdleDurationMin() == 0 && record.getIdleDurationSec() == 0) {
            logMessage(Level.SEVERE, "Idle time can not be 0", userData);
            return false;
        }
        return true;
    }

    @Override
    public void startTimedOperation(String operationId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseTimedOperations.class.getName());
        query.addAnd("operationEnumId", operationId);
        BaseTimedOperations timedOperation = (BaseTimedOperations) util.executeSingleResultQuery(query, userData);
        if (timedOperation == null) {
            return;
        }
        timedBeanManager.startTimer(timedOperation, userData);
        timedOperation.setCurrentStatus(EnumTimedOperationStatus.STARTED.toString());
        this.update(timedOperation, userData);
    }

    @Override
    public void stopTimedOperation(String operationId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseTimedOperations.class.getName());
        query.addAnd("operationEnumId", operationId);
        BaseTimedOperations timedOperation = (BaseTimedOperations) util.executeSingleResultQuery(query, userData);
        if (timedOperation == null) {
            return;
        }
        timedBeanManager.stopTimer(timedOperation, userData);
        timedOperation.setCurrentStatus(EnumTimedOperationStatus.STOPED.toString());
        this.update(timedOperation, userData);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateInNewTx(BaseTimedOperations record, EMCUserData userData) throws EMCEntityBeanException {
        update(record, userData);
    }
}
