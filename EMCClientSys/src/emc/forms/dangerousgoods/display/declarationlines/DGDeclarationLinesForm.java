/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.declarationlines;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.DGDUN;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.entity.dangerousgoods.datasource.DGDeclarationLinesDS;
import emc.entity.dangerousgoods.datasource.DGDeclarationMasterDS;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.dangerousgoods.display.declarationlines.resources.DGSelectRegDialog;
import emc.forms.dangerousgoods.display.resources.DGDLRMLines;
import emc.forms.dangerousgoods.display.resources.DGDLRMMaster;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.dangerousgoods.menuitems.display.DGDContactsMI;
import emc.menus.dangerousgoods.menuitems.display.DGDUNMI;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.dangerousgoods.ServerDGMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

/**
 *
 * @author pj
 */
public class DGDeclarationLinesForm extends BaseInternalFrame {

    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData linesUD;
    private emcDataRelationManagerUpdate linesDRM;
    private EMCLookupRelationManager lrmMaster;
    private EMCLookupRelationManager lrmLines;
    private emcJLabel lblComments;
    private emcJTextArea txaComments = new emcJTextArea();

    public DGDeclarationLinesForm(EMCUserData userData) {
        super("Dangerous Goods Declarations", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DG.getId(), new DGDeclarationMasterDS(), masterUD), masterUD) {
            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                formUserData = super.generateRelatedFormUserData(formUserData, Index);

                switch (Index) {
                    case 0:
                        Object decNum = super.getLastFieldValueAt("decNumber");
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDeclarationLines.class);
                        if (decNum != null) {
                            query.addAnd("decNumber", decNum);
                        }

                        EMCQuery query2 = new EMCQuery(enumQueryTypes.SELECT, DGDUN.class);
                        query2.addAnd("lineNumber", null);

                        formUserData.setUserData(0, query2);
                        formUserData.setUserData(9, query);
                        break;
                    default:
                        break;
                }
                return formUserData;
            }

            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                if (lrmMaster != null) {
                    lrmMaster.doRowChanged(masterDRM);
                }
            }

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
                if (columnIndex.equals("customer") && lrmMaster != null) {
                    lrmMaster.doRowChanged(masterDRM);
                }
            }
        };
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("defConsignor");
        masterDRM.setFormTextId2("defOperator");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DG.getId(), new DGDeclarationLinesDS(), linesUD), linesUD) {
            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                if (lrmLines != null) {
                    lrmLines.doRowChanged(linesDRM);
                }
            }
            
            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) 
             {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
                if(this.getFieldValueAt(getLastRowAccessed(), "registrationNumber") != null)
                {
                    if(this.getFieldValueAt(getLastRowAccessed(), "registrationNumber").equals("~"))
                    {
                        //pop dialog
                        new DGSelectRegDialog(utilFunctions.findEMCDesktop(DGDeclarationLinesForm.this), linesDRM, this.getFieldValueAt(getLastRowAccessed(), "operator").toString());
                        //setFieldValueAt(rowIndex, columnIndex, aValue);
                    }
                }
             }
        };
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("consignee");
        linesDRM.setFormTextId2("operator");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("decNumber");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("decNumber");

        initFrame();
               
        lrmMaster.doRowChanged(masterDRM);
    }

    private void initFrame() {
//        emcJTabbedPane masterTableTab = new emcJTabbedPane();
//        masterTableTab.add("Customer", masterTablePane());
//
//        emcJPanel contentPane = new emcJPanel(new BorderLayout());
//
//        emcJTabbedPane linesTableTab = new emcJTabbedPane();
//        linesTableTab.add("Contacts", linestablePane());
//        linesTableTab.add("Additional Info", AdditionalInfoPane());
//        linesTableTab.add("Vehicle Registration Number", VehicleRegPane());
//
//        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterTableTab, linesTableTab);
//        topBottomSplit.setDividerLocation(350);
//
//        contentPane.add(topBottomSplit);
////        contentPane.add(createButtons(), BorderLayout.EAST);
//        this.setContentPane(contentPane);
        
        emcJTabbedPane masterTabs = masterTablePane();
        masterTabs.setRelationManager(masterDRM);
        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new BorderLayout());
        pnlMaster.add(masterTabs, BorderLayout.CENTER);

        emcJTabbedPane linesTabs = linestablePane();
        linesTabs.setRelationManager(linesDRM);
        emcJPanel pnlLines = new emcJPanel(new BorderLayout());
        pnlLines.add(linesTabs, BorderLayout.CENTER);
        pnlLines.add(createButtons(), BorderLayout.EAST);

        this.setLayout(new GridLayout(1, 1));

        this.add(pnlMaster);
        this.add(pnlLines);

        emcJSplitPane topBottomSplit;
        topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlMaster, pnlLines);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(300);

        this.add(topBottomSplit);
    }

    private emcJTabbedPane masterTablePane() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();

        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new GridLayout(1, 1));
        
        List<String> keys = new ArrayList<String>();
        keys.add("decNumber");
        keys.add("customer");
        keys.add("defConsignor");
        keys.add("defOperator");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        //Customer lookup
        EMCLookupJTableComponent customerlkp = new EMCLookupJTableComponent(new SOPCustomersMenu());
        customerlkp.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", masterUD));
        table.setLookupToColumn("customer", customerlkp);

       //Default Consignor and Operator Lookups
        EMCLookupJTableComponent defConsignorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        EMCLookupJTableComponent defOperatorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        lrmMaster = new DGDLRMMaster();
        lrmMaster.addLookup(defConsignorlkp, "defconsignorlkp");
        lrmMaster.addLookup(defOperatorlkp, "defoperatorlkp");
        lrmMaster.initializeLookups();

        /*DefConsignor Lookup*/

        EMCLookupPopup defConsignorPop = new EMCLookupPopup(new DGDContacts(), "contactNumber", masterUD);
        defConsignorlkp.setPopup(defConsignorPop);

        table.setLookupToColumn("defConsignor", defConsignorlkp);

        /*DefOperator Lookup*/

        List<String> operatorlkpKeys = new ArrayList<>();
        operatorlkpKeys.add("contactName");
        operatorlkpKeys.add("company");

        EMCLookupPopup defOperatorPop = new EMCLookupPopup(new DGDContacts(), "contactNumber", operatorlkpKeys, masterUD);
        defOperatorlkp.setPopup(defOperatorPop);
                
        table.setLookupToColumn("defOperator", defOperatorlkp);

        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        
        pnlMaster.setLayout(new BorderLayout());
        pnlMaster.add(tableScroll, BorderLayout.CENTER);
        
        masterTabs.add("Declaration Master", pnlMaster);
       
        return masterTabs;
    }

    private emcJTabbedPane linestablePane() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        emcJPanel pnlLines = new emcJPanel();
        
        List<String> keys = new ArrayList<String>();
        keys.add("lineNumber");
        keys.add("description");
        keys.add("consignor");
        keys.add("consignee");
        keys.add("operator");
        keys.add("productOwner");
        keys.add("productCustodian");
        keys.add("productManufacturer");
        keys.add("contractingParty");

        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        /*Lookups*/

        List<String> lkpKeys = new ArrayList<>();
        lkpKeys.add("contactName");
        lkpKeys.add("company");

        EMCLookupJTableComponent consignorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        consignorlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", lkpKeys, linesUD));

        EMCLookupJTableComponent consigneelkp = new EMCLookupJTableComponent(new DGDContactsMI());
        consigneelkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", lkpKeys, linesUD));

        EMCLookupJTableComponent operatorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        operatorlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", lkpKeys, linesUD));

        EMCLookupJTableComponent productownerlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        productownerlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", lkpKeys, linesUD));

        EMCLookupJTableComponent productcustodianlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        productcustodianlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", lkpKeys, linesUD));

        EMCLookupJTableComponent productmanufacturerlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        productmanufacturerlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", lkpKeys, linesUD));

        EMCLookupJTableComponent contractingpartylkp = new EMCLookupJTableComponent(new DGDContactsMI());
        contractingpartylkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", lkpKeys, linesUD));

        lrmLines = new DGDLRMLines();
        lrmLines.addLookup(consignorlkp, "consignor");
        lrmLines.addLookup(consigneelkp, "consignee");
        lrmLines.addLookup(operatorlkp, "operator");
        lrmLines.addLookup(productownerlkp, "productowner");
        lrmLines.addLookup(productcustodianlkp, "productcustodian");
        lrmLines.addLookup(productmanufacturerlkp, "productmanufacturer");
        lrmLines.addLookup(contractingpartylkp, "contractingparty");

        lrmLines.initializeLookups();
        lrmLines.doRowChanged(linesDRM);

        table.setLookupToColumn("consignor", consignorlkp);
        table.setLookupToColumn("consignee", consigneelkp);
        table.setLookupToColumn("operator", operatorlkp);
        table.setLookupToColumn("productOwner", productownerlkp);
        table.setLookupToColumn("productCustodian", productcustodianlkp);
        table.setLookupToColumn("productManufacturer", productmanufacturerlkp);
        table.setLookupToColumn("contractingParty", contractingpartylkp);

        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        
        pnlLines.setLayout(new BorderLayout());
        pnlLines.add(tableScroll, BorderLayout.CENTER);

        linesTabs.add("Declaration Lines", pnlLines);
        linesTabs.add("Additional Info", AdditionalInfoPane());
        linesTabs.add("Vehicle Registration Number", VehicleRegPane());

        return linesTabs;
    }
    
    private emcJPanel AdditionalInfoPane() 
    {
        this.lblComments = new emcJLabel(linesDRM.getColumnName("additionalInfo"));
        this.txaComments.setDocument(new EMCStringFormDocument(linesDRM, "additionalInfo"));
        
        Component[][] components = new Component[][]
        {
            {txaComments, lblComments}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }
    
    private emcJPanel VehicleRegPane() {
        emcJTextField txtDescription = new emcJTextField(new EMCStringFormDocument(linesDRM, "registrationNumber"));
        txtDescription.setEditable(false);
        
        Component[][] comp = {{new emcJLabel("Registration Number"), txtDescription}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Vehicle Registration Number");
    }

    private emcJPanel createButtons() {
        List<emcJButton> buttons = new ArrayList<>();

        buttons.add(0, new emcMenuButton("UN", new DGDUNMI(), this, 0, false));

        emcMenuButtonList btnDeclarationReport = new emcMenuButtonList("Print Report", this) {
            EMCQuery query = linesDRM.getOriginalQuery().copyQuery();

            @Override
            public void executeCmd(String theCmd) {
                if (theCmd.equals("This Record")) {
                    if (EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Print", "Are you sure you want to print ?") == JOptionPane.YES_OPTION) {
                        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DG.getId(), ServerDGMethods.PRINT_DECLARATION.toString());

                        JasperInformation jasperInfo = new JasperInformation();

                        jasperInfo.setJasperTemplate("/emc/reports/dangerousgoods/DeclarationReport.jrxml");
                        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.dangerousgoods.DeclarationReportDS");
                        jasperInfo.setReportTitle("Dangerous Goods Declaration");

                        query = new EMCQuery(enumQueryTypes.SELECT, DGDeclarationLines.class);
                            query.addAnd("decNumber", linesDRM.getLastFieldValueAt("decNumber"));
//                            query.addTableAnd(DGDeclarationMaster.class.getName(), "decNumber", DGDeclarationLines.class.getName(), "decNumber");

                        List toSend = new ArrayList();
                        toSend.add(query);


                        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DG_DECLARATION, toSend, linesDRM.getUserData());
                    }
                }
            }
        };
        btnDeclarationReport.addMenuItem("This Record", null, 1, false);
        buttons.add(btnDeclarationReport);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}

