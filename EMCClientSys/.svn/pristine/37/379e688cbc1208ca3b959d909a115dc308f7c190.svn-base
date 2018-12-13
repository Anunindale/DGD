/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.parameters;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.tables.EMCFormFieldsMapComboBox;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.menus.pop.menuitems.display.PurchaseOrderApprovalGroup;
import java.awt.Component;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;

/**
 *
 * @author wikus
 */
public class POPParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;
    private emcYesNoComponent overReceive;
    private emcYesNoComponent allowBlanketOrderOverRelease;
    private emcJTextField overPer;
    private emcJTextField blanketOrderOverReleasePercentage;
    private emcYesNoComponent displaySupplierItemRef;
    private emcYesNoComponent serialMoreThanOne;
    private emcYesNoComponent printReceivePrice;
    private emcYesNoComponent printReturnPrice;
    private emcJLabel lblAllowBOMItemsPurchase;
    private emcYesNoComponent ysnAllBOMItemsPurchase;
    private EMCLookupFormComponent lkpItemGroup;
    private emcYesNoComponent ysnUseLowestPrice;
    private emcYesNoComponent ysnAllowStandardPrice;
    private EMCUserData cpUserData;

    public POPParametersForm(EMCUserData userData) {
        super("Purchasing Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 500);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(), new emc.entity.pop.POPParameters(), userData), userData);
            this.setDataManager(dataRelation);
        } catch (Exception e) {
        }
        cpUserData = userData.copyUserData();
        initFrame();
        dataRelation.setDummyForm(this);
        dataRelation.doRelation(0);
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Purchase Orders", purchasePane());
        tabbed.add("Planning", planningPane());
        tabbed.add("Contact", createContactPanel());
        this.add(tabbed);
    }

    private emcJPanel purchasePane() {
        overPer = new emcJTextField();
        overPer.setDocument(new EMCDoubleFormDocument(dataRelation, "overPercentage"));
        blanketOrderOverReleasePercentage = new emcJTextField();
        blanketOrderOverReleasePercentage.setDocument(new EMCDoubleFormDocument(dataRelation, "blanketOrderOverReleasePercentage"));
        overReceive = new emcYesNoComponent(dataRelation, "allowOverReceive") {
            @Override
            protected void selectedItemChanged() {
                if (this.getSelectedIndex() == 0) {
                    overPer.setEnabled(true);
                } else {
                    overPer.setEnabled(false);
                }
                super.selectedItemChanged();
            }
        };
        allowBlanketOrderOverRelease = new emcYesNoComponent(dataRelation, "allowBlanketOrderOverRelease") {
            @Override
            protected void selectedItemChanged() {
                if (this.getSelectedIndex() == 0) {
                    blanketOrderOverReleasePercentage.setEnabled(true);
                } else {
                    blanketOrderOverReleasePercentage.setEnabled(false);
                }
                super.selectedItemChanged();
            }
        };

        displaySupplierItemRef = new emcYesNoComponent(dataRelation, "displaySupplierItemRef");
        serialMoreThanOne = new emcYesNoComponent(dataRelation, "serialMoreThanOne");

        lblAllowBOMItemsPurchase = new emcJLabel(dataRelation.getFieldEmcLabel("allowBOMItemsPurchase"));
        ysnAllBOMItemsPurchase = new emcYesNoComponent(dataRelation, "allowBOMItemsPurchase");

        printReceivePrice = new emcYesNoComponent(dataRelation, "priceReceivedReport");
        printReturnPrice = new emcYesNoComponent(dataRelation, "priceReturnedReport");
        lkpItemGroup = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.ItemGroups(), dataRelation, "itemGrpToReadColMast", "itemGroup");
        EMCLookupPopup itemGroup = new EMCLookupPopup(new emc.entity.inventory.InventoryItemGroup(), "itemGroup", cpUserData);
        lkpItemGroup.setPopup(itemGroup);
        emcYesNoComponent printDetailedItemDesc = new emcYesNoComponent(dataRelation, "printDetailedItemDescription");

        ysnAllowStandardPrice = new emcYesNoComponent(dataRelation, "allowStandardPrice");
        ysnUseLowestPrice = new emcYesNoComponent(dataRelation, "useLowestPrice");

        EMCLookupFormComponent lkpWarehouse = new EMCLookupFormComponent(new Warehouse(), dataRelation, "mtoWarehouse");
        lkpWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", dataRelation.getUserData()));

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
        query.addAnd("mto", true);
        lkpWarehouse.setTheQuery(query);

        Component[][] components = {{new emcJLabel("Allow Over Receive"), overReceive},
                                    {new emcJLabel("Over Percentage"), overPer},
                                    {new emcJLabel("Allow Blanket Order Over Release"), allowBlanketOrderOverRelease},
                                    {new emcJLabel("Over Release Percentage"), blanketOrderOverReleasePercentage},
                                    {new emcJLabel("More Than One Item Per Serial No."), serialMoreThanOne},
                                    {lblAllowBOMItemsPurchase, ysnAllBOMItemsPurchase},
                                    {new emcJLabel("Print Supplier Ref First"), displaySupplierItemRef},
                                    {new emcJLabel("Print Received Prices"), printReceivePrice},
                                    {new emcJLabel("Print Returned Prices"), printReturnPrice},
                                    {new emcJLabel("Print Colour Master Detail for Group"), lkpItemGroup},
                                    {new emcJLabel("Print Detailed Description for Item"), printDetailedItemDesc},
                                    {new emcJLabel(dataRelation.getColumnName("useLowestPrice")), ysnUseLowestPrice},
                                    {new emcJLabel(dataRelation.getColumnName("allowStandardPrice")), ysnAllowStandardPrice},
                                    {new emcJLabel("MTO Warehouse"), lkpWarehouse}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Purchase Orders"));
        return thePanel;
    }

    private Component planningPane() {
        EMCFormFieldsMapComboBox itemReleaseField1 = new EMCFormFieldsMapComboBox(dataRelation, "itemField1ForPlannedRelease");
        itemReleaseField1.updateFields(InventoryItemMaster.class.getName(), cpUserData);
        itemReleaseField1.addEmptyField();
        itemReleaseField1.setSelectedItem(" ");
        EMCFormFieldsMapComboBox itemReleaseField2 = new EMCFormFieldsMapComboBox(dataRelation, "itemField2ForPlannedRelease");
        itemReleaseField2.updateFields(InventoryItemMaster.class.getName(), cpUserData);
        itemReleaseField2.addEmptyField();
        itemReleaseField2.setSelectedItem(" ");
        EMCLookupFormComponent lkpApprovalGroup = new EMCLookupFormComponent(new PurchaseOrderApprovalGroup(), dataRelation, "defaultApprovalGroup");
        lkpApprovalGroup.setPopup(new EMCLookupPopup(new POPPurchaseOrderApprovalGroups(), "purchaseOrderApprovalGroupId", cpUserData));
        Component[][] comp = {{new emcJLabel("Release Item Field 1"), itemReleaseField1},
                              {new emcJLabel("Release Item Field 2"), itemReleaseField2},
                              {new emcJLabel("Default Approval Group"), lkpApprovalGroup}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Planning");
    }

    /**
     * Creates contact panel.
     */
    private emcJPanel createContactPanel() {
        Component[][] components = new Component[][]{
            {new emcJLabel(dataRelation.getColumnName("faxNumber")), new emcJTextField(new EMCStringFormDocument(dataRelation, "faxNumber"))}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }
}
