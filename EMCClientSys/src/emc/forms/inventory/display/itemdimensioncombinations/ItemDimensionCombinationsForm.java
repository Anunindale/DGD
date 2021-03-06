/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemdimensioncombinations;

import emc.app.components.EMCFormComboBox;
import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.constants.systemConstants;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.pop.menuitems.display.PurchaseOrderApprovalGroup;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.table.TableModel;

/**
 *
 * @author riaan
 */
public class ItemDimensionCombinationsForm extends BaseInternalFrame {

    private EMCControlLookupComponent lkpItem;
    private emcJTextField txtItemDesc = new emcJTextField();
    private EMCUserData copyUD;
    //DataSource
    private EMCControlLookupComponentDRM dataRelation;
    List<String> keys;

    public ItemDimensionCombinationsForm(EMCUserData userData) {
        super("Item Dimension Combinations", true, true, true, true, userData);
        copyUD = userData.copyUserDataAndDataList();
        this.setBounds(20, 20, 600, 400);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryItemDimCombinations.html"));

        try {
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimensionCombinationsDS(), userData), userData) {

                @Override
                public void deleteRowCache(int rowIndex) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Row cannot be deleted", copyUD);
                }

                @Override
                public void insertRowCache(int rowIndex, boolean addRowAfter) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Rows cannot be added", copyUD);
                }

                @Override
                public void setUserData(EMCUserData userData) {
                    int dim1Index = -1;
                    int dim2Index = -1;
                    int dim3Index = -1;

                    final emcJTableUpdate table = this.getMainTableComponent();

                    if (table != null) {
                        keys = new ArrayList<String>();

                        Object value = dataRelation.getFieldValueAt(getLastRowAccessed(), "dimension1Id");

                        if ((value == null) || (!value.equals(systemConstants.emcNA()))) {
                            keys.add("dimension1Id");
                            dim1Index = keys.size() - 1;
                        }

                        value = dataRelation.getFieldValueAt(getLastRowAccessed(), "dimension3Id");

                        if ((value == null) || (!value.equals(systemConstants.emcNA()))) {
                            keys.add("dimension3Id");
                            dim3Index = keys.size() - 1;
                        }

                        value = dataRelation.getFieldValueAt(getLastRowAccessed(), "dimension2Id");

                        if ((value == null) || (!value.equals(systemConstants.emcNA()))) {
                            keys.add("dimension2Id");
                            dim2Index = keys.size() - 1;
                        }

                        keys.add("active");
                        keys.add("costPrice");
                        keys.add("sellingPrice");
                        keys.add("showOnWeb");

                        TableModel model = table.getModel();

                        ((emcTableModelUpdate) model).setKeys(keys);
                        ((emcTableModelUpdate) model).fireTableStructureChanged();
                        //this.getMainTableComponent().setModel(model);

                        table.setColumnSizesFromDataTypes();

                        if (dim1Index != -1) {
                            table.setLookupCellEditor(dim1Index, new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
                        }

                        if (dim2Index != -1) {
                            table.setLookupCellEditor(dim2Index, new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
                        }

                        if (dim3Index != -1) {
                            table.setLookupCellEditor(dim3Index, new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
                        }

                    }

                    super.setUserData(userData);
                }
            };

            lkpItem = EMCItemLookupFactory.createItemControllLookup(dataRelation, txtItemDesc, InventoryItemDimensionCombinations.class.getName(), copyUD);


            //query.addAnd("");
            //query.addAnd("companyId", userData.getCompanyId());
            //lkpItem.setFormQuery(query);

            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemId");
            dataRelation.setFormTextId2("active");

        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Dimension Combinations Form", userData);
            }
        }

        setupComponents();
        initFrame();

        dataRelation.setLookup(lkpItem);
    }

    private void setupComponents() {
        txtItemDesc.setEditable(false);

    }

    private EMCControlLookupPanel createControlLookupPanel() {
        keys = new ArrayList<String>();
        keys.add("dimension1Id");
        keys.add("dimension3Id");
        keys.add("dimension2Id");
        keys.add("active");
        keys.add("costPrice");
        keys.add("sellingPrice");
        keys.add("showOnWeb");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex >= keys.size() - 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        emcJTableUpdate toptable = new emcJTableUpdate(m);

        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        dataRelation.setTablePanel(topscroll);

        return new EMCControlLookupPanel("Item Dimension Combinations", dataRelation.getColumnName("itemPrimaryReference"), lkpItem, "Description", txtItemDesc, topscroll, "Overview");
    }

    private emcJPanel orderTab() {
        emcJTextField txtMultOrderQty = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "multOrderQty"));
        emcJTextField txtMinOrderQty = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "minOrderQty"));
        emcJTextField txtMaxOrderQty = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "maxOrderQty"));
        emcJTextField txtSafetyStock = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "safetyStock"));
        emcJTextField txtEconomicOrderQty = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "economicOrderQuantity"));

        Component[][] comp = {
            {new emcJLabel(dataRelation.getColumnName("minOrderQty")), txtMinOrderQty},
            {new emcJLabel(dataRelation.getColumnName("multOrderQty")), txtMultOrderQty},
            {new emcJLabel(dataRelation.getColumnName("maxOrderQty")), txtMaxOrderQty},
            {new emcJLabel(dataRelation.getColumnName("safetyStock")), txtSafetyStock},
            {new emcJLabel(dataRelation.getColumnName("economicOrderQuantity")), txtEconomicOrderQty}
        };
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);

        thePanel.setBorder(BorderFactory.createTitledBorder("Order"));

        return thePanel;
    }

    private emcJPanel purchaseTab() {
        emcJTextField txtPurchasePrice = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "purchasePrice"));
        emcJTextField txtLastPurchasePrice = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "lastPurchasePrice"));
        emcJTextField txtPurchaseLeadTime = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "purchaseLeadTime"));
        EMCDatePickerFormComponent dpLastPurchasePrice = new EMCDatePickerFormComponent(dataRelation, "lastPurchaseDate");
        dpLastPurchasePrice.setEnabled(false);
        txtLastPurchasePrice.setEditable(false);
        EMCFormComboBox cbStopPurchase = new EMCFormComboBox(InventoryStopPurchasingType.values(), dataRelation, "stopPurchase");
        EMCLookupFormComponent lkpOverseePurchaseOrderGroup = new EMCLookupFormComponent(new PurchaseOrderApprovalGroup(), dataRelation, "overseePurchaseGroup");
        lkpOverseePurchaseOrderGroup.setPopup(new EMCLookupPopup(new POPPurchaseOrderApprovalGroups(), "purchaseOrderApprovalGroupId", copyUD));






        Component[][] comp = {{new emcJLabel("Purchase Price"), txtPurchasePrice},
            {new emcJLabel("Last Purchase Price"), txtLastPurchasePrice},
            {new emcJLabel("Last Purchase Date"), dpLastPurchasePrice},
            {new emcJLabel(dataRelation.getColumnName("purchaseLeadTime")), txtPurchaseLeadTime},
            {new emcJLabel("Stop Purchase"), cbStopPurchase, new emcJLabel("Oversee Group"), lkpOverseePurchaseOrderGroup}
        };
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Purchase Price"));
        return thePanel;
    }

    private emcJPanel costTab() {
        emcJTextField txtCurrentCost = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "costPrice"));
        EMCDatePickerFormComponent dpCurCostDate = new EMCDatePickerFormComponent(dataRelation, "currentCostDate");
        emcJTextField txtPrevCost = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "prevCostPrice"));
        EMCDatePickerFormComponent dpPrevCostDate = new EMCDatePickerFormComponent(dataRelation, "prevCostDate");
        Component[][] comp = {{new emcJLabel("Current Cost"), txtCurrentCost},
            {new emcJLabel("Cost Date"), dpCurCostDate},
            {new emcJLabel("Previous Cost"), txtPrevCost},
            {new emcJLabel("Previous Cost Date"), dpPrevCostDate}
        };
        txtPrevCost.setEditable(false);
        dpPrevCostDate.setEnabled(false);
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Costing"));
        return thePanel;
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();

        EMCControlLookupPanel combinationsPanel = createControlLookupPanel();

        emcJTabbedPane tabs = combinationsPanel.getTabs();
        tabs.add("Costing", costTab());
        tabs.add("Purchasing", purchaseTab());
        tabs.add("Order", orderTab());
        tabs.add("Production", productionTab());

        tabbedPanetop.add("Item Dimension Combinations", combinationsPanel);

        this.add(tabbedPanetop, BorderLayout.CENTER);
    }

    private Component productionTab() {
        emcJTextField txtLeadTime = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "productionLeadTime"));
        Component[][] comp = {{new emcJLabel("Prod L/Time (Days)"), txtLeadTime}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Production");
    }
}
