/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.absenteeismlog;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.hr.HRAbsenteeismLog;
import emc.entity.hr.HRAbsenteeismType;
import emc.entity.hr.datasource.HRAbsenteeismLogDS;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import emc.menus.hr.menuitems.display.HRAbsenteeismTypeMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRAbsenteeismLogForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HRAbsenteeismLogForm(EMCUserData userData) {
        super("Absenteeism Log", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 320);
        this.userData = userData.copyUserDataAndDataList();
        dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(new HRAbsenteeismLogDS(), userData), userData);
        dataRelation.setTheForm(this);
        this.setDataManager(dataRelation);
        dataRelation.setFormTextId1("employeeNumber");
        dataRelation.setFormTextId2("absenteeismType");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Absenteeism Log", tablePane());
        contentPane.add(lookupPane(), BorderLayout.NORTH);
        contentPane.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJPanel lookupPane() {
        emcJTextField txtName = new emcJTextField();
        EMCControlLookupComponent lkpEmp = new EMCControlLookupComponent(new employees(), dataRelation, "employeeNumber", txtName, "surname", HRAbsenteeismLog.class.getName());
        lkpEmp.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));
        dataRelation.setLookup(lkpEmp);
        Component[][] comp = {{new emcJLabel("Employee"), lkpEmp, new emcJLabel("Name"), txtName}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Employee");
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("absentFromDate");
        keys.add("absentToDate");
        keys.add("absenteeismType");
        keys.add("description");
        keys.add("absentHourse");
        keys.add("absentMinutes");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpType = new EMCLookupJTableComponent(new HRAbsenteeismTypeMenu());
        lkpType.setPopup(new EMCLookupPopup(new HRAbsenteeismType(), "absenteeismType", userData));
        table.setLookupToColumn("absenteeismType", lkpType);
    }
}
