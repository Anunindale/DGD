/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.costchange.summary;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.pop.costchange.CostChangeSummaryDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.users;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.pop.menuitems.display.CostChangeReasonMenu;
import emc.menus.pop.menuitems.display.PurchaseOrders;
import emc.menus.pop.menuitems.display.Suppliers;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class CostChangeSummaryForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData copyUD;

    public CostChangeSummaryForm(EMCUserData userData) {
        super("Cost Change", true, true, true, true, userData);
        this.setBounds(20, 20, 900, 290);
        try {
            copyUD = userData.copyUserDataAndDataList();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(), new CostChangeSummaryDS(), userData), userData) {

                @Override
                public void insertRowCache(int rowIndex, boolean addRowAfter) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You can't add a record to this table.", CostChangeSummaryForm.this.copyUD);
                }

                @Override
                public void deleteRowCache(int rowIndex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You can't remove a record from this table.", CostChangeSummaryForm.this.copyUD);
                }

                @Override
                public void updatePersist(int rowIndex) {
                    super.updatePersist(rowIndex);
                    super.refreshData();
                }
            };
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("purchaseOrderId");
            dataRelation.setFormTextId2("itemReference");
        } catch (Exception e) {

        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Cost Change", contentPane());
        this.add(tabbed);
    }

    private emcJPanel contentPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout(1, 1));
        List keys = new ArrayList();
        keys.add("purchaseOrderId");
        keys.add("suppId");
        keys.add("quantity");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("itemDimension1");
        keys.add("itemDimension3");
        keys.add("itemDimension2");
        keys.add("itemCost");
        keys.add("itemPrice");
        keys.add("createdBy");
        keys.add("reason");
        keys.add("approveCost");
        keys.add("updateCost");
        final emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == model.getColumnByFieldName("itemPrice") ||
                        column == model.getColumnByFieldName("approveCost") ||
                        column == model.getColumnByFieldName("updateCost")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        table.setLookupCellEditor(model.getColumnByFieldName("purchaseOrderId"), new EMCGoToMainTableEditor(new EMCStringDocument(), new PurchaseOrders()));
        table.setLookupCellEditor(model.getColumnByFieldName("suppId"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Suppliers()));
        table.setLookupCellEditor(model.getColumnByFieldName("itemReference"), new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        table.setLookupCellEditor(model.getColumnByFieldName("itemDimension1"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
        table.setLookupCellEditor(model.getColumnByFieldName("itemDimension2"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
        table.setLookupCellEditor(model.getColumnByFieldName("itemDimension3"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
        table.setLookupCellEditor(model.getColumnByFieldName("createdBy"), new EMCGoToMainTableEditor(new EMCStringDocument(), new users()));
        table.setLookupCellEditor(model.getColumnByFieldName("reason"), new EMCGoToMainTableEditor(new EMCStringDocument(), new CostChangeReasonMenu()));
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tablescroll = new emcTablePanelUpdate(table);
        thePanel.add(tablescroll);
        return thePanel;
    }
}
