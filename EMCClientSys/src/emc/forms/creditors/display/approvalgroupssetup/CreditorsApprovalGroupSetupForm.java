package emc.forms.creditors.display.approvalgroupssetup;

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
import emc.entity.creditors.CreditorsApprovalGroupSetup;
import emc.entity.creditors.CreditorsApprovalGroups;
import emc.entity.creditors.datasource.CreditorsApprovalGroupSetupDS;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import emc.menus.creditors.menuitems.display.CreditorsApprovalGroupsMI;
import java.awt.Component;
import java.awt.GridBagConstraints;

/** 
 *
 * @author claudette
 */
public class CreditorsApprovalGroupSetupForm extends BaseInternalFrame {

    private EMCUserData userData;
    private EMCControlLookupComponentDRM drm;
    EMCControlLookupComponent lkpApprovalGroup;

    /** Creates a new instance of CreditorsApprovalGroupSetupForm. */
    public CreditorsApprovalGroupSetupForm(EMCUserData userData) {
        super("Approval Group Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        this.userData = userData.copyUserDataAndDataList();
        drm = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(new CreditorsApprovalGroupSetupDS(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("approvalGroupId");
        drm.setFormTextId2("employeeId");
        initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        emcJPanel mainPanel = new emcJPanel(new BorderLayout());
        mainPanel.add(selectionPane(), BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        emcJTextField txtDescription = new emcJTextField();
        txtDescription.setEditable(false);
        lkpApprovalGroup = new EMCControlLookupComponent(new CreditorsApprovalGroupsMI(), drm, "approvalGroupId",
                txtDescription, "description", CreditorsApprovalGroupSetup.class.getName());
        lkpApprovalGroup.setPopup(new EMCLookupPopup(new CreditorsApprovalGroups(), "approvalGroupId", userData));
        drm.setLookup(lkpApprovalGroup);
        Component[][] comp = {{new emcJLabel("Approval Group"), lkpApprovalGroup, new emcJLabel("Description"), txtDescription}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Approval Group");
    }

    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("employeeId");
        keys.add("employeeName");
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpEmployee = new EMCLookupJTableComponent(new employees());
        lkpEmployee.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));
        table.setLookupToColumn("employeeId", lkpEmployee);

        table.setColumnEditable("employeeName", false);
    }
}