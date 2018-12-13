/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.mill;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.pop.POPDeliveryTerms;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.datasource.POPPurchaseOrderLinesDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.pop.menuitems.display.DeliveryTerms;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class POPMillPurchaseOrders extends BaseInternalFrame {

    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData linesUD;
    private emcDataRelationManagerUpdate linesDRM;

    public POPMillPurchaseOrders(EMCUserData userData) {
        super("Mill Purchase", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.POP.getId(), new POPPurchaseOrderMaster(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("purchaseOrderId");
        masterDRM.setFormTextId2("supplier");
        //Linese
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.POP.getId(), new POPPurchaseOrderLinesDS(), linesUD), linesUD) {

            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                Logger.getLogger("emc").log(Level.SEVERE, "You may not insert lines on this form.", getUserData());
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "You may not delete lines on this form.", getUserData());
            }
        };
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("itemReference");
        linesDRM.setFormTextId2("itemDimension3");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("purchaseOrderId");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("purchaseOrderId");
        masterDRM.refreshData();
        //Form
        initFrame();
    }

    private void initFrame() {
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(260);
        this.setContentPane(topBottomSplit);
    }

    private emcJPanel masterPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", masterTab1());
        tabbed.add("Additional", masterTab2());
        tabbed.add("Delivery", masterTab3());
        tabbed.add("Comments", masterTab4());
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(tabbed);
        return thePanel;
    }

    private Component masterTab1() {
        emcJTextField txtCustomer = new emcJTextField(new EMCStringFormDocument(masterDRM, "companyId"));
        txtCustomer.setEditable(false);
        emcJTextField txtCustomerOrderNo = new emcJTextField(new EMCStringFormDocument(masterDRM, "supplierOrderNumber"));
        EMCDatePickerFormComponent dpDate = new EMCDatePickerFormComponent(masterDRM, "requestedDeliveryDate");
        dpDate.setEnabled(false);
        emcJTextField txtAgentCode = new emcJTextField(new EMCStringFormDocument(masterDRM, "agentCode"));
        emcJTextField txtDeliveryAddress = new emcJTextField(new EMCStringFormDocument(masterDRM, "addressLine1"));
        txtDeliveryAddress.setEditable(false);
        emcJTextField txtItem = new emcJTextField(new EMCStringFormDocument(linesDRM, "itemReference"));
        txtItem.setEditable(false);
        emcJTextField txtDescription = new emcJTextField(new EMCStringFormDocument(linesDRM, "itemDescription"));
        txtDescription.setEditable(false);
        emcJTextField txtDesignNo = new emcJTextField(new EMCStringFormDocument(linesDRM, "designNo"));
        txtDesignNo.setEditable(false);
        emcJTextField txtRetailer = new emcJTextField(new EMCStringFormDocument(masterDRM, "retailer"));
        emcJTextField txtConsignment = new emcJTextField(new EMCStringFormDocument(masterDRM, "consignment"));
        EMCLookupFormComponent lkpTerms = new EMCLookupFormComponent(new DeliveryTerms(), masterDRM, "deliveryTerms");
        lkpTerms.setPopup(new EMCLookupPopup(new POPDeliveryTerms(), "deliveryTermsId", masterUD));
        emcJTextField txtEndUser = new emcJTextField(new EMCStringFormDocument(masterDRM, "endUser"));
        emcJTextField txtBlanketOrder = new emcJTextField(new EMCStringFormDocument(masterDRM, "blanketOrderRef"));
        Component[][] comp = {{new emcJLabel("Customer"), txtCustomer, new emcJLabel("Cust O/N"), txtCustomerOrderNo},
                              {new emcJLabel("Date"), dpDate, new emcJLabel("Agent Code"), txtAgentCode},
                              {new emcJLabel("Delivery Address"), txtDeliveryAddress},
                              {new emcJLabel("Item"), txtItem, new emcJLabel("Description"), txtDescription},
                              {new emcJLabel("Design No"), txtDesignNo, new emcJLabel("Retailer"), txtRetailer},
                              {new emcJLabel("Consignment"), txtConsignment, new emcJLabel("End User"), txtEndUser},
                              {new emcJLabel("Reservation/Proforma No"), txtBlanketOrder, new emcJLabel("Terms"), lkpTerms}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Overview");
    }

    private Component masterTab2() {
        MillBooleanComboBox cbSoftex = new MillBooleanComboBox(masterDRM, "softex");
        emcJTextField txtSoftex = new emcJTextField(new EMCStringFormDocument(masterDRM, "otherSoftex"));
        MillBooleanComboBox cbExistingDesign = new MillBooleanComboBox(masterDRM, "existingDesign");
        MillBooleanComboBox cbBulked = new MillBooleanComboBox(masterDRM, "designBulkOrder");
        emcJTextField txtWidthPlain = new emcJTextField(new EMCStringFormDocument(masterDRM, "widthPlain"));
        emcJTextField txtWidthPrinted = new emcJTextField(new EMCStringFormDocument(masterDRM, "widthPrint"));
        emcJTextField txtWeightPlain = new emcJTextField(new EMCStringFormDocument(masterDRM, "weightPlain"));
        emcJTextField txtWeightPrinted = new emcJTextField(new EMCStringFormDocument(masterDRM, "weightPrint"));
        MillBooleanComboBox cbLabDye = new MillBooleanComboBox(masterDRM, "labDyes");
        MillBooleanComboBox cbStrikeOff = new MillBooleanComboBox(masterDRM, "strikeOff");
        emcJTextField txtCorrespondence = new emcJTextField(new EMCStringFormDocument(masterDRM, "correspondenceReference"));
        MillBooleanComboBox cbColourBlocked = new MillBooleanComboBox(masterDRM, "colourBlocked");
        emcJTextField txtPreviousOrder = new emcJTextField(new EMCStringFormDocument(masterDRM, "previousOrder"));
        Component[][] comp = {{new emcJLabel("Existing Design"), cbExistingDesign, new emcJLabel("Width Plain"), txtWidthPlain},
                              {new emcJLabel("Softex"), cbSoftex, new emcJLabel("Weight Plain"), txtWeightPlain},
                              {new emcJLabel("Other"), txtSoftex, new emcJLabel("Width Printed"), txtWidthPrinted},
                              {new emcJLabel("Lab Dyes Required"), cbLabDye, new emcJLabel("Weight Printed"), txtWeightPrinted},
                              {new emcJLabel("Strike Offs Required"), cbStrikeOff, new emcJLabel("Correspondence Reference"), txtCorrespondence},
                              {new emcJLabel("Colour Blocked"), cbColourBlocked, new emcJLabel("Per Previous O/No"), txtPreviousOrder},
                              {new emcJLabel("Bulked Before"), cbBulked}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Additional");
    }

    private Component masterTab3() {
        emcJTextField txtDeliverOrder = new emcJTextField(new EMCStringFormDocument(masterDRM, "deliverWithOrder"));
        emcJTextField txtMatchOrder = new emcJTextField(new EMCStringFormDocument(masterDRM, "matchOrder"));
        MillBooleanComboBox cbReplacement = new MillBooleanComboBox(masterDRM, "replacementOrder");
        emcJTextField txtReplacement = new emcJTextField(new EMCStringFormDocument(masterDRM, "replacementReason"));
        MillBooleanComboBox cbPrintAfterDelivery = new MillBooleanComboBox(masterDRM, "printAfterDelivery");
        emcJTextField txtPrintAfterDelivery = new emcJTextField(new EMCStringFormDocument(masterDRM, "typeOfPrint"));
        MillBooleanComboBox cbProcessAfterDelivery = new MillBooleanComboBox(masterDRM, "processAfterDelivery");
        emcJTextField txtProcessAfterDelivery = new emcJTextField(new EMCStringFormDocument(masterDRM, "typeOfProcess"));
        MillBooleanComboBox cbCuttingReceived = new MillBooleanComboBox(masterDRM, "cuttingReceived");
        MillBooleanComboBox cbEXStock = new MillBooleanComboBox(masterDRM, "EXStock");
        Component[][] comp = {{new emcJLabel("Deliver with Order"), txtDeliverOrder, new emcJLabel("Print After Delivery"), cbPrintAfterDelivery},
                              {new emcJLabel("Match with Order"), txtMatchOrder, new emcJLabel("Type Of Print"), txtPrintAfterDelivery},
                              {new emcJLabel("Replacement Order"), cbReplacement, new emcJLabel("Process After Delivery"), cbProcessAfterDelivery},
                              {new emcJLabel("Replacement Reason"), txtReplacement, new emcJLabel("Type Of Process"), txtProcessAfterDelivery},
                              {new emcJLabel("EX Stock"), cbEXStock},
                              {new emcJLabel("Cutting Received"), cbCuttingReceived}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Delivery");
    }

    private Component masterTab4() {
        emcJTextArea txaComments = new emcJTextArea(new EMCStringFormDocument(masterDRM, "comments"));
        emcJTextArea txaLabInfo = new emcJTextArea(new EMCStringFormDocument(masterDRM, "additionalLabInfo"));
        Component[][] components = {{txaComments, new emcJLabel("Special Conditions")},
                                    {txaLabInfo, new emcJLabel("Laboratory Information")}};
        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    private emcJPanel linesPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", linesTablePane());
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(tabbed);
        return thePanel;
    }

    private emcTablePanelUpdate linesTablePane() {
        List keys = new ArrayList();
        keys.add("itemDimension3");
        keys.add("colourCatagory");
        keys.add("fabricColourNumber");
        keys.add("designCode");
        keys.add("printColour");
        keys.add("printColourNumber");
        keys.add("fabricPrice");
        keys.add("printPrice");
        keys.add("itemPrice");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLinesLookups(table);
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLinesLookups(emcJTableUpdate table) {
        table.setColumnCellEditor("itemDimension3", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
        table.setColumnEditable("itemDimension3", false);
        table.setColumnEditable("colourCatagory", false);
        table.setColumnEditable("fabricColourNumber", false);
        table.setColumnEditable("printColourNumber", false);
        table.setColumnEditable("itemPrice", false);
    }
}
