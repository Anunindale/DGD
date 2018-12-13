package emc.forms.base.display.employeeaccessgroupemployees;

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
import emc.entity.base.employeeaccessgroup.BaseEmployeeAccessGroup;
import emc.entity.base.employeeaccessgroupemployees.BaseEmployeeAccessGroupEmployees;
import emc.entity.base.employeeaccessgroupemployees.datasourse.BaseEmployeeAccessGroupEmployeesDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.BaseEmployeeAccessGroupMenu;
import emc.menus.base.menuItems.display.employees;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseEmployeeAccessGroupEmployeesForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public BaseEmployeeAccessGroupEmployeesForm(EMCUserData userData) {
        super("Access Group Employees", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserDataAndDataList();
        dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseEmployeeAccessGroupEmployeesDS(), userData), userData);
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("accessGroup");
        dataRelation.setFormTextId2("employeeName");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPanel = new emcJPanel(new BorderLayout());
        contentPanel.add(lookupPane(), BorderLayout.NORTH);
        contentPanel.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPanel);
    }

    private emcJPanel lookupPane() {
        emcJTextField txtDesc = new emcJTextField();
        EMCControlLookupComponent lkpAccessGroup = new EMCControlLookupComponent(new BaseEmployeeAccessGroupMenu(), dataRelation, "accessGroup",
                txtDesc, "description", BaseEmployeeAccessGroupEmployees.class.getName());
        lkpAccessGroup.setPopup(new EMCLookupPopup(new BaseEmployeeAccessGroup(), "accessGroup", userData));
        dataRelation.setLookup(lkpAccessGroup);
        Component[][] comp = {{new emcJLabel("Access Group"), lkpAccessGroup, new emcJLabel("Description"), txtDesc}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Access Group");
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Employees", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("employeeId");
        keys.add("employeeName");
        emcTableModelUpdate model = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpEmployee = new EMCLookupJTableComponent(new employees());
        lkpEmployee.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));
        table.setLookupToColumn("employeeId", lkpEmployee);

        table.setColumnEditable("employeeName", false);
    }
}
