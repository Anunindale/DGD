/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.basket;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BasePostalCodes;
import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.datasource.DebtorsBasketLinesDS;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.basket.BasketStatus;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.debtors.DebtorsJournalsMenu;
import emc.menus.debtors.menuitems.display.DebtorsBasketMI;
import emc.menus.debtors.menuitems.display.DebtorsCustomerInvoices;
import emc.menus.debtors.menuitems.display.DebtorsJournals;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSplitPane;

/**
 *
 * @author stuart
 */
public class DebtorsBasketForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drmMaster;
    private emcDataRelationManagerUpdate drmLines;

    public DebtorsBasketForm(EMCUserData userData) {
        super("Basket", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 650);
        EMCUserData linesUD = userData.copyUserData();
        drmMaster = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new DebtorsBasketMaster(), userData), userData);
        drmLines = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new DebtorsBasketLinesDS(), linesUD), linesUD);
        drmMaster.setTheForm(this);
        drmLines.setTheForm(this);
        this.setDataManager(drmMaster);
        drmMaster.setHeaderColumnID("basketId");
        drmMaster.setFormTextId1("basketId");
        drmMaster.setFormTextId2("customerId");
        drmMaster.setLinesTable(drmLines);
        drmLines.setRelationColumnToHeader("basketId");
        drmLines.setFormTextId1("basketId");
        drmLines.setFormTextId2("lineNumber");
        drmLines.setHeaderTable(drmMaster);
        this.initFrame();

    }

    private void initFrame() {
        emcJTabbedPane masterTabs = createMasterTabs();
        masterTabs.setRelationManager(drmMaster);
        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new BorderLayout());
        pnlMaster.add(masterTabs, BorderLayout.CENTER);
        pnlMaster.add(createMasterButtons(), BorderLayout.EAST);

        emcJTabbedPane linesTab = createLinesTabs();
        linesTab.setRelationManager(drmLines);
        emcJPanel pnlLines = new emcJPanel(new BorderLayout());
        pnlLines.add(linesTab, BorderLayout.CENTER);
        pnlLines.add(createLinesButtons(), BorderLayout.EAST);

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlMaster, pnlLines);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(250);

        this.add(topBottomSplit, BorderLayout.CENTER);
    }

    private Component createMasterOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("basketId");
        keys.add("sessionId");
        keys.add("customerId");
        keys.add("journalId");
        keys.add("invoiceId");
        keys.add("status");
        emcTableModelUpdate model = new emcTableModelUpdate(drmMaster, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", getUserData()));
        table.setLookupToColumn("customerId", lkpCustomer);
        
       

        EMCLookupJTableComponent lkpInvoiceId = new EMCLookupJTableComponent(new DebtorsCustomerInvoices());
        lkpInvoiceId.setPopup(new EMCLookupPopup(new DebtorsCustomerInvoiceMaster(), "invoiceId", getUserData()));
        table.setLookupToColumn("invoiceId", lkpInvoiceId);
        
        EMCLookupJTableComponent lkpJournaId = new EMCLookupJTableComponent(new DebtorsJournals());
        lkpJournaId.setPopup(new EMCLookupPopup(new DebtorsJournalMaster(), "journalNumber", getUserData()));
        table.setLookupToColumn("journalId", lkpJournaId);

        drmMaster.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drmMaster.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

    private Component createMasterButtons() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new emcJButton("Invoice Basket") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        });

        emcJButton btnRelease = new emcJButton("Release Trec") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                String status = (String) drmMaster.getLastFieldValueAt("status");

                if (!BasketStatus.RELEASED.toString().equalsIgnoreCase(status)) {

                    EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.RELEASE_TREC);

                    List toSend = new ArrayList();
                    toSend.add(drmMaster.getLastFieldValueAt("basketId"));
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean) {
                        boolean updated = (boolean) toSend.get(1);
                        if (updated) {
                            Logger.getLogger("emc").log(Level.INFO, "Trec Card(s) successfully released");
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to release Trec Card(s)");
                        }
                        drmMaster.refreshRecord(-1);

                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Trec Card(s) already released");
                }
            }
        };

        buttons.add(btnRelease);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    private Component createLinesButtons() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new emcJButton("Re Release") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
               String status = (String) drmMaster.getLastFieldValueAt("status");
               Long record = (Long) drmLines.getLastFieldValueAt("recordID");
                if (BasketStatus.RELEASED.toString().equalsIgnoreCase(status)) {

                    EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.REPRINT_DEBTORSBASKETLINE);

                    List toSend = new ArrayList();
                    toSend.add(record);
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean) {
                        boolean updated = (boolean) toSend.get(1);
                        if (updated) {
                            Logger.getLogger("emc").log(Level.INFO, "Trec Card successfully released for Reprint");
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to release Trec Card for reprint, Please check Print Qty");
                        }
                        drmLines.refreshRecord(-1);

                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Trec Card has not been released yet");
                }
            }
        });


        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
     private emcJPanel addressTab() {
        //Physical
        emcJTextField txtPhysicalAddress1 = new emcJTextField(new EMCStringFormDocument(drmMaster, "physicalAddresLine1"));
        emcJTextField txtPhysicalAddress2 = new emcJTextField(new EMCStringFormDocument(drmMaster, "physicalAddresLine2"));
        emcJTextField txtPhysicalAddress3 = new emcJTextField(new EMCStringFormDocument(drmMaster, "physicalAddresLine3"));
        emcJTextField txtPhysicalAddress4 = new emcJTextField(new EMCStringFormDocument(drmMaster, "physicalAddresLine4"));
        emcJTextField txtPhysicalAddress5 = new emcJTextField(new EMCStringFormDocument(drmMaster, "physicalAddresLine5"));
        EMCLookupFormComponent lkpPhysicalPostalCode = new EMCLookupFormComponent(new PostalCodes(), drmMaster, "physicalPostalCode");
        lkpPhysicalPostalCode.setPopup(new EMCLookupPopup(new BasePostalCodes(), "code", drmMaster.getUserData()));
        Component[][] physicalComp = {{new emcJLabel("Physical Address Line 1"), txtPhysicalAddress1},
            {new emcJLabel("Physical Address Line 2"), txtPhysicalAddress2},
            {new emcJLabel("Physical Address Line 3"), txtPhysicalAddress3},
            {new emcJLabel("Physical Address Line 4"), txtPhysicalAddress4},
            {new emcJLabel("Physical Address Line 5"), txtPhysicalAddress5},
            {new emcJLabel("Physical Postal Code"), lkpPhysicalPostalCode}};
        
       
        return emcSetGridBagConstraints.createSimplePanel(physicalComp, GridBagConstraints.NONE, true, "Delivery Address");
    }

    private emcJTabbedPane createMasterTabs() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();
        masterTabs.add("Overview", createMasterOverviewTab());
        masterTabs.add("Address", addressTab());
        return masterTabs;

    }

    private emcJTabbedPane createLinesTabs() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();
        linesTabs.add("Overview", createLinesOverviewTab());
        linesTabs.add("Additional Info",linesAdditionalInfoPane());
        return linesTabs;
    }

    private Component createLinesOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("lineNumber");
        keys.add("itemId");
        keys.add("unNumber");
        keys.add("printQty");
        keys.add("printExtraQty");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantity");
        keys.add("price");
        keys.add("vat");
        keys.add("totalPrice");

        emcTableModelUpdate model = new emcTableModelUpdate(drmLines, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        EMCLookupJTableComponent lkpItem = new EMCLookupJTableComponent(new ItemMaster());
        lkpItem.setPopup(new EMCLookupPopup(new InventoryItemMaster(), "itemId", drmLines.getUserData()));
        table.setLookupToColumn("itemId", lkpItem);


        drmLines.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drmLines.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }
     private emcJPanel linesAdditionalInfoPane() {
        emcJTextField txtPlacardUNNUmber = new emcJTextField(new EMCStringFormDocument(drmLines, "placardUNNumber"));
        emcJTextField txtOperatorTelNo = new emcJTextField(new EMCStringFormDocument(drmLines, "operatorTlNo"));
        emcJTextField txtSpecialistTelNo = new emcJTextField(new EMCStringFormDocument(drmLines, "specialistTelNo"));
       
        
        Component[][] comp = {{new emcJLabel("Placard UN Number"), txtPlacardUNNUmber}, 
            {new emcJLabel("Operator Tel Number"), txtOperatorTelNo},
            {new emcJLabel("Specialist Tel Number"), txtSpecialistTelNo}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Placard Info");
    }
}
