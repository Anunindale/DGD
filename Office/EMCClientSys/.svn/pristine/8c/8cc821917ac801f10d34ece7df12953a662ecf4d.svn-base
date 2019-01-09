/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.employeecategoryhistory;

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
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmployeeCategoryHistory;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.datasource.BaseEmployeeCategoryHistoryDS;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class BaseEmployeeCategoryHistoryForm extends BaseInternalFrame {

    private EMCUserData userData;
    private EMCControlLookupComponentDRM dataManager;

    public BaseEmployeeCategoryHistoryForm(EMCUserData userData) {
        super("Employee Category History", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 350);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(new BaseEmployeeCategoryHistoryDS(), userData), userData) {

            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add or remove rows from this table.", getUserData());
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add or remove rows from this table.", getUserData());
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("employeeNumber");
        dataManager.setFormTextId2("categoryChangeDate");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Category History", tablePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(lookupPane(), BorderLayout.NORTH);
        contentPane.add(tabbed, BorderLayout.CENTER);

        this.setContentPane(contentPane);
    }

    private emcJPanel lookupPane() {
        emcJTextField txtEmployeeName = new emcJTextField();
        EMCControlLookupComponent lkpEmployee = new EMCControlLookupComponent(new employees(), dataManager, "employeeNumber", txtEmployeeName, "forenames", "surname", BaseEmployeeCategoryHistory.class.getName());
        lkpEmployee.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));
        dataManager.setLookup(lkpEmployee);
        Component[][] comp = {{new emcJLabel("Employee"), lkpEmployee, new emcJLabel("Name"), txtEmployeeName}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Employee");
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("categoryChangeDate");
        keys.add("fromCategory");
        keys.add("fromCategoryDescription");
        keys.add("toCategory");
        keys.add("toCategoryDescription");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }
}
