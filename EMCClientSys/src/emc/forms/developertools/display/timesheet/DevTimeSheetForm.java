/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCCalenderUI;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.scheduletable.EMCScheduleTable;
import emc.app.components.emctable.scheduletable.EMCScheduleTableModel;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.developertools.DevTimeSheet;
import emc.entity.developertools.datasources.DevTimeSheetDS;
import emc.enums.enumQueryTypes;
import emc.forms.developertools.display.timesheet.resources.DevTimeSheetDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author wikus
 */
public class DevTimeSheetForm extends BaseInternalFrame {

    private DevTimeSheetDRM dataManager;
    private EMCUserData userData;
    private EMCControlLookupComponent lkpEmployee;

    public DevTimeSheetForm(EMCUserData userData) {
        super("Time Sheet", true, true, true, true, userData);
        this.setBounds(20, 20, 1150, 730);

        this.userData = userData.copyUserDataAndDataList();

        dataManager = new DevTimeSheetDRM(new emcGenericDataSourceUpdate(new DevTimeSheetDS(), this.userData), this.userData);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("employeeId");
        dataManager.setFormTextId2("taskSummary");

        this.setDataManager(dataManager);

        initFrame();

        populateEmp();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());

        emcJPanel formPane = new emcJPanel(new BorderLayout());
        formPane.add(lookupPane(), BorderLayout.NORTH);
        formPane.add(tabbed, BorderLayout.CENTER);

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(formPane, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel lookupPane() {
        emcJTextField txtEmployeeName = new emcJTextField();

        lkpEmployee = new EMCControlLookupComponent(new employees(), dataManager, "employeeId", txtEmployeeName, "forenames", "surname", DevTimeSheet.class.getName());
        lkpEmployee.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        query.addAnd("companyId", userData.getCompanyId());

        lkpEmployee.setTheQuery(query);

        dataManager.setLookup(lkpEmployee);

        Component[][] comp = {{new emcJLabel("Employee"), lkpEmployee, new emcJLabel("Name"), txtEmployeeName}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Employee");
    }

    private emcTablePanelUpdate tablePane() {
        EMCScheduleTableModel model = new EMCScheduleTableModel(dataManager);

        final EMCScheduleTable table = new EMCScheduleTable(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getButton() == MouseEvent.BUTTON1) {
                    int column = table.getColumnModel().getColumnIndexAtX(e.getX());

                    if (column > 0) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dataManager.getFirstDate());
                        cal.add(Calendar.DATE, column - 1);

                        dataManager.setSelectedDate(cal.getTime());
                    }
                }
            }
        });


        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);

        dataManager.setMainTableComponent(table);
        dataManager.setTablePanel(tableScroll);

        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnAdd = new emcJButton("Add") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                dataManager.addSession();
            }
        };

        emcJButton btnEdit = new emcJButton("Edit") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (dataManager.getSelectedComponent() != null) {
                    dataManager.editSession((EMCEntityClass) dataManager.getRowCache(dataManager.getSelectedComponent().getRowInCach()));
                } else {
                    Logger.getLogger("emc").log(Level.WARNING, "Please select a session first.", userData);
                }
            }
        };

        emcJButton btnRemove = new emcJButton("Remove") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (dataManager.getSelectedComponent() != null) {
                    dataManager.removeSession((EMCEntityClass) dataManager.getRowCache(dataManager.getSelectedComponent().getRowInCach()));
                } else {
                    Logger.getLogger("emc").log(Level.WARNING, "Please select a session first.", userData);
                }
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnAdd);
        buttonList.add(btnEdit);
        buttonList.add(btnRemove);

        EMCCalenderUI calendarUI = new EMCCalenderUI() {

            @Override
            protected void dateChanged() {
                dataManager.setFirstDate(retCal.getTime());
                dataManager.refreshData();
            }
        };
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        thePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        for (emcJButton butt : buttonList) {
            thePanel.add(butt, gbc);
            y++;
            gbc = emcSetGridBagConstraints.changePosition(gbc, 0, y, 0.1);
        }
        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 0, y, 0.1);


        thePanel.add(calendarUI, gbc);

        thePanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        thePanel.setPreferredSize(new Dimension(245, 25));

        return thePanel;
    }

    public String getEmployeeId() {
        return (String) lkpEmployee.getValue();
    }

    private void populateEmp() {
        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.FIND_EMPLOYEE);

        List toSend = new ArrayList();
        toSend.add(userData.getUserName());

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend.size() > 1 && toSend.get(1) instanceof String) {
            lkpEmployee.setValueOnNew((String) toSend.get(1));
        } else {
            dataManager.refreshData();
        }
    }
}
