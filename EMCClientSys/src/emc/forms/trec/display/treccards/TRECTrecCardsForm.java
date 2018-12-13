/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.DataRelationManagerInterface;
import emc.app.components.emctable.editors.EMCComboBoxCellEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.lookup.popup.EMCMultiValuePopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.sop.SOPCustomers;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECColours;
import emc.entity.trec.TRECCustomerChemicals;
import emc.entity.trec.TRECForms;
import emc.entity.trec.TRECOdours;
import emc.entity.trec.TRECPreferredShipName;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECPackingGroupEnum;
import emc.forms.trec.display.treccards.resources.BackupTRECButton;
import emc.forms.trec.display.treccards.resources.PrintTRECButton;
import emc.forms.trec.display.treccards.resources.PrintTRECProducedCardTemplateButton;
import emc.forms.trec.display.treccards.resources.TRECLRM;
import emc.forms.trec.display.treccards.resources.TRECMasterDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.developertools.trec.TRECChemicalsMenu;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.menus.trec.menuitems.display.TRECColoursMenu;
import emc.menus.trec.menuitems.display.TRECEditTRECMI;
import emc.menus.trec.menuitems.display.TRECFormsMenu;
import emc.menus.trec.menuitems.display.TRECOdoursMenu;
import emc.menus.trec.menuitems.display.TRECPrefShipNameMI;
import emc.methods.base.ServerBaseMethods;
import emc.methods.debtors.ServerDebtorsMethods;
import emc.methods.trec.ServerTRECMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import org.apache.xpath.functions.Function;

/**
 *
 * @author wikus
 */
public class TRECTrecCardsForm extends BaseInternalFrame {

    private TRECMasterDRM masterDRM;
    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate linesDRM;
    private EMCUserData linesUD;
    private TRECLRM lrm;
    private emcJComboBox cbPackagingGroup = new emcJComboBox();
    EMCLookupJTableComponent lkpPrefShipName;
    EMCQuery query = new EMCQuery();
    private boolean showCustomerID = false;
    //
    private TRECCustomerChemicals chemical;

    public TRECTrecCardsForm(EMCUserData userData) {
        super("Trec", true, true, true, true, userData);
        this.setHelpFile(new emcHelpFile("Trec/TREC.html"));
        this.setBounds(20, 20, 1000, 600);
        //Header
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new TRECMasterDRM(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECTrecCardsMaster(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("masterId");
        masterDRM.setFormTextId2("trecCompanyName");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECTrecCardsLines(), linesUD), linesUD) {
            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                if (lrm != null) {
                    lrm.doRowChanged(linesDRM);
                }

                updatePackingGroups(rowIndex);
            }

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                boolean rowEdited = isRowUpdated();

                super.setFieldValueAt(rowIndex, columnIndex, aValue);

                if (columnIndex.equals("preferredShipName")) {
                    if (lrm != null) {
                        lrm.doRowChanged(linesDRM);
                    }
                    updatePrefShippingName(rowIndex);

                }

                if (columnIndex.equals("unNumber")) {
                    if (lrm != null) {
                        lrm.doRowChanged(linesDRM);
                    }

                    updatePackingGroups(rowIndex);
                }

                if (rowEdited && !isRowUpdated()) {
                    long version = (long) getFieldValueAt(rowIndex, "version");

                    super.setFieldValueAt(rowIndex, "version", -1L);

                    super.setFieldValueAt(rowIndex, "version", version);
                }
            }

            private void updatePrefShippingName(int rowIndex) {
                if (lkpPrefShipName != null) {
                    String unnumber = (String) linesDRM.getFieldValueAt(rowIndex, "unNumber");
                    String shippingName = (String) linesDRM.getFieldValueAt(rowIndex, "unNumber");
                    if (!Functions.checkBlank(unnumber) && !Functions.checkBlank(unnumber)) {
                    }

                }
            }

            private void updatePackingGroups(int rowIndex) {
                if (cbPackagingGroup != null) {
                    cbPackagingGroup.removeAllItems();
                    String unnumber = (String) linesDRM.getFieldValueAt(rowIndex, "unNumber");

                    if (!Functions.checkBlank(unnumber)) {

                        EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.GET_PACKING_GROUP);

                        List toSend = new ArrayList();
                        toSend.add(unnumber);
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof String) {
                            String x = (String) toSend.get(1);
                            if (!Functions.checkBlank(x)) {
                                if (x.contains(";")) {
                                    String[] phraseArray = x.split(";");
                                    cbPackagingGroup.removeAllItems();

                                    for (String phrase : phraseArray) {
                                        cbPackagingGroup.addItem(phrase);
                                    }
                                } else {
                                    cbPackagingGroup.addItem(x);
                                }
                            }

                        }
                    }

                }
            }

            @Override
            public void updatePersist(int rowIndex) {
                super.updatePersist(rowIndex);
            }

            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData generatedUD = super.generateRelatedFormUserData(formUserData, Index);

                switch (Index) {
                    case 1: {
                        generatedUD.setUserData(0, null);
                        generatedUD.setUserData(1, getRowCache(getLastRowAccessed()));
                        generatedUD.setUserData(2, chemical);
                        generatedUD.setUserData(3, this);
                    }
                }

                return generatedUD;
            }
        };
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("unNumber");
        linesDRM.setFormTextId2("preparedBy");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("masterId");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("masterId");
        showCustomerId(userData);
        initFrame();
    }

    private void initFrame() {
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(250);
        this.setContentPane(topBottomSplit);
    }

    private boolean showCustomerId(EMCUserData userData) {

        List toSend;
        EMCCommandClass cmd;

        cmd = new EMCCommandClass(ServerBaseMethods.GET_ACTIVE_MODULELIST);

        toSend = new ArrayList();

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

        if (toSend.size() > 1) {
            List<String> moduleList = (List<String>) toSend;
            if (moduleList.size() > 0) {
                if (moduleList.contains("Debtors")) {
                    showCustomerID = true;
                }
            }
        }
        return showCustomerID;
    }

    private emcJPanel masterPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", masterTablePane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(masterButtonPane(), BorderLayout.EAST);
        return thePanel;
    }

    private emcTablePanelUpdate masterTablePane() {
        List keys = new ArrayList();
        keys.add("masterId");
        if (showCustomerID) {
            keys.add("customerId");
        }
        keys.add("trecCompanyName");
        keys.add("emergencyNumber");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate tabel = new emcJTableUpdate(model);
        if (showCustomerID) {
            EMCLookupJTableComponent lkpCustomerId = new EMCLookupJTableComponent(new SOPCustomersMenu());
            lkpCustomerId.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", linesUD));
            tabel.setLookupToColumn("customerId", lkpCustomerId);
        }
        masterDRM.setMainTableComponent(tabel);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(tabel);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel linesPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Trec", linesTablePane());
        tabbed.add("Additional Info", linesAdditionalInfoPane());
        tabbed.add("Approval", linesApprovalPane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(linesButtonPane(), BorderLayout.EAST);
        return thePanel;
    }

    private emcTablePanelUpdate linesTablePane() {
        List keys = new ArrayList();
        keys.add("lineId");
        keys.add("unNumber");
        keys.add("tradingName");
        keys.add("properShipping");
        keys.add("preferredShipName");
        keys.add("packingGroup");
        keys.add("form");
        keys.add("colour");
        keys.add("odour");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate tabel = new emcJTableUpdate(model);
        setupLinesTableLookups(tabel);
        linesDRM.setMainTableComponent(tabel);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(tabel);
        linesDRM.setTablePanel(tableScroll);

        return tableScroll;
    }

    private void setupLinesTableLookups(emcJTableUpdate tabel) {
        EMCLookupJTableComponent lkpUNNumber = new EMCLookupJTableComponent(new TRECChemicalsMenu());
        Map<String, String> formKeys = new HashMap();
        formKeys.put("chemicalId", "chemicalId");
        lkpUNNumber.setPopup(new EMCMultiValuePopup(new TRECChemicals(), "unNumber", linesDRM, formKeys, linesUD));
        tabel.setLookupToColumn("unNumber", lkpUNNumber);

        lkpPrefShipName = new EMCLookupJTableComponent(new TRECPrefShipNameMI());
        lkpPrefShipName.setPopup(new EMCLookupPopup(new TRECPreferredShipName(), "properShipName", linesUD));
        //lkpPrefShipName.setTheQuery(query);

        tabel.setLookupToColumn("preferredShipName", lkpPrefShipName);

        EMCLookupJTableComponent lkpShippingName = new EMCLookupJTableComponent(new TRECChemicalsMenu());
        lkpShippingName.setPopup(new EMCMultiValuePopup(new TRECChemicals(), "shippingName", linesDRM, formKeys, linesUD));
        tabel.setLookupToColumn("properShipping", lkpShippingName);
        lrm = new TRECLRM();
        lrm.addLookup(lkpUNNumber, "unNumber");
        lrm.addLookup(lkpShippingName, "shippingName");
        lrm.addLookup(lkpPrefShipName, "preferredName");
        lrm.initializeLookups();

        tabel.setComboBoxLookupToColumn("packingGroup", cbPackagingGroup);

        EMCLookupJTableComponent lkpForm = new EMCLookupJTableComponent(new TRECFormsMenu());

        lkpForm.setPopup(
                new EMCLookupPopup(new TRECForms(), "formId", linesUD));
        tabel.setLookupToColumn(
                "form", lkpForm);
        EMCLookupJTableComponent lkpColour = new EMCLookupJTableComponent(new TRECColoursMenu());

        lkpColour.setPopup(
                new EMCLookupPopup(new TRECColours(), "colourId", linesUD));
        tabel.setLookupToColumn(
                "colour", lkpColour);
        EMCLookupJTableComponent lkpOdour = new EMCLookupJTableComponent(new TRECOdoursMenu());

        lkpOdour.setPopup(
                new EMCLookupPopup(new TRECOdours(), "odourId", linesUD));
        tabel.setLookupToColumn(
                "odour", lkpOdour);
    }

    private emcJPanel linesAdditionalInfoPane() {
        emcJTextField txtPreparedBy = new emcJTextField(new EMCStringFormDocument(linesDRM, "preparedBy"));
        emcJTextField txtEmergencyNumber = new emcJTextField(new EMCStringFormDocument(linesDRM, "emergencyNumber"));
        emcJTextArea txaAdditionalInfo = new emcJTextArea(new EMCStringFormDocument(linesDRM, "additionalInfo"));
        emcJTextField txtOwnRef = new emcJTextField(new EMCStringFormDocument(linesDRM, "ownReference"));
        
        Component[][] comp = {{new emcJLabel("Prepared By"), txtPreparedBy, new emcJLabel("Emergency Number"), txtEmergencyNumber},
            {new emcJLabel("Own Refernce"), txtOwnRef},
            {txaAdditionalInfo, new emcJLabel("Additional Info")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Additional Info");
    }

    private emcJPanel linesButtonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();

        buttonList.add(new PrintTRECButton(linesDRM, linesUD));

        final TRECEditTRECMI editMI = new TRECEditTRECMI();
        editMI.setDoNotOpenForm(false);
        buttonList.add(new emcMenuButton("Edit", editMI, this, 1, true, false) {
            @Override
            public void doActionPerformed(ActionEvent e) {
                TRECTrecCardsForm.this.doSaveOnButtonPress();

                if (Functions.checkBlank(linesDRM.getLastFieldValueAt("form")) && Functions.checkBlank(linesDRM.getLastFieldValueAt("colour")) && Functions.checkBlank(linesDRM.getLastFieldValueAt("odour"))) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please set either Form, Colour or Odour.", linesUD);
                } else {
                    if ((Long) linesDRM.getLastFieldValueAt("recordID") != 0L) {
                        EMCQuery query;
                        List toSend;
                        EMCCommandClass cmd;

                        long custChemId = 0;

                        if ((Long) linesDRM.getLastFieldValueAt("customerChemical") <= 0L) {
                            cmd = new EMCCommandClass(ServerTRECMethods.CREATE_CUSTOMER_CHEMICALS);

                            toSend = new ArrayList();
                            toSend.add(linesDRM.getLastFieldValueAt("recordID"));
                            toSend.add(String.valueOf(linesDRM.getLastFieldValueAt("recordID")));
                            toSend.add(linesDRM.getLastFieldValueAt("unNumber"));
                            toSend.add(linesDRM.getLastFieldValueAt("properShipping"));

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                            if (toSend.size() > 1 && toSend.get(1) instanceof Long) {
                                long custChemRecId = (long) toSend.get(1);

                                if (custChemRecId > 0) {
                                    cmd = new EMCCommandClass(ServerTRECMethods.ENCRYPT_CUSTOMER_CHEMICALS);

                                    toSend = new ArrayList();
                                    toSend.add(custChemRecId);

                                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                                    custChemId = custChemRecId;
                                }
                            } else {
                                custChemId = 0;
                            }

                            linesDRM.refreshRecordIgnoreFocus(linesDRM.getLastRowAccessed());
                        } else {
                            custChemId = (Long) linesDRM.getLastFieldValueAt("customerChemical");
                        }

                        if (custChemId > 0) {
                            toSend = new ArrayList();
                            toSend.add(custChemId);

                            cmd = new EMCCommandClass(ServerTRECMethods.FETCH_CUSTOMER_CHEMICALS);

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, linesUD);

                            TRECCustomerChemicals chemical = (TRECCustomerChemicals) toSend.get(1);

                            if (chemical != null) {
                                TRECTrecCardsForm.this.chemical = chemical;

                                EMCUserData createUD = linesDRM.generateRelatedFormUserData(linesDRM.getUserData(), 1);

                                //Add selection conditions
                                if (createUD.getUserData(0) instanceof EMCQuery) {
                                    linesDRM.fixRelatedFormQuery((EMCQuery) createUD.getUserData(0), createUD);
                                }

                                TRECTrecCardsForm.this.getDeskTop().createAndAdd(editMI, -1, -1, createUD, null, 1);
                            }
                        }
                    }
                }
            }
        });

        buttonList.add(new emcJButton("Reset") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if ((Long) linesDRM.getLastFieldValueAt("recordID") != 0L) {

                    int dialogResult = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this),
                            "Reset TREC Line", "Are you sure you want to reset the TREC Line. Any changes made will be lost.");

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.RESET_CUSTOMER_CHEMICAL);

                        List toSend = new ArrayList();
                        toSend.add(linesDRM.getLastFieldValueAt("recordID"));

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, linesUD);

                        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "TREC Line reset.", linesUD);
                            linesDRM.refreshRecordIgnoreFocus(linesDRM.getLastRowAccessed());
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to reset the TREC Line.", linesUD);
                        }
                    }
                }
            }
        });
        buttonList.add(new PrintTRECProducedCardTemplateButton(linesDRM, linesUD));

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel masterButtonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new BackupTRECButton(this, masterDRM));
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel linesApprovalPane() {
        emcYesNoComponent ynApproved = new emcYesNoComponent(linesDRM, "approved");
        ynApproved.setEditable(false);
        EMCDatePickerFormComponent dpApprovedDate = new EMCDatePickerFormComponent(linesDRM, "approvedDate");
        dpApprovedDate.setEnabled(false);
        emcJTextField txtApprovedBy = new emcJTextField(new EMCStringFormDocument(linesDRM, "approvedBy"));
        txtApprovedBy.setEditable(false);

        Component[][] comp = {{new emcJLabel("Approved"), ynApproved},
            {new emcJLabel("Approved By"), txtApprovedBy},
            {new emcJLabel("Approved Date"), dpApprovedDate}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Approval");
    }
}
