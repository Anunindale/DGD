/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricearangement.enquiries;

import emc.app.components.documents.EMCDoubleDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.inventory.lookups.itemreference.ItemReferencePopup;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class SOPPriceArangementEnquiryForm extends BaseInternalFrame {

    private EMCUserData userData;
    private EMCLookup lkpCustomer;
    private EMCLookup lkpItem;
    private EMCLookup lkpDimension1;
    private EMCLookup lkpDimension2;
    private EMCLookup lkpDimension3;
    private emcJTextField txtQuantity;
    private EMCDatePicker dpAtDate;

    public SOPPriceArangementEnquiryForm(EMCUserData userData) {
        super("Price List Enquiry", true, true, true, true, userData);
        this.setBounds(20, 20, 380, 300);
        this.userData = userData.copyUserData();
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        lkpCustomer = new EMCLookup(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        lkpItem = new EMCLookup(new ItemMaster()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                setDimensionQueries((String) value);
            }
        };
        lkpItem.setPopup(new ItemReferencePopup("itemReference", userData));

        lkpDimension1 = new EMCLookup(new Dimension1());
        lkpDimension1.setPopup(new EMCLookupPopup(new InventoryItemDimension1SetupDS(), "dimensionId", userData));
        EMCQuery dimension1Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class);
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimension1Id");
        dimension1Query.addTableAnd(InventoryDimension1.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimensionId");
        dimension1Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension1Query.addGroupBy("dimensionId");
        dimension1Query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        lkpDimension1.setTheQuery(dimension1Query);

        lkpDimension2 = new EMCLookup(new Dimension2());
        lkpDimension2.setPopup(new EMCLookupPopup(new InventoryItemDimension2SetupDS(), "dimensionId", userData));
        EMCQuery dimension2Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension2Setup.class);
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimension2Id");
        dimension2Query.addTableAnd(InventoryDimension2.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimensionId");
        dimension2Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension2Query.addGroupBy("dimensionId");
        dimension2Query.addOrderBy("sortCode", InventoryDimension2.class.getName());
        lkpDimension2.setTheQuery(dimension2Query);

        lkpDimension3 = new EMCLookup(new Dimension3());
        lkpDimension3.setPopup(new EMCLookupPopup(new InventoryItemDimension3SetupDS(), "dimensionId", userData));
        EMCQuery dimension3Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension3Setup.class);
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimension3Id");
        dimension3Query.addTableAnd(InventoryDimension3.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimensionId");
        dimension3Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension3Query.addGroupBy("dimensionId");
        dimension3Query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        lkpDimension3.setTheQuery(dimension3Query);

        txtQuantity = new emcJTextField(new EMCDoubleDocument());

        dpAtDate = new EMCDatePicker();

        Component[][] comp = {{new emcJLabel("Customer"), lkpCustomer},
            {new emcJLabel("Item"), lkpItem},
            {new emcJLabel("Config"), lkpDimension1},
            {new emcJLabel("Colour"), lkpDimension3},
            {new emcJLabel("Size"), lkpDimension2},
            {new emcJLabel("Quantity"), txtQuantity},
            {new emcJLabel("At Date"), dpAtDate}};
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Price List", emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true));
        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnGetPrice = new emcJButton("Get Price") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.GET_PRICE.toString());
                List toSend = new ArrayList();
                toSend.add(Functions.checkBlank(lkpCustomer.getValue()) ? "" : lkpCustomer.getValue());
                toSend.add(Functions.checkBlank(lkpItem.getValue()) ? "" : lkpItem.getValue());
                toSend.add(Functions.checkBlank(lkpDimension1.getValue()) ? "" : lkpDimension1.getValue());
                toSend.add(Functions.checkBlank(lkpDimension2.getValue()) ? "" : lkpDimension2.getValue());
                toSend.add(Functions.checkBlank(lkpDimension3.getValue()) ? "" : lkpDimension3.getValue());
                toSend.add(Functions.checkBlank(dpAtDate.getDate()) ? Functions.nowDate() : dpAtDate.getDate());
                toSend.add(new BigDecimal(Functions.checkBlank(txtQuantity.getText()) ? "0" : txtQuantity.getText()));
                BigDecimal price = (BigDecimal) EMCWSManager.executeGenericWS(cmd, toSend, userData).get((1));
                Logger.getLogger("emc").log(Level.INFO, "Item Price: " + new DecimalFormat("###########0.00").format(price.doubleValue()), userData);
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnGetPrice);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private void setDimensionQueries(String item) {
        if (!Functions.checkBlank(item)) {
            if (lkpDimension1 != null) {
                EMCQuery dimension1Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class);
                dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
                dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimension1Id");
                dimension1Query.addTableAnd(InventoryDimension1.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimensionId");
                dimension1Query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
                dimension1Query.addTableAnd(InventoryReference.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                dimension1Query.addAnd("reference", item, InventoryReference.class.getName());
                dimension1Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
                dimension1Query.addGroupBy("dimensionId");
                dimension1Query.addOrderBy("sortCode", InventoryDimension1.class.getName());
                lkpDimension1.setTheQuery(dimension1Query);
            }

            if (lkpDimension2 != null) {
                EMCQuery dimension2Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension2Setup.class);
                dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
                dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimension2Id");
                dimension2Query.addTableAnd(InventoryDimension2.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimensionId");
                dimension2Query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
                dimension2Query.addTableAnd(InventoryReference.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                dimension2Query.addAnd("reference", item, InventoryReference.class.getName());
                dimension2Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
                dimension2Query.addGroupBy("dimensionId");
                dimension2Query.addOrderBy("sortCode", InventoryDimension2.class.getName());
                lkpDimension2.setTheQuery(dimension2Query);
            }

            if (lkpDimension3 != null) {
                EMCQuery dimension3Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension3Setup.class);
                dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
                dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimension3Id");
                dimension3Query.addTableAnd(InventoryDimension3.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimensionId");
                dimension3Query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
                dimension3Query.addTableAnd(InventoryReference.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                dimension3Query.addAnd("reference", item, InventoryReference.class.getName());
                dimension3Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
                dimension3Query.addGroupBy("dimensionId");
                dimension3Query.addOrderBy("sortCode", InventoryDimension3.class.getName());
                lkpDimension3.setTheQuery(dimension3Query);
            }
        }
    }
}
