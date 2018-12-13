/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar.resources;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.entity.base.calendar.BaseCalendarShifts;
import emc.enums.base.calendar.CalendarShiftType;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wikus
 */
public class ExceptionDRM extends EMCControlLookupComponentDRM {

    public ExceptionDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object calendarId = super.getFieldValueAt(super.getLastRowAccessed(), "calendarId");
        Object exceptionDate = super.getFieldValueAt(super.getLastRowAccessed(), "lineDate");
        if (!Functions.checkBlank(calendarId) && (!Functions.checkBlank(exceptionDate))) {
            EMCQuery query;
            List theList;
            switch (Index) {
                case 0:
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
                    query.addAnd("calendarId", calendarId);
                    query.addAnd("companyId", formUserData.getCompanyId());
                    query.openConditionBracket(EMCQueryBracketConditions.AND);
                    query.addAnd("shiftDate", Functions.date2String((Date) exceptionDate, "yyyy-MM-dd"));
                    query.openConditionBracket(EMCQueryBracketConditions.OR);
                    query.addAnd("createdDate", Functions.date2String(Functions.nowDate(), "yyyy-MM-dd"), EMCQueryConditions.GREATER_THAN_EQ);
                    query.addAnd("createdTime", Functions.date2String(Functions.nowDate(), "HH:mm"), EMCQueryConditions.GREATER_THAN_EQ);
                    query.addAnd("createdBy", formUserData.getUserName());
                    query.closeConditionBracket();
                    query.closeConditionBracket();
                    theList = new ArrayList();
                    theList.add(0, query.toString());
                    theList.add(1, query.getCountQuery());
                    theList.add(2, "");
                    theList.add(3, calendarId);
                    theList.add(4, "");
                    //Sets form behaviour
                    theList.add(5, CalendarShiftType.EXCEPTION.toString());
                    formUserData.setUserData(theList);
                    break;
            }
        }
        return formUserData;
    }
}
