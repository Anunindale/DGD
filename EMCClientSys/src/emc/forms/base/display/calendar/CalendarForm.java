/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.calendar.BaseCalendar;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.calendar.resources.BaseTestCalanderDialog;
import emc.forms.base.display.calendar.resources.CalendarDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author wikus
 */
public class CalendarForm extends BaseInternalFrame {

    private CalendarDRM dataRelation;
    private EMCUserData userData;

    public CalendarForm(EMCUserData userData) {
        super("Calendar", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 550);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new CalendarDRM(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseCalendar(), userData), userData);
            dataRelation.setTheForm(this);
            this.setDataManager(dataRelation);
            dataRelation.setFormTextId1("calendarId");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJSplitPane split = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, topPane(), bottomPane());
        split.setDividerLocation(220);
        this.add(split);
    }

    private emcJPanel topPane() {
        emcJTabbedPane toptabbed = new emcJTabbedPane();
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJPanel tablePanel = new emcJPanel(new GridLayout(1, 1));
        tablePanel.add(topTablePane());
        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Overview"));
        toptabbed.add("Overview", tablePanel);
        thePanel.add(toptabbed, BorderLayout.CENTER);
        thePanel.add(topButtonPane(), BorderLayout.EAST);
        return thePanel;
    }

    private emcTablePanelUpdate topTablePane() {
        List keys = new ArrayList();
        keys.add("calendarId");
        keys.add("description");
//        keys.add("country");
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        //Lookup
//        keys = new ArrayList();
//        keys.add("Code");
//        keys.add("Name");
//        EMCLookupPopup pop = new EMCLookupPopup(new BaseCountries(), "Code", keys, userData);
//        EMCLookupJTableComponent lkpCountry = new EMCLookupJTableComponent(new Countries());
//        lkpCountry.setPopup(pop);
//        table.setLookupToColumn("country", lkpCountry);
        //Lookup
        emcTablePanelUpdate tablepane = new emcTablePanelUpdate(table);
        return tablepane;
    }

    private emcJPanel topButtonPane() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        thePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(new emcMenuButton("Exceptions", new emc.menus.base.menuItems.display.CalendarExceptionsMenu(), this, 0, false), gbc);
        y++;
        gbc.gridy = y;
        thePanel.add(new emcMenuButton("Shifts", new emc.menus.base.menuItems.display.CalendarShiftsMenu(), this, 1, false), gbc);
        y++;
        gbc.gridy = y;
        thePanel.add(new emcJButton("Copy Cal.") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                String newCalendar = EMCDialogFactory.createInputDialog(CalendarForm.this, "Copy Calendar", "New Calendar Name:");
                if (newCalendar != null) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.COPY_CALENDAR.toString());
                    List toSend = new ArrayList();
                    toSend.add(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "calendarId"));
                    toSend.add(newCalendar);
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    dataRelation.refreshData();
                }
            }
        }, gbc);
//        y++;
//        gbc.gridy = y;
//        thePanel.add(new emcJButton("Test") {
//
//            @Override
//            public void doActionPerformed(ActionEvent evt) {
//                super.doActionPerformed(evt);
//                new BaseTestCalanderDialog(utilFunctions.findEMCDesktop(this), dataRelation, userData);
//            }
//        }, gbc);
        y++;
        thePanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        thePanel.setPreferredSize(new Dimension(120, 25));
        return thePanel;
    }

    private emcJTabbedPane bottomPane() {
        emcJTabbedPane bottomtabbed = new emcJTabbedPane();
        emcJTextField txtHourMon = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "hourMon"));
        txtHourMon.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtHourTue = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "hourTue"));
        txtHourTue.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtHourWed = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "hourWed"));
        txtHourWed.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtHourThu = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "hourThu"));
        txtHourThu.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtHourFri = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "hourFri"));
        txtHourFri.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtHourSat = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "hourSat"));
        txtHourSat.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtHourSun = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "hourSun"));
        txtHourSun.setPreferredSize(new Dimension(70, 25));

        emcJTextField txtminMon = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "minMon"));
        txtminMon.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtminTue = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "minTue"));
        txtminTue.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtminWed = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "minWed"));
        txtminWed.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtminThu = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "minThu"));
        txtminThu.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtminFri = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "minsFri"));
        txtminFri.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtminSat = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "minSat"));
        txtminSat.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtminSun = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "minSun"));
        txtminSun.setPreferredSize(new Dimension(70, 25));

        emcJTextField txtShiftMon = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "stdShiftsMon"));
        txtShiftMon.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtShiftTue = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "stdShiftsTue"));
        txtShiftTue.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtShiftWed = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "stdShiftsWed"));
        txtShiftWed.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtShiftThu = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "stdShiftsThu"));
        txtShiftThu.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtShiftFri = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "stdShiftsFri"));
        txtShiftFri.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtShiftSat = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "stdShiftsSat"));
        txtShiftSat.setPreferredSize(new Dimension(70, 25));
        emcJTextField txtShiftSun = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "stdShiftsSun"));
        txtShiftSun.setPreferredSize(new Dimension(70, 25));

        emcYesNoComponent ynMon = new emcYesNoComponent(dataRelation, "monday");
        ynMon.setPreferredSize(new Dimension(70, 25));
        emcYesNoComponent ynTue = new emcYesNoComponent(dataRelation, "tuesday");
        ynTue.setPreferredSize(new Dimension(70, 25));
        emcYesNoComponent ynWed = new emcYesNoComponent(dataRelation, "wednesday");
        ynWed.setPreferredSize(new Dimension(70, 25));
        emcYesNoComponent ynThu = new emcYesNoComponent(dataRelation, "thursday");
        ynThu.setPreferredSize(new Dimension(70, 25));
        emcYesNoComponent ynFri = new emcYesNoComponent(dataRelation, "friday");
        ynFri.setPreferredSize(new Dimension(70, 25));
        emcYesNoComponent ynSat = new emcYesNoComponent(dataRelation, "saturday");
        ynSat.setPreferredSize(new Dimension(70, 25));
        emcYesNoComponent ynSun = new emcYesNoComponent(dataRelation, "sunday");
        ynSun.setPreferredSize(new Dimension(70, 25));

        Component[][] comp = {{new emcJLabel("Days"), new emcJLabel("Work Day"), new emcJLabel("Hours"), new emcJLabel("Minutes"), new emcJLabel("Shifts")},
            {new emcJLabel("Monday"), ynMon, txtHourMon, txtminMon, txtShiftMon},
            {new emcJLabel("Tuesday"), ynTue, txtHourTue, txtminTue, txtShiftTue},
            {new emcJLabel("Wednesday"), ynWed, txtHourWed, txtminWed, txtShiftWed},
            {new emcJLabel("Thursday"), ynThu, txtHourThu, txtminThu, txtShiftThu},
            {new emcJLabel("Friday"), ynFri, txtHourFri, txtminFri, txtShiftFri},
            {new emcJLabel("Saturday"), ynSat, txtHourSat, txtminSat, txtShiftSat},
            {new emcJLabel("Sunday"), ynSun, txtHourSun, txtminSun, txtShiftSun}};
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        thePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Setup"));
        bottomtabbed.add("Setup", thePanel);
        bottomtabbed.setMaximumSize(new java.awt.Dimension(600, 220));
        return bottomtabbed;
    }
}
