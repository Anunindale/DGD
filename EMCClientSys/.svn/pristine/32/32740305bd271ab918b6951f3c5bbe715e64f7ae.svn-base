/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepgroups;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.sop.SOPSalesRepGroupsSetup;
import emc.entity.sop.datasource.SOPSalesRepGroupsDS;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import emc.menus.sop.menuitems.display.SOPSalesRepGroupSetupMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPSalesRepGroupsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public SOPSalesRepGroupsForm(EMCUserData userData) {
        super("Sales Rep Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 340);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPSalesRepGroupsDS(), userData), userData) {

            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData userData = super.generateRelatedFormUserData(formUserData, Index);
                EMCQuery query;
                List udList;
                switch (Index) {
                    case 0:
                        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepGroupsSetup.class);
                        query.addAnd("repGroupId", dataManager.getLastFieldValueAt("repGroupId"));
                        udList = new ArrayList();
                        udList.add(query);
                        udList.add("");
                        udList.add(dataManager.getLastFieldValueAt("description"));
                        udList.add(dataManager.getLastFieldValueAt("repGroupId"));
                        userData.setUserData(udList);
                }
                return userData;
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("repGroupId");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("repGroupId");
        keys.add("description");
        keys.add("groupManager");
        keys.add("groupManagerName");
        keys.add("groupManagerCommissionPerc");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupTableLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupTableLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpEmployee = new EMCLookupJTableComponent(new employees());
        lkpEmployee.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));
        table.setLookupToColumn("groupManager", lkpEmployee);
        table.setColumnEditable("groupManagerName", false);
    }

    private emcJPanel buttonPane() {
        emcMenuButton btnSetup = new emcMenuButton("Setup", new SOPSalesRepGroupSetupMenu(), this, 0, false);
        btnSetup.setEnabled(false);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnSetup);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
