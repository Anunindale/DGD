/*
 * companyForm.java
 *
 * Created on 04 December 2007, 09:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.base.display.companies;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcMenuButton;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.documentHandler;
import emc.menus.gl.menuitems.display.GLCurrency;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class companyForm extends BaseInternalFrame {

    private emcJPanel generalPanel = new emcJPanel();
    private emcJPanel companiesPanel = new emcJPanel();
    private emcJPanel contactPanel = new emcJPanel();
    private emcJPanel otherPanel = new emcJPanel();
    private emcJTextField compName = new emcJTextField();
    private emcJLabel compTradingAsLabel = new emcJLabel("Trading As");
    private emcJTextField compTradingAs = new emcJTextField();
    private emcJLabel compNameLabel = new emcJLabel("Company Name");
    private emcJLabel compPostalCodeLabel = new emcJLabel("Postal Code");
    //private emcJTextField compPostalCode = new emcJTextField();
    private EMCLookupFormComponent postCodeLookup;
    private emcJLabel compPhysicalCodeLabel = new emcJLabel("Postal Code");
    //private emcJTextField compPhysicalCode = new emcJTextField();
    private EMCLookupFormComponent physPostCodeLookup;
    private emcJLabel compPostAdrsLabel = new emcJLabel("Postal Address");
    private emcJLabel compPhysAdrsLabel = new emcJLabel("Physical Address");
    private emcJLabel compPostAdrsLine1Label = new emcJLabel("Address Line 1");
    private emcJLabel compPostAdrsLine2Label = new emcJLabel("Address Line 2");
    private emcJLabel compPostAdrsLine3Label = new emcJLabel("Address Line 3");
    private emcJLabel compPostAdrsLine4Label = new emcJLabel("Address Line 4");
    private emcJLabel compPostAdrsLine5Label = new emcJLabel("Address Line 5");
    private emcJLabel compPhysAdrsLine1Label = new emcJLabel("Address Line 1");
    private emcJLabel compPhysAdrsLine2Label = new emcJLabel("Address Line 2");
    private emcJLabel compPhysAdrsLine3Label = new emcJLabel("Address Line 3");
    private emcJLabel compPhysAdrsLine4Label = new emcJLabel("Address Line 4");
    private emcJLabel compPhysAdrsLine5Label = new emcJLabel("Address Line 5");
    private emcJTextField compPostAdrsLine1 = new emcJTextField();
    private emcJTextField compPostAdrsLine2 = new emcJTextField();
    private emcJTextField compPostAdrsLine3 = new emcJTextField();
    private emcJTextField compPostAdrsLine4 = new emcJTextField();
    private emcJTextField compPostAdrsLine5 = new emcJTextField();
    private emcJTextField compPhysAdrsLine1 = new emcJTextField();
    private emcJTextField compPhysAdrsLine2 = new emcJTextField();
    private emcJTextField compPhysAdrsLine3 = new emcJTextField();
    private emcJTextField compPhysAdrsLine4 = new emcJTextField();
    private emcJTextField compPhysAdrsLine5 = new emcJTextField();
    private emcJPanel postAdrsPanel = new emcJPanel();
    private emcJPanel physAdrsPanel = new emcJPanel();
    //
    private emcJLabel compTelLabel = new emcJLabel("Telephone");
    private emcJTextField compTel = new emcJTextField();
    private emcJLabel compRegLabel = new emcJLabel("Registration No");
    private emcJTextField compReg = new emcJTextField();
    private emcJLabel compFaxLabel = new emcJLabel("Fax No");
    private emcJTextField compFax = new emcJTextField();
    private emcJLabel compVatLabel = new emcJLabel("VAT No");
    private emcJTextField compVat = new emcJTextField();
    private emcJLabel compEmailLabel = new emcJLabel("E-mail");
    private emcJTextField compEmail = new emcJTextField();
    private emcJLabel compCallCentreLabel = new emcJLabel("Call Centre No");
    private emcJTextField compCallCentre = new emcJTextField();
    private emcJLabel compEmergencyLabel = new emcJLabel("Emergency No");
    private emcJTextField compEmergency = new emcJTextField();
    private emcJLabel compCellLabel = new emcJLabel("Cell No");
    private emcJTextField compCell = new emcJTextField();
    private emcJLabel compWebLabel = new emcJLabel("Web Site");
    private emcJTextField compWeb = new emcJTextField();
    private emcJLabel compBankLabel = new emcJLabel("Bank Name");
    private emcJTextField compBank = new emcJTextField();
    private emcJLabel compAccountLabel = new emcJLabel("Bank Account");
    private emcJTextField compAccount = new emcJTextField();
    private emcJLabel compBranchLabel = new emcJLabel("Bank Branch");
    private emcJTextField compBranch = new emcJTextField();
    private emcJLabel lblSWIFTCode;
    private emcJTextField txtSWIFTCode = new emcJTextField();
    private emcJLabel lblExportersCode;
    private emcJTextField txtExportersCode = new emcJTextField();
    private emcJLabel lblCurrency;
    private EMCLookupFormComponent lkpCurrency;
//    private emcJLabel compLogoLabel = new emcJLabel("Logo Path");
//    private EMCFilePickerFormComponent compLogo;
    private emcJLabel compLetterLabel = new emcJLabel("Letter Head Path");
    private EMCFilePickerFormComponent compLetter;
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    //end dataSource
    /** Creates a new instance of companyForm */
    public companyForm(EMCUserData userData) {
        super("Companies", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 450);
        this.setHelpFile(new emcHelpFile("Base/Companies.html"));
        try {
            //Postal code lookups
            //List postalCodeKeys = new ArrayList();
            //postalCodeKeys.add("code");
            //postalCodeKeys.add("suburb");
            //postalCodeKeys.add("country");


            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.BaseCompanyTable(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("companyId");
            dataRelation.setFormTextId2("companyName");
            setDocuments();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        compLogo = new EMCFilePickerFormComponent(dataRelation, "coLogoPath", userData);
//        compLetter = new EMCFilePickerFormComponent(dataRelation, "coLetterHeadPath", userData);

        initFrame();
    }

    private void setDocuments() {
        EMCUserData copyUD = this.getUserData().copyUserData();
        //Postal code lookups
        physPostCodeLookup = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                dataRelation, "coPhysPostCode");

        postCodeLookup = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                dataRelation, "coPostCode");

        List postalCodeKeys = new ArrayList();
        postalCodeKeys.add("code");
        postalCodeKeys.add("suburb");

        EMCLookupPopup postCodePopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BasePostalCodes(),
                "code", postalCodeKeys, copyUD);
        physPostCodeLookup.setPopup(postCodePopup);
        postCodeLookup.setPopup(postCodePopup);

        lkpCurrency = new EMCLookupFormComponent(new GLCurrency(), dataRelation, "currency");
        lkpCurrency.setPopup(new EMCLookupPopup(new emc.entity.gl.GLCurrency(), "currency", copyUD));

        this.compName.setDocument(new EMCStringFormDocument(dataRelation, "companyName"));
        this.compAccount.setDocument(new EMCStringFormDocument(dataRelation, "coBankAccNo"));
        this.compBank.setDocument(new EMCStringFormDocument(dataRelation, "coBank"));
        this.compBranch.setDocument(new EMCStringFormDocument(dataRelation, "coBankBranch"));
        this.compCallCentre.setDocument(new EMCStringFormDocument(dataRelation, "coCallCentreNumber"));
        this.compCell.setDocument(new EMCStringFormDocument(dataRelation, "coCellNr"));
        this.compEmail.setDocument(new EMCStringFormDocument(dataRelation, "coEmailAdrs"));
        this.compEmergency.setDocument(new EMCStringFormDocument(dataRelation, "coEmergencyNumber"));
        this.compFax.setDocument(new EMCStringFormDocument(dataRelation, "coFaxNr"));
        this.compTel.setDocument(new EMCStringFormDocument(dataRelation, "coPhoneNr"));
        this.compPhysAdrsLine1.setDocument(new EMCStringFormDocument(dataRelation, "coPhysAdrs1"));
        this.compPhysAdrsLine2.setDocument(new EMCStringFormDocument(dataRelation, "coPhysAdrs2"));
        this.compPhysAdrsLine3.setDocument(new EMCStringFormDocument(dataRelation, "coPhysAdrs3"));
        this.compPhysAdrsLine4.setDocument(new EMCStringFormDocument(dataRelation, "coPhysAdrs4"));
        this.compPhysAdrsLine5.setDocument(new EMCStringFormDocument(dataRelation, "coPhysAdrs5"));
        this.compPostAdrsLine1.setDocument(new EMCStringFormDocument(dataRelation, "coPostAdrs1"));
        this.compPostAdrsLine2.setDocument(new EMCStringFormDocument(dataRelation, "coPostAdrs2"));
        this.compPostAdrsLine3.setDocument(new EMCStringFormDocument(dataRelation, "coPostAdrs3"));
        this.compPostAdrsLine4.setDocument(new EMCStringFormDocument(dataRelation, "coPostAdrs4"));
        this.compPostAdrsLine5.setDocument(new EMCStringFormDocument(dataRelation, "coPostAdrs5"));
        this.compReg.setDocument(new EMCStringFormDocument(dataRelation, "coRegNr"));
        this.compTradingAs.setDocument(new EMCStringFormDocument(dataRelation, "coTradingAs"));

        this.lblExportersCode = new emcJLabel(dataRelation.getColumnName("exportersCode"));
        this.lblSWIFTCode = new emcJLabel(dataRelation.getColumnName("swiftCode"));
        this.lblCurrency = new emcJLabel(dataRelation.getColumnName("currency"));
        this.txtExportersCode.setDocument(new EMCStringFormDocument(dataRelation, "exportersCode"));
        this.txtSWIFTCode.setDocument(new EMCStringFormDocument(dataRelation, "swiftCode"));

        //Creates an instance of emcDocument used for a VAT number
        EMCStringFormDocument vatDoc = new EMCStringFormDocument(dataRelation, "coVatRegNr");
        this.compVat.setDocument(vatDoc);
        this.compWeb.setDocument(new EMCStringFormDocument(dataRelation, "coWebsite"));
    }

    private void tabGeneral() {
        generalPanel.setLayout(new GridBagLayout());
        generalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));
        GridBagConstraints localg;

        //physAdrsPanel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, updatedFocusTraversalKeys);

        //Company name
        int y = 0;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(this.compNameLabel, localg);
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 1, y, 0.9, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        generalPanel.add(this.compName, localg);

        //Trading as
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(this.compTradingAsLabel, localg);
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        generalPanel.add(this.compTradingAs, localg);

        //Empty space (4 cells)
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(new emcJLabel(), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 1);
        generalPanel.add(new emcJLabel(), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 1);
        generalPanel.add(new emcJLabel(), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 1);
        generalPanel.add(new emcJLabel(), localg);

        //Postal address panel      
        int panelY = 0;
        GridBagConstraints panelg;
        postAdrsPanel.setLayout(new GridBagLayout());

        //Empty space
        panelg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(new emcJLabel(), panelg);

        //Postal Address Label
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(compPostAdrsLabel, panelg);

        //Line 1
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.compPostAdrsLine1Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(compPostAdrsLine1, panelg);

        //Line 2
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.compPostAdrsLine2Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(compPostAdrsLine2, panelg);

        //Line 3
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.compPostAdrsLine3Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(compPostAdrsLine3, panelg);

        //Line 4
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.compPostAdrsLine4Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(compPostAdrsLine4, panelg);

        //Line 5
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.compPostAdrsLine5Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(compPostAdrsLine5, panelg);

        //Postal code
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.compPostalCodeLabel, panelg);
        panelg = emcSetGridBagConstraints.createStandard(1, panelY, 0.1, GridBagConstraints.LINE_START);
        postCodeLookup.setPreferredSize(new java.awt.Dimension(80, 25));
        postAdrsPanel.add(postCodeLookup, panelg);

        //Physical address panel      
        panelY = 0;
        physAdrsPanel.setLayout(new GridBagLayout());

        //Empty space
        panelg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(new emcJLabel(), panelg);

        //Postal Address Label
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(compPhysAdrsLabel, panelg);

        //Line 1
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.compPhysAdrsLine1Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(compPhysAdrsLine1, panelg);

        //Line 2
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.compPhysAdrsLine2Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(compPhysAdrsLine2, panelg);

        //Line 3
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.compPhysAdrsLine3Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(compPhysAdrsLine3, panelg);

        //Line 4
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.compPhysAdrsLine4Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(compPhysAdrsLine4, panelg);

        //Line 5
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.compPhysAdrsLine5Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(compPhysAdrsLine5, panelg);

        //Postal code
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.compPhysicalCodeLabel, panelg);
        panelg = emcSetGridBagConstraints.createStandard(1, panelY, 0.1, GridBagConstraints.LINE_START);
        physPostCodeLookup.setPreferredSize(new java.awt.Dimension(80, 25));
        physAdrsPanel.add(physPostCodeLookup, panelg);

        //Add the panels
        y++;
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 2);
        generalPanel.add(this.postAdrsPanel, localg);
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 3, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 2);
        generalPanel.add(this.physAdrsPanel, localg);

        //End general panel
        y++;
        localg = emcSetGridBagConstraints.endPanel(y);
        generalPanel.add(new emcJLabel(), localg);

//        GridBagConstraints localg;
//        //comp name
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compNameLabel,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.9;
//        localg.anchor = GridBagConstraints.LINE_END;
//        localg.fill = GridBagConstraints.HORIZONTAL;
//        localg.gridwidth = GridBagConstraints.REMAINDER;
//        compName.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compName,localg);
//        //trading as
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compTradingAsLabel,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.9;
//        localg.anchor = GridBagConstraints.LINE_END;
//        localg.fill = GridBagConstraints.HORIZONTAL;
//        localg.gridwidth = GridBagConstraints.REMAINDER;
//        compTradingAs.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compTradingAs,localg);
//        //space
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 1;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(new emcJLabel(),localg);
//        //Adress labels
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 1;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compPostAdrsLabel,localg);
//        localg.gridx = 3;
//        localg.gridy = y;
//        generalPanel.add(this.compPhysAdrsLabel,localg);
//        //line 1
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compPostAdrsLine1Label,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.1;
//        compPostAdrsLine1.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPostAdrsLine1,localg);
//        localg.gridx = 2;
//        localg.gridy = y;
//        generalPanel.add(this.compPhysAdrsLine1Label,localg);
//        localg.gridx = 3;
//        localg.gridy = y;
//        this.compPhysAdrsLine1.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPhysAdrsLine1,localg);
//        //line 2
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compPostAdrsLine2Label,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.1;
//        compPostAdrsLine2.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPostAdrsLine2,localg);
//        localg.gridx = 2;
//        localg.gridy = y;
//        generalPanel.add(this.compPhysAdrsLine2Label,localg);
//        localg.gridx = 3;
//        localg.gridy = y;
//        this.compPhysAdrsLine2.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPhysAdrsLine2,localg);
//        //line 3
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compPostAdrsLine3Label,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.1;
//        compPostAdrsLine3.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPostAdrsLine3,localg);
//        localg.gridx = 2;
//        localg.gridy = y;
//        generalPanel.add(this.compPhysAdrsLine3Label,localg);
//        localg.gridx = 3;
//        localg.gridy = y;
//        this.compPhysAdrsLine3.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPhysAdrsLine3,localg);
//        //line 4
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compPostAdrsLine4Label,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.1;
//        compPostAdrsLine4.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPostAdrsLine4,localg);
//        localg.gridx = 2;
//        localg.gridy = y;
//        generalPanel.add(this.compPhysAdrsLine4Label,localg);
//        localg.gridx = 3;
//        localg.gridy = y;
//        this.compPhysAdrsLine4.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPhysAdrsLine4,localg);
//        //line 5
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compPostAdrsLine5Label,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.1;
//        compPostAdrsLine5.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPostAdrsLine5,localg);
//        localg.gridx = 2;
//        localg.gridy = y;
//        generalPanel.add(this.compPhysAdrsLine5Label,localg);
//        localg.gridx = 3;
//        localg.gridy = y;
//        this.compPhysAdrsLine5.setPreferredSize(new java.awt.Dimension(150,25));
//        generalPanel.add(this.compPhysAdrsLine5,localg);
//        //postal code
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        generalPanel.add(this.compPostalCodeLabel,localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.1;
//        compPostalCode.setPreferredSize(new java.awt.Dimension(60,25));
//        generalPanel.add(this.compPostalCode,localg);
//        localg.gridx = 2;
//        localg.gridy = y;
//        generalPanel.add(this.compPhysicalCodeLabel,localg);
//        localg.gridx = 3;
//        localg.gridy = y;
//        this.compPhysicalCode.setPreferredSize(new java.awt.Dimension(60,25));
//        generalPanel.add(this.compPhysicalCode,localg);
//        y++;
//        generalPanel.add(new emcJLabel(),emcSetGridBagConstraints.endPanel(y));

    }

    private void tabCompanies() {
        List keys = new ArrayList();
        keys.add("companyId");
        keys.add("companyName");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        companiesPanel.setLayout(new GridLayout(1, 1));
        companiesPanel.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void tabContactInfo() {

        this.contactPanel.setLayout(new GridBagLayout());
        contactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Details"));
        int y = 0;
        GridBagConstraints localg;
        //Adress labels
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 1;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.FIRST_LINE_START;
        contactPanel.add(new emcJLabel("Contact Details"), localg);
        localg.gridx = 3;
        localg.gridy = y;
        contactPanel.add(new emcJLabel("Registration Details"), localg);
        //tel and reg
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        contactPanel.add(this.compTelLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.1;
        compTel.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compTel, localg);
        localg.gridx = 2;
        localg.gridy = y;
        contactPanel.add(this.compRegLabel, localg);
        localg.gridx = 3;
        localg.gridy = y;
        this.compReg.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compReg, localg);
        //fax and vat
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        contactPanel.add(this.compFaxLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.1;
        compFax.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compFax, localg);
        localg.gridx = 2;
        localg.gridy = y;
        contactPanel.add(this.compVatLabel, localg);
        localg.gridx = 3;
        localg.gridy = y;
        this.compVat.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compVat, localg);
        //e-mail
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        contactPanel.add(this.compEmailLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.1;
        compEmail.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compEmail, localg);
        //call Centre
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        contactPanel.add(this.compCallCentreLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.1;
        compCallCentre.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compCallCentre, localg);
        //emergency
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        contactPanel.add(this.compEmergencyLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.1;
        compEmergency.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compEmergency, localg);
        //cell nr
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        contactPanel.add(this.compCellLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.1;
        compCell.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compCell, localg);
        //web site
        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        contactPanel.add(this.compWebLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.9;
        localg.anchor = GridBagConstraints.LINE_END;
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        compWeb.setPreferredSize(new java.awt.Dimension(150, 25));
        contactPanel.add(this.compWeb, localg);
        y++;
        contactPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void tabOther() {
        this.otherPanel.setLayout(new GridBagLayout());
        otherPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Bank and Branding"));
        int y = 0;
        GridBagConstraints localg;
        //Banking Label

        localg = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(new emcJLabel("Banking Details"), localg);
        //bank 
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(this.compBankLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);

        otherPanel.add(this.compBank, localg);
        //branch 
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(this.compBranchLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        otherPanel.add(this.compBranch, localg);
        //Account nr
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(this.compAccountLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        otherPanel.add(this.compAccount, localg);
        //SWIFT Code
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(this.lblSWIFTCode, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        otherPanel.add(this.txtSWIFTCode, localg);
        //Exporter's Code
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(this.lblExportersCode, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        otherPanel.add(this.txtExportersCode, localg);
        //Currency
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(this.lblCurrency, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        otherPanel.add(this.lkpCurrency, localg);
        //Branding Label + space
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        otherPanel.add(new emcJLabel(), localg);
//        y++;
//        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
//        otherPanel.add(new emcJLabel("Branding Details"), localg);
        //logo 
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        otherPanel.add(this.compLogoLabel, localg);
//
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.9;
//        localg.anchor = GridBagConstraints.LINE_END;
//        localg.fill = GridBagConstraints.HORIZONTAL;
//        localg.gridwidth = GridBagConstraints.REMAINDER;
//        otherPanel.add(this.compLogo, localg);
        //letter head
//        localg = null;
//        localg = new GridBagConstraints();
//        y++;
//        localg.gridx = 0;
//        localg.gridy = y;
//        localg.weightx = 0.1;
//        localg.anchor = GridBagConstraints.LINE_START;
//        otherPanel.add(this.compLetterLabel, localg);
//        localg.gridy = y;
//        localg.gridx = 1;
//        localg.weightx = 0.9;
//        localg.anchor = GridBagConstraints.LINE_END;
//        localg.fill = GridBagConstraints.HORIZONTAL;
//        localg.gridwidth = GridBagConstraints.REMAINDER;
//        otherPanel.add(this.compLetter, localg);
//        y++;
        otherPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));

    }

    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabCompanies();
        tabGeneral();
        tabContactInfo();
        tabOther();
        tabbedPanetop.add("Companies", this.companiesPanel);
        tabbedPanetop.add("General", this.generalPanel);
        tabbedPanetop.add("Contact Information", this.contactPanel);
        tabbedPanetop.add("Bank and Branding", this.otherPanel);

        this.add(tabbedPanetop, BorderLayout.CENTER);
        this.add(createButtonsPane(), BorderLayout.EAST);
    }

    private emcJPanel createButtonsPane() {
        emcMenuButton btnAddPhoto = new emcMenuButton("Add Logo", new documentHandler(), this, -1, false) {

            @Override
            public void doActionPerformed(ActionEvent e) {
                if(dataRelation.getLastFieldValueAt("companyId").equals(getUserData().getCompanyId())){
                    companyForm.this.doSaveOnButtonPress();
                    if ((Long) dataRelation.getLastFieldValueAt("recordID") != 0) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.ADD_COMPANY_LOGO_PATH);
                        List toSend = new ArrayList();

                        toSend.add(companyForm.this.dataRelation.getLastFieldValueAt("recordID"));

                        EMCWSManager.executeGenericWS(cmd, toSend, getUserData());
                    }
                    super.doActionPerformed(e);
                }else{
                    Logger.getLogger("emc").log(Level.WARNING, "Logo may only be set for the active company.", getUserData());
                    return;
                }
            }
        };

        emcMenuButton btnAddHeader = new emcMenuButton("Add Header", new documentHandler(), this, -1, false) {

            @Override
            public void doActionPerformed(ActionEvent e) {
                if(dataRelation.getLastFieldValueAt("companyId").equals(getUserData().getCompanyId())){
                    companyForm.this.doSaveOnButtonPress();
                    if ((Long) dataRelation.getLastFieldValueAt("recordID") != 0) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.ADD_LETTER_HEAD);
                        List toSend = new ArrayList();

                        toSend.add(companyForm.this.dataRelation.getLastFieldValueAt("recordID"));

                        EMCWSManager.executeGenericWS(cmd, toSend, getUserData());
                    }
                    super.doActionPerformed(e);
                    }else{
                    Logger.getLogger("emc").log(Level.WARNING, "Logo may only be set for the active company.", getUserData());
                    return;
                }
            }
        };

        emcMenuButton btnAddAdditionalLogo = new emcMenuButton("Add Additional Logo", new documentHandler(), this, -1, false) {

            @Override
            public void doActionPerformed(ActionEvent e) {
                if(dataRelation.getLastFieldValueAt("companyId").equals(getUserData().getCompanyId())){
                    companyForm.this.doSaveOnButtonPress();
                    if ((Long) dataRelation.getLastFieldValueAt("recordID") != 0) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.ADD_LETTER_HEAD);
                        List toSend = new ArrayList();

                        toSend.add(companyForm.this.dataRelation.getLastFieldValueAt("recordID"));

                        EMCWSManager.executeGenericWS(cmd, toSend, getUserData());
                    }
                    super.doActionPerformed(e);
               }else{
                    Logger.getLogger("emc").log(Level.WARNING, "Logo may only be set for the active company.", getUserData());
                    return;
              }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnAddPhoto);
        buttonList.add(btnAddHeader);
        buttonList.add(btnAddAdditionalLogo);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
