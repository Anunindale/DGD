/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.cargocheck;

import emc.app.components.base.sms.EMCSMSButton;
import java.awt.BorderLayout;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcJButton;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.entity.trec.TRECChemicals;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECPackingGroupEnum;
import emc.forms.trec.display.treccards.resources.TRECLRM;
import emc.framework.EMCUserData;
import emc.menus.developertools.trec.TRECChemicalsMenu;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;
import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import emc.framework.EMCQuery;
import emc.enums.enumQueryTypes;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.util.utilFunctions;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class TRECCargoCheckForm extends BaseInternalFrame {

    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData linesUD;
    private emcDataRelationManagerUpdate linesDRM;
    private TRECLRM lrm;

    public TRECCargoCheckForm(EMCUserData userData) {
        super("Cargo Check", true, true, true, true, userData);
        this.setBounds(20, 20, 850, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECCargoCheckMaster(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("cargoCheckNumber");
        masterDRM.setFormTextId2("description");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECCargoCheckLines(), linesUD), linesUD) {

            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                if (lrm != null) {
                    lrm.doRowChanged(linesDRM);
                }
            }

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
                if (columnIndex.equals("unNumber") && lrm != null) {
                    lrm.doRowChanged(linesDRM);
                }
            }

            @Override
            public void updatePersist(int rowIndex) {
                super.updatePersist(rowIndex);
                if (getLastUpdateStatus()) {
                    this.getHeaderTable().refreshRecordIgnoreFocus(-1);
                }
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                super.deleteRowCache(rowIndex);
                this.getHeaderTable().refreshRecordIgnoreFocus(-1);
            }
        };
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("unNumber");
        linesDRM.setFormTextId2("properShipping");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("cargoCheckNumber");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("cargoCheckNumber");
        //In It
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane masterTableTab = new emcJTabbedPane();
        masterTableTab.add("Overview", masterTablePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(buttonPane(), BorderLayout.EAST);
        
        
        emcJTabbedPane masterNotesTab = new emcJTabbedPane();
        emcJTextArea masterNotes = new emcJTextArea(new EMCStringFormDocument(masterDRM, "notes"));
        masterNotes.setEditable(false);
        masterNotesTab.add("notes", new emcJScrollPane(masterNotes));
        emcJTabbedPane linesTableTab = new emcJTabbedPane();
        linesTableTab.add("Overview", linestablePane());

        emcJSplitPane topSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterTableTab, masterNotesTab);
       
        topSplit.setDividerLocation(200);
        
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, linesTableTab);
        topBottomSplit.setDividerLocation(350);
        contentPane.add(topBottomSplit);
        this.setContentPane(contentPane);


    }

    private emcTablePanelUpdate masterTablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("cargoCheckNumber");
        keys.add("description");
        keys.add("placeCard");
        keys.add("trecRequired");
        keys.add("cargo");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnEditable("placeCard", false);
        table.setColumnEditable("trecRequired", false);
        table.setColumnEditable("cargo", false);

        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;

    }

    private emcTablePanelUpdate linestablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("unNumber");
        keys.add("properShipping");
        keys.add("packingGroup");
        keys.add("quantity");
        keys.add("allowed");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpUNNumber = new EMCLookupJTableComponent(new TRECChemicalsMenu());
        lkpUNNumber.setPopup(new EMCLookupPopup(new TRECChemicals(), "unNumber", linesUD));
        table.setLookupToColumn("unNumber", lkpUNNumber);
        EMCLookupJTableComponent lkpShippingName = new EMCLookupJTableComponent(new TRECChemicalsMenu());
        lkpShippingName.setPopup(new EMCLookupPopup(new TRECChemicals(), "shippingName", linesUD));
        table.setLookupToColumn("properShipping", lkpShippingName);
        lrm = new TRECLRM();
        lrm.addLookup(lkpUNNumber, "unNumber");
        lrm.addLookup(lkpShippingName, "shippingName");
        lrm.initializeLookups();
        table.setComboBoxLookupToColumn("packingGroup", new emcJComboBox(TRECPackingGroupEnum.values()));
        table.setColumnEditable("allowed", false);
        //Lookups
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {

        emcMenuButtonList btnCargoCheckReport = new emcMenuButtonList("Print Report", this){

            EMCQuery query = masterDRM.getOriginalQuery().copyQuery();

                 @Override
                public void executeCmd(String theCmd) {
                     if(theCmd.equals("This Record")){
                                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Print", "Are you sure you want to print ?") == JOptionPane.YES_OPTION) {
                                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_CARGO_CHECK_REPORT.toString());

                                    JasperInformation jasperInfo = new JasperInformation();

                                    jasperInfo.setJasperTemplate("/emc/reports/trec/cargocheck/CargoCheckReport.jrxml");
                                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.cargocheck.CargoCheckReportDS");
                                    jasperInfo.setReportTitle("CARGO CHECK REPORT");

                                    query = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckMaster.class);
                                    query.addAnd("cargoCheckNumber", masterDRM.getLastFieldValueAt("cargoCheckNumber"));
                                    query.addTableAnd(TRECCargoCheckLines.class.getName(), "cargoCheckNumber", TRECCargoCheckMaster.class.getName(), "cargoCheckNumber");

                                    List toSend = new ArrayList();
                                    toSend.add(query);


                                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.PRINT_CARGO_CHECK_REPORT, toSend, masterDRM.getUserData());
                                }
                     }
                     }
        };
        btnCargoCheckReport.addMenuItem("This Record", null, 1, false);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnCargoCheckReport);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

}
