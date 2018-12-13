/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar;

import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.calendar.BaseCalendar;
import emc.entity.base.calendar.BaseCalendarShifts;
import emc.enums.base.calendar.CalendarShiftType;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.calendar.resources.ShiftDRM;
import emc.forms.base.display.calendar.resources.WeekDaysDropDown;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.CalendarMenu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CalendarShiftsForm extends BaseInternalFrame {

    private ShiftDRM dataRelation;
    private EMCUserData userData;
    private CalendarShiftType shiftType = CalendarShiftType.STANDARD;

    public CalendarShiftsForm(EMCUserData userData) {
        super("Calendar Shifts", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 350);
        try {
            if (userData.getUserData(5) != null) {
                shiftType = CalendarShiftType.fromString(userData.getUserData(5).toString());
            }
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new ShiftDRM(
                    new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseCalendarShifts(), userData), shiftType, userData);
            dataRelation.setTheForm(this);
            this.setDataManager(dataRelation);
            dataRelation.setFormTextId1("calendarId");
            dataRelation.setFormTextId2("shiftDate");
        } catch (Exception ex) {

        }
        initFrame();
    }

    private void initFrame() {
        List keys = new ArrayList();
        keys.add("calendarId");
        keys.add("description");
        EMCLookupPopup popup = new EMCLookupPopup(new BaseCalendar(), "calendarId", keys, userData);
        emcJTextField txtDesc = new emcJTextField();
        txtDesc.setEditable(false);
        EMCControlLookupComponent lkpCalendar = new EMCControlLookupComponent(new CalendarMenu(), dataRelation, "calendarId", txtDesc, "description", BaseCalendarShifts.class.getName());
        lkpCalendar.setPopup(popup);
        if (shiftType.equals(CalendarShiftType.STANDARD)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
            query.addAnd("companyId", userData.getCompanyId());
            query.addStringNotBlank("dayOfWeek", BaseCalendarShifts.class.getName(), EMCQueryBracketConditions.AND);
            lkpCalendar.setFormQuery(query);
        } else if (shiftType.equals(CalendarShiftType.EXCEPTION)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
            query.addAnd("companyId", userData.getCompanyId());
            query.addStringIsBlank("dayOfWeek", BaseCalendarShifts.class.getName(), EMCQueryBracketConditions.AND);
            lkpCalendar.setFormQuery(query);
        }
        dataRelation.setLookup(lkpCalendar);
        this.add(new EMCControlLookupPanel("Calendar", "Calendar", lkpCalendar, "Description", txtDesc, tablePane(), shiftType.equals(CalendarShiftType.STANDARD) ? "Standard" : "Exceptions"));
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        if (shiftType.equals(CalendarShiftType.STANDARD)) keys.add("dayOfWeek");
        if (shiftType.equals(CalendarShiftType.EXCEPTION)) keys.add("shiftDate");
        keys.add("shiftStartTime");
        keys.add("shiftEndTime");
        keys.add("shiftNumber");
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        if (shiftType.equals(CalendarShiftType.STANDARD)) table.setComboBoxLookupToColumn(0, new WeekDaysDropDown());
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tablepane = new emcTablePanelUpdate(table);
        this.setTablePanel(tablepane);
        return tablepane;
    }
}
