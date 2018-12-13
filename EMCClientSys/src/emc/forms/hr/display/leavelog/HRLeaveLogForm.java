/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.leavelog;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.hr.HREmployeeControllLookupPanel;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRLeaveLog;
import emc.entity.hr.HRLeaveType;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.hr.menuitems.display.HRLeaveTypeMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRLeaveLogForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HRLeaveLogForm(EMCUserData userData) {
        super("Leave Log", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRLeaveLog(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("leaveType");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(HRLeaveLog.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Leave", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("leaveType");
        keys.add("startDate");
        keys.add("returnDate");
        keys.add("numberOfDays");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupPopup typePop = new EMCLookupPopup(new HRLeaveType(), "leaveType", userData);
        EMCLookupJTableComponent lkpType = new EMCLookupJTableComponent(new HRLeaveTypeMenu());
        lkpType.setPopup(typePop);
        table.setLookupToColumn("leaveType", lkpType);
    }
}
