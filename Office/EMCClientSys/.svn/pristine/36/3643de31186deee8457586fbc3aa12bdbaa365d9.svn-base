/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar;

import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.calendar.BaseCalendar;
import emc.entity.base.calendar.BaseCalendarExceptionTypes;
import emc.entity.base.calendar.BaseCalendarExceptions;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.calendar.resources.ExceptionDRM;
import emc.forms.base.display.calendar.resources.ExceptionTypeDropDown;
import emc.forms.base.display.calendar.resources.MassExceptionUpdateDialog;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.CalendarExceptionTypesMenu;
import emc.menus.base.menuItems.display.CalendarMenu;
import emc.menus.base.menuItems.display.CalendarShiftsMenu;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CalendarExceptionForm extends BaseInternalFrame {

    private ExceptionDRM dataRelation;
    private EMCUserData userData;

    public CalendarExceptionForm(EMCUserData userData) {
        super("Calendar Exceptions", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 350);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new ExceptionDRM(
                    new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseCalendarExceptions(), userData), userData);
            dataRelation.setTheForm(this);
            this.setDataManager(dataRelation);
            dataRelation.setFormTextId1("calendarId");
            dataRelation.setFormTextId2("lineDate");
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
        EMCControlLookupComponent lkpCalendar = new EMCControlLookupComponent(new CalendarMenu(), dataRelation, "calendarId", txtDesc, "description", BaseCalendarExceptions.class.getName());
        lkpCalendar.setPopup(popup);
        dataRelation.setLookup(lkpCalendar);
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(new EMCControlLookupPanel("Calendar", "Calendar", lkpCalendar, "Description", txtDesc, tablePane(), "Exceptions"), BorderLayout.CENTER);
        thePanel.add(buttonPane(), BorderLayout.EAST);
        this.add(thePanel);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("lineDate");
        keys.add("type");
        keys.add("repeatYearly");
        keys.add("hours");
        keys.add("minutes");
        keys.add("numberOfShifts");
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        //Lookups
        EMCLookupJTableComponent lkpExceptionType = new EMCLookupJTableComponent(new CalendarExceptionTypesMenu());
        lkpExceptionType.setPopup(new EMCLookupPopup(new BaseCalendarExceptionTypes(), "exceptionType", userData));
        table.setLookupToColumn("type", lkpExceptionType);
        //Lookups
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tablepane = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tablepane);
        return tablepane;
    }

    private emcJPanel buttonPane() {
        emcJButton theButton = new emcJButton("Mass Update") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                Long recId = (Long) dataRelation.getLastFieldValueAt("recordID");
                if (recId != null && recId != 0) {
                    new MassExceptionUpdateDialog(recId, userData);
                }
            }
        };
        emcMenuButton btnShifts = new emcMenuButton("Shifts", new CalendarShiftsMenu(), this, 0, false);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(theButton);
        buttonList.add(btnShifts);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
