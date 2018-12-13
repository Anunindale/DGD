/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.calendar;

import emc.entity.base.calendar.BaseCalendar;
import emc.entity.base.calendar.BaseCalendarExceptions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerBaseMessageEnum;
import emc.tables.EMCTable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseCalendarExceptionsBean extends EMCEntityBean implements BaseCalendarExceptionsLocal {

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            BaseCalendarExceptions exc = (BaseCalendarExceptions) theRecord;
            if (exc.getPercentageOfTarget() == 0 && !fieldNameToValidate.equals("percentageOfTarget")) {
                exc.setPercentageOfTarget(100);
                return exc;
            }
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doUpdateValidation(doHoursSave((BaseCalendarExceptions) vobject), userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        return super.doInsertValidation(doHoursSave((BaseCalendarExceptions) vobject), userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseCalendarExceptions calendarExceptions = (BaseCalendarExceptions) iobject;
        if (!isBlank(calendarExceptions.getCalendarId())) {
            dateHandler.markCalendarAsUpdated(calendarExceptions.getCalendarId(), userData);
        }
        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseCalendarExceptions calendarExceptions = (BaseCalendarExceptions) uobject;
        if (!isBlank(calendarExceptions.getCalendarId())) {
            dateHandler.markCalendarAsUpdated(calendarExceptions.getCalendarId(), userData);
        }
        return super.update(uobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseCalendarExceptions calendarExceptions = (BaseCalendarExceptions) dobject;
        if (!isBlank(calendarExceptions.getCalendarId())) {
            dateHandler.markCalendarAsUpdated(calendarExceptions.getCalendarId(), userData);
        }
        return super.delete(dobject, userData);
    }

    private BaseCalendarExceptions doHoursSave(BaseCalendarExceptions record) {
        record.setHours(record.getHours() + new Double(Math.floor(record.getMinutes() / 60)).intValue());
        record.setMinutes((record.getMinutes() % 60));
        record.setWorkHours(record.getHours() + (new Integer(record.getMinutes()).doubleValue() / 60));
        return record;
    }

    public List getMassData(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendar.class.getName());
        List<BaseCalendar> calList = util.executeGeneralSelectQuery(query, userData);
        List ret = new ArrayList();
        int count = 0;
        for (BaseCalendar cal : calList) {
            ret.add(cal.getCalendarId());
            count++;
        }
        return ret;
    }

    public void odMassUpdate(long recordId, List calendarList, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class.getName());
        query.addAnd("recordID", recordId);
        BaseCalendarExceptions exception = (BaseCalendarExceptions) util.executeSingleResultQuery(query, userData);
        BaseCalendarExceptions newException;
        query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class.getName());
        query.addAnd("lineDate", Functions.date2String(exception.getLineDate(), "yyyy-MM-dd"));
        for (Object o : calendarList) {
            query.removeAnd("calendarId");
            query.addAnd("calendarId", o);
            if (!util.exists(query, userData)) {
                newException = (BaseCalendarExceptions) doClone(exception, userData);
                newException.setCalendarId((String) o);
                super.insert(newException, userData);
            }
        }
        logMessage(Level.INFO, ServerBaseMessageEnum.EXCOPY, userData);
    }

    public double findPercentageOfTarget(String calendarId, Date firstDate, Date secondDate, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class.getName());
        query.addAnd("calendarId", calendarId);
        query.addAndCommaSeperated("lineDate", Functions.date2String(firstDate, "yyyy/MM/dd") + "~" +
                (secondDate == null ? Functions.date2String(firstDate, "yyyy/MM/dd") : Functions.date2String(secondDate, "yyyy/MM/dd")),
                BaseCalendarExceptions.class.getName(), EMCQueryConditions.EQUALS);
        query.addFieldAggregateFunction("percentageOfTarget", "SUM");
        Double perc = (Double) util.executeSingleResultQuery(query, userData);
        if (perc == null) {
            return 1;
        }
        if (secondDate == null) {
            return perc / 100;
        }
        return (perc / Functions.calculateDayDiff(firstDate, secondDate)) / 100;
    }
}
