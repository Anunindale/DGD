/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.base.calendar.BaseCalendarExceptions;
import emc.entity.base.calendar.BaseCalendarShifts;
import emc.enums.base.calendar.CalendarShiftType;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CalendarDRM extends emcDataRelationManagerUpdate {

    public CalendarDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object calendarId = super.getFieldValueAt(super.getLastRowAccessed(), "calendarId");
        Object desc = super.getFieldValueAt(super.getLastRowAccessed(), "description");
        if (!Functions.checkBlank(calendarId)) {
            EMCQuery query;
            List theList;
            switch (Index) {
                case 0:
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class.getName());
                    query.addAnd("calendarId", calendarId);
                    query.addAnd("companyId", formUserData.getCompanyId());
                    theList = new ArrayList();
                    theList.add(0, query.toString());
                    theList.add(1, query.getCountQuery());
                    theList.add(2, Functions.checkBlank(desc) ? "" : desc);
                    theList.add(3, calendarId);
                    formUserData.setUserData(theList);
                    break;
                case 1:
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
                    query.addAnd("calendarId", calendarId);
                    query.addAnd("companyId", formUserData.getCompanyId());
                    query.addAnd("shiftDate", null);
                    theList = new ArrayList();
                    theList.add(0, query.toString());
                    theList.add(1, query.getCountQuery());
                    theList.add(2, Functions.checkBlank(desc) ? "" : desc);
                    theList.add(3, calendarId);
                    theList.add(4, "");
                    //Sets form behaviour
                    theList.add(5, CalendarShiftType.STANDARD.toString());
                    formUserData.setUserData(theList);
                    break;
            }
        }
        return formUserData;
    }
}
