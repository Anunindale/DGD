/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.planning;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.pop.planning.datasource.POPPlannedPurchaseOrdersDS;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemGroups;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.methods.pop.ServerPOPMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class POPPlannedPurchaseOrderReleaseForm extends BaseInternalFrame {

    private StockDRM dataManager;
    private StockDRM planningDRM;
    private EMCUserData userData;

    public POPPlannedPurchaseOrderReleaseForm(EMCUserData userData) {
        super("Planned Purchase Order Release", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 390);
        planningDRM = (StockDRM) userData.getUserData().remove(0);
        this.userData = userData.copyUserDataAndDataList();
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null);
        dataManager = new StockDRM(new emcGenericDataSourceUpdate(new POPPlannedPurchaseOrdersDS(), userData), param, userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("plannedPOId");
        dataManager.setFormTextId2("itemReference");
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
        keys.add("plannedPOId");
        keys.add("itemGroup");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantityToRelease");
        keys.add("includeOnRelease");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnEditable("plannedPOId", false);
        table.setColumnEditable("itemGroup", false);
        table.setColumnEditable("itemReference", false);
        table.setColumnEditable("itemDescription", false);
        table.setColumnEditable("dimension1", false);
        table.setColumnEditable("dimension3", false);
        table.setColumnEditable("dimension2", false);
        table.setColumnCellEditor("itemGroup", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemGroups()));
        table.setColumnCellEditor("itemReference", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        table.setColumnCellEditor("dimension1", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
        table.setColumnCellEditor("dimension3", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
        table.setColumnCellEditor("dimension2", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOk = new emcJButton("Release") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                Map<Long, Long> versionMap = new HashMap<Long, Long>();
                POPPlannedPurchaseOrdersDS ds;
                for (int r = 0; r < dataManager.getRowCount(); r++) {
                    ds = (POPPlannedPurchaseOrdersDS) dataManager.getRowCache(r);
                    if (ds.isIncludeOnRelease()) {
                        versionMap.put(ds.getRecordID(), ds.getVersion());
                    }
                }
                if (versionMap.isEmpty()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No records were found to be included on the release.", userData);
                } else {
                    List toSend = new ArrayList();
                    toSend.add(userData.getUserData(1));
                    toSend.add(userData.getUserData(2));
                    toSend.add(userData.getUserData(3));
                    toSend.add(versionMap);
                    EMCCommandClass cmd = new EMCCommandClass(ServerPOPMethods.RELEASE_PLANNED_PURCHASE_ORDER);
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    if (toSend.size() > 1 && toSend.get(1) instanceof String) {
                        Logger.getLogger("emc").log(Level.INFO, "Purchase order released - " + toSend.get(1), userData);
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to release purchase order. Data refreshed, you may try again.", userData);
                    }
                    dataManager.refreshData();
                }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOk);
       return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
