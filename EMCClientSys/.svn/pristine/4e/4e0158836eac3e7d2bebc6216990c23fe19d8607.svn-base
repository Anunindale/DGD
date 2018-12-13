/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.activityalert;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.workflow.WFActivityAlerts;
import emc.entity.workflow.WorkFlowActivityGroups;
import emc.entity.workflow.WorkFlowActivityTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.workflow.WFMyActivitiesEnum;
import emc.framework.EMCUserData;
import emc.menus.workflow.menuitems.display.activityGroup;
import emc.menus.workflow.menuitems.display.activityTypes;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class WFActivityAlertForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public WFActivityAlertForm(EMCUserData userData) {
        super("Activity Alerts", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 290);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.WORKFLOW.getId(), new WFActivityAlerts(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("activityGroup");
        dataManager.setFormTextId2("activityType");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("activityCategory");
        keys.add("activityGroup");
        keys.add("activityType");
        keys.add("manager");
        keys.add("employee");
        keys.add("taskManager");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        table.setComboBoxLookupToColumn("activityCategory", new emcJComboBox(WFMyActivitiesEnum.values()));

        EMCLookupJTableComponent lkpActivityGroup = new EMCLookupJTableComponent(new activityGroup());
        lkpActivityGroup.setPopup(new EMCLookupPopup(new WorkFlowActivityGroups(), "activityGroup", userData));
        table.setLookupToColumn("activityGroup", lkpActivityGroup);

        EMCLookupJTableComponent lkpActivityType = new EMCLookupJTableComponent(new activityTypes());
        lkpActivityType.setPopup(new EMCLookupPopup(new WorkFlowActivityTypes(), "activityType", userData));
        table.setLookupToColumn("activityType", lkpActivityType);
    }
}
