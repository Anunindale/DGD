/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.dependants;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.hr.HRDependantsTypesDropDown;
import emc.app.components.hr.HREmployeeControllLookupPanel;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HREmployeeDependants;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HREmployeeDependantsForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HREmployeeDependantsForm(EMCUserData userData) {
        super("Employee Dependants", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HREmployeeDependants(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("type");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(HREmployeeDependants.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Dependants", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("type");
        keys.add("relation");
        keys.add("name");
        keys.add("surname");
        keys.add("dateOfBirth");
        keys.add("idNumber");
        keys.add("contactNum");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        table.setComboBoxLookupToColumn("type", new HRDependantsTypesDropDown());
    }
}
