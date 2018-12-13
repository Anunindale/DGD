/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepgroupsetup;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
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
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.SOPSalesRepGroups;
import emc.entity.sop.SOPSalesRepGroupsSetup;
import emc.entity.sop.datasource.SOPSalesRepGroupSetupDS;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import emc.menus.sop.menuitems.display.SOPSalesRepCommissionMenu;
import emc.menus.sop.menuitems.display.SOPSalesRepGroupsMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPSalesRepGroupSetupForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataManager;
    private EMCUserData userData;

    public SOPSalesRepGroupSetupForm(EMCUserData userData) {
        super("Sales Rep Group Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 340);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPSalesRepGroupSetupDS(), userData), userData) {

            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData userData = super.generateRelatedFormUserData(formUserData, Index);
                EMCQuery query;
                List udList;
                switch (Index) {
                    case 0:
                        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                        query.addAnd("repId", dataManager.getLastFieldValueAt("repId"));
                        udList = new ArrayList();
                        udList.add(query);
                        udList.add("");
                        udList.add(dataManager.getLastFieldValueAt("repName"));
                        udList.add(dataManager.getLastFieldValueAt("repId"));
                        userData.setUserData(udList);
                }
                return userData;
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("repGroupId");
        dataManager.setFormTextId2("repId");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(lookupPane(), BorderLayout.NORTH);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel lookupPane() {
        emcJTextField txtDescription = new emcJTextField();
        EMCControlLookupComponent lkpRepGroup = new EMCControlLookupComponent(new SOPSalesRepGroupsMenu(), dataManager, "repGroupId", txtDescription, "description", SOPSalesRepGroupsSetup.class.getName());
        lkpRepGroup.setPopup(new EMCLookupPopup(new SOPSalesRepGroups(), "repGroupId", userData));
        dataManager.setLookup(lkpRepGroup);
        Component[][] comp = {{new emcJLabel("Group"), lkpRepGroup, new emcJLabel("Description"), txtDescription}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Sales Rep Group");
    }

    private emcJPanel tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("repId");
        keys.add("repName");
        keys.add("groupManagerCommissionPerc");
        keys.add("defaultCommissionPerc");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpRep = new EMCLookupJTableComponent(new employees());
        lkpRep.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));
        table.setLookupToColumn("repId", lkpRep);
        table.setColumnEditable("repName", false);
    }

    private emcJPanel buttonPane() {
        emcMenuButton btnCommission = new emcMenuButton("Commission", new SOPSalesRepCommissionMenu(), this, 0, false);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnCommission);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
