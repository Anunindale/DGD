/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.requirementsplanning;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanningHistoryDS;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryRequirementsPlanningHistoryForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDRMViewOnly dataManager;

    public InventoryRequirementsPlanningHistoryForm(EMCUserData userData) {
        super("Requirements Planning", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 360);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new InventoryRequirementsPlanningHistoryDS(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("itemReference");
        dataManager.setFormTextId2("itemDescription");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("demandType");
        keys.add("demandId");
        keys.add("demandDate");
        keys.add("demandQuantity");
        keys.add("supplyType");
        keys.add("supplyId");
        keys.add("supplyDate");
        keys.add("supplyQuantity");
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
