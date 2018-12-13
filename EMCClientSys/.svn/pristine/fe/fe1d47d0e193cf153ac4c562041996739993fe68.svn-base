/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.requirementsplanning;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanningDS;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.pop.menuitems.display.POPPlannedPurchaseOrderMenu;
import emc.menus.pop.menuitems.display.PurchaseOrders;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author wikus
 */
public class InventoryRequirementsPlanningForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDRMViewOnly dataManager;

    public InventoryRequirementsPlanningForm(EMCUserData userData) {
        super("Requirements Planning", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 360);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new InventoryRequirementsPlanningDS(), userData), userData);
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
        final emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setColumnCellEditor("demandId", new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                EMCDataType dt = dataManager.getDataType("demandId");
                if (!Functions.checkBlank(dataManager.getLastFieldValueAt("demandType"))) {
                    switch (RequirementsPlanningReferenceTypes.fromShortString((String) dataManager.getLastFieldValueAt("demandType"))) {
                        case PLANNED_PURCHASE_ORDER:
                            super.changeMenuItem(new POPPlannedPurchaseOrderMenu());
                            dt.setRelatedTable(POPPlannedPurchaseOrders.class.getName());
                            dt.setRelatedField("plannedPOId");
                            break;
                        case PURCHASE_ORDER:
                            super.changeMenuItem(new PurchaseOrders());
                            dt.setRelatedTable(POPPurchaseOrderMaster.class.getName());
                            dt.setRelatedField("purchaseOrderId");
                            break;

                    }
                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        });
        table.setColumnCellEditor("supplyId", new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                EMCDataType dt = dataManager.getDataType("supplyId");
                if (!Functions.checkBlank(dataManager.getLastFieldValueAt("supplyType"))) {
                    switch (RequirementsPlanningReferenceTypes.fromShortString((String) dataManager.getLastFieldValueAt("supplyType"))) {
                        case PLANNED_PURCHASE_ORDER:
                            super.changeMenuItem(new POPPlannedPurchaseOrderMenu());
                            dt.setRelatedTable(POPPlannedPurchaseOrders.class.getName());
                            dt.setRelatedField("plannedPOId");
                            break;
                        case PURCHASE_ORDER:
                            super.changeMenuItem(new PurchaseOrders());
                            dt.setRelatedTable(POPPurchaseOrderMaster.class.getName());
                            dt.setRelatedField("purchaseOrderId");
                            break;
                    }
                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        });
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }
}
