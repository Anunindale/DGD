/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.activities.simple;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.workflow.WorkFlowSourceButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.workflow.datasource.WorkFlowActivityDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.workflow.menuitems.display.activityMain;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class WorkFlowSimpleActivitiesForm extends BaseInternalFrame {

    private SimpleActivitiesDRM dataManager;

    public WorkFlowSimpleActivitiesForm(EMCUserData userData) {
        super("Overview Activities", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 290);
        dataManager = new SimpleActivitiesDRM(new emcGenericDataSourceUpdate(enumEMCModules.WORKFLOW.getId(), new WorkFlowActivityDS(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("employeeNumber");
        dataManager.setFormTextId2("employeeName");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        contentPanel.add(tabbed, BorderLayout.CENTER);
        contentPanel.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPanel);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("description");
        keys.add("requiredCompletionDate");
        keys.add("startDate");
        keys.add("completionDate");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        emcMenuButton btnDetailedView = new emcMenuButton("Detailed", new activityMain(), this, 0, false);
        buttonList.add(btnDetailedView);
        buttonList.add(new WorkFlowSourceButton(this, dataManager, "sourceTable", "sourceRecordId"));
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    @Override
    public void populateUserDataForPermissions(EMCUserData userData) {
        userData.setUserData(2, "emc");
    }
}
