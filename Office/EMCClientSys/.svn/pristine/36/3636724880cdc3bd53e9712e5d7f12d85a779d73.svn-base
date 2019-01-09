/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.requirementsplanning.audit;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.datasource.InventoryReqPlanAudit;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryReqPlanAuditForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDRMViewOnly dataManager;

    public InventoryReqPlanAuditForm(EMCUserData userData) {
        super("Requirements Planning Audit", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        this.userData = userData.copyUserDataAndDataList();

        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new InventoryReqPlanAudit(), userData), userData);
        dataManager.setTheForm(InventoryReqPlanAuditForm.this);
        dataManager.setFormTextId1("recordType");
        dataManager.setFormTextId2("recordType");

        this.setDataManager(dataManager);

        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Audit", tablePane());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbed, BorderLayout.CENTER);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("recordType");
        keys.add("sourceCount");
        keys.add("sourceSum");
        keys.add("reqPlanCount");
        keys.add("reqPlanSum");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);

        dataManager.setMainTableComponent(table);
        dataManager.setTablePanel(tableScroll);

        return tableScroll;
    }
}
