/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.declarationlines;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.entity.trec.TRECChemicals;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECPackingGroupEnum;
import emc.forms.trec.display.treccards.resources.TRECLRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.developertools.trec.TRECChemicalsMenu;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.trec.ServerTRECMethods;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

/**
 *
 * @author pj
 */
public class DGDeclarationLinesForm extends BaseInternalFrame{
    
    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData linesUD;
    private emcDataRelationManagerUpdate linesDRM;

    public DGDeclarationLinesForm(EMCUserData userData) {
        super("Dangerous Goods Declarations", true, true, true, true, userData);
        this.setBounds(20, 20, 850, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DG.getId(), new DGDeclarationMaster(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("defConsignor");
        masterDRM.setFormTextId2("defOperator");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DG.getId(), new DGDeclarationLines(), linesUD), linesUD);
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("consignee");
        linesDRM.setFormTextId2("operator");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("decNumber");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("decNumber");
        //In It
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane masterTableTab = new emcJTabbedPane();
        masterTableTab.add("Overview", masterTablePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        
        emcJTabbedPane linesTableTab = new emcJTabbedPane();
        linesTableTab.add("Overview", linestablePane());
       
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterTableTab, linesTableTab);
        topBottomSplit.setDividerLocation(350);
       
        contentPane.add(topBottomSplit);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate masterTablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("decNumber");
        keys.add("customer");
        keys.add("defConsignor");
        keys.add("defOperator");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;

    }

    private emcTablePanelUpdate linestablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("lineNumber");
        keys.add("consignor");
        keys.add("consignee");
        keys.add("operator");
        keys.add("productOwner");
        keys.add("productCustodian");
        keys.add("productManufacturer");
        keys.add("contractingParty");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
//        EMCLookupJTableComponent lkpUNNumber = new EMCLookupJTableComponent(new TRECChemicalsMenu());
//        lkpUNNumber.setPopup(new EMCLookupPopup(new TRECChemicals(), "unNumber", linesUD));
//        table.setLookupToColumn("unNumber", lkpUNNumber);
//        EMCLookupJTableComponent lkpShippingName = new EMCLookupJTableComponent(new TRECChemicalsMenu());
//        lkpShippingName.setPopup(new EMCLookupPopup(new TRECChemicals(), "shippingName", linesUD));
//        table.setLookupToColumn("properShipping", lkpShippingName);
//        table.setComboBoxLookupToColumn("packingGroup", new emcJComboBox(TRECPackingGroupEnum.values()));
//        table.setColumnEditable("allowed", false);
        //Lookups
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

//    private emcJPanel buttonPane() {
//
//        emcMenuButtonList btnCargoCheckReport = new emcMenuButtonList("Print Report", this){
//
//            EMCQuery query = masterDRM.getOriginalQuery().copyQuery();
//
//                 @Override
//                public void executeCmd(String theCmd) {
//                     if(theCmd.equals("This Record")){
//                                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Print", "Are you sure you want to print ?") == JOptionPane.YES_OPTION) {
//                                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_CARGO_CHECK_REPORT.toString());
//
//                                    JasperInformation jasperInfo = new JasperInformation();
//
//                                    jasperInfo.setJasperTemplate("/emc/reports/trec/cargocheck/CargoCheckReport.jrxml");
//                                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.cargocheck.CargoCheckReportDS");
//                                    jasperInfo.setReportTitle("CARGO CHECK REPORT");
//
//                                    query = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckMaster.class);
//                                    query.addAnd("cargoCheckNumber", masterDRM.getLastFieldValueAt("cargoCheckNumber"));
//                                    query.addTableAnd(TRECCargoCheckLines.class.getName(), "cargoCheckNumber", TRECCargoCheckMaster.class.getName(), "cargoCheckNumber");
//
//                                    List toSend = new ArrayList();
//                                    toSend.add(query);
//
//
//                                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.PRINT_CARGO_CHECK_REPORT, toSend, masterDRM.getUserData());
//                                }
//                     }
//                     }
//        };
//        btnCargoCheckReport.addMenuItem("This Record", null, 1, false);
//        List<emcJButton> buttonList = new ArrayList<emcJButton>();
//        buttonList.add(btnCargoCheckReport);
//
//        return emcSetGridBagConstraints.createButtonPanel(buttonList);
//    }
    
}
