/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.receivedjournals;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.inventory.emcStockButton;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.BaseCompanyTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.supplierreceivedjournals.ReceivedJournalType;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.pop.ServerPOPMethods;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class ReceivedJournalsForm extends BaseInternalFrame {

    // <editor-fold defaultstate="collapsed" desc="Master Overview Tab">
    private emcJTableUpdate tblMaster;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Overview Tab">
    private emcJTableUpdate tblLines;
    // </editor-fold>
    private ReceivedJournalsDRM masterDRM;
    private StockDRM linesDRM;
    private EMCUserData copyUD;

    public ReceivedJournalsForm(EMCUserData userData) {
        super("Goods Received Notes", true, true, true, true, userData);
        this.setBounds(20, 20, 770, 550);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryItemRanges.html"));
        try {
            copyUD = userData.copyUserDataAndDataList();

            masterDRM = new ReceivedJournalsDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(),
                    new emc.entity.pop.journals.POPSupplierReceivedJournalMaster(), copyUD), copyUD);
            this.setDataManager(masterDRM);

            StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", "transactionNumber");
            linesDRM = new StockDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(),
                    new emc.entity.pop.journals.datasource.POPSupplierReceivedJournalLinesDS(), userData), param, userData);

            masterDRM.setHeaderColumnID("receivedId");
            masterDRM.setTheForm(this);
            masterDRM.setFormTextId1("receivedId");
            masterDRM.setFormTextId2("supplierId");
            masterDRM.setLinesTable(linesDRM);

            linesDRM.setTheForm(this);
            linesDRM.setFormTextId1("lineNo");
            linesDRM.setFormTextId2("receivedId");
            linesDRM.setHeaderTable(masterDRM);
            linesDRM.setRelationColumnToHeader("receivedId");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel masterTabs = masterTabs();
        emcJTabbedPane linesTabs = linesTabs();

        this.setLayout(new GridLayout(1, 1));

        this.add(masterTabs);
        this.add(linesTabs);

        emcJSplitPane topBottomSplit;
        topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterTabs, linesTabs);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(220);

        this.add(topBottomSplit);
    }

    private emcJPanel masterTabs() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new BorderLayout(1, 1));

        emcJTabbedPane masterTabs = new emcJTabbedPane();
        masterTabs.add("Overview", masterOverview());

        thePanel.add(masterTabs, BorderLayout.CENTER);
        thePanel.add(masterButtons(), BorderLayout.EAST);
        return thePanel;
    }

    private emcJPanel masterButtons() {
        emcJPanel pnlButtons = new emcJPanel();
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        emcMenuButton button = new emcMenuButton("Print/Copy") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                String type = (String) masterDRM.getLastFieldValueAt("type");
                if (ReceivedJournalType.RECEIVED_NOTE.toString().equals(type)) {

                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_GOODS_RECEIVE_REPORT.toString());
                    //EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.GENERATE_GOODS_RECEIVED_REPORT.toString());

//                    JasperInformation jasperInfo = new JasperInformation();
//                    jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderPostingRecievedNote.jrxml");
//                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");
//                    jasperInfo.setReportTitle("Goods Received");

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorders/GoodsReceiveReport.jrxml");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.goodsreceive.POPGoodsReceiveReportDS");
                    jasperInfo.setReportTitle("Goods Received");
                    jasperInfo.setDateRangeDisplay(POPSupplierReceivedJournalLines.class.getName(), "receivedDate");

                    int row = masterDRM.getLastRowAccessed();

                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalLines.class);
                    query.addTableAnd(POPSupplierReceivedJournalMaster.class.getName(), "receivedId", POPSupplierReceivedJournalLines.class.getName(), "receivedId");
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", POPSupplierReceivedJournalLines.class.getName(), "itemId");
                    query.addTableAnd(BaseCompanyTable.class.getName(), "companyId", POPSupplierReceivedJournalMaster.class.getName(), "companyId");
                    query.addTableAnd(POPPurchaseOrderMaster.class.getName(), "purchaseOrderId", POPSupplierReceivedJournalMaster.class.getName(), "purchaseOrderId");
                    query.addTableAnd(POPPurchaseOrderLines.class.getName(), "purchaseOrderId", POPPurchaseOrderMaster.class.getName(), "purchaseOrderId");

                    query.addAnd("purchaseOrderId", masterDRM.getFieldValueAt(row, "purchaseOrderId"), POPSupplierReceivedJournalMaster.class.getName());
                    query.addAnd("receivedId", masterDRM.getFieldValueAt(row, "receivedId"), POPSupplierReceivedJournalMaster.class.getName());
                    //query.addAnd("itemId", masterDRM.getFieldValueAt(row, "itemId"), InventoryItemMaster.class.getName());
                    //query.addAnd("direction", InventoryTransactionDirection.IN.toString());
//                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
//                    query.addAnd("documentNo", masterDRM.getFieldValueAt(row, "documentNumber"));
//                    query.addAnd("refNumber", masterDRM.getFieldValueAt(row, "purchaseOrderId"));
//                    query.addAnd("companyId", copyUD.getCompanyId());
//                    query.addAnd("direction", InventoryTransactionDirection.IN.toString());

                    copyUD.setUserData(3, DocumentTypes.GOODS_RECEIVED_NOTE.toString());
                    copyUD.setUserData(7, false);
                    //Specifies that this is a copy.
                    copyUD.setUserData(9, true);

                    ArrayList toSend = new ArrayList();
                    toSend.add(query);

                    //EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.GOODS_RECEIVED_NOTE, toSend, copyUD);
                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.GOODS_RECEIVE_REPORT, toSend, copyUD);

                } else if (ReceivedJournalType.RETURN_GOODS.toString().equals(type)) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_GOODSRETURNEDREPORT.toString());

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorders/GoodsReturnedReport.jrxml");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorders.POPGoodsReceivedDS");
                    jasperInfo.setReportTitle("Goods Returned");

                    int row = masterDRM.getLastRowAccessed();
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                    query.addAnd("documentNo", masterDRM.getFieldValueAt(row, "documentNumber"));
                    query.addAnd("refNumber", masterDRM.getFieldValueAt(row, "purchaseOrderId"));
                    query.addAnd("companyId", copyUD.getCompanyId());
                    query.addAnd("direction", InventoryTransactionDirection.OUT.toString());

                    ArrayList toSend = new ArrayList();
                    toSend.add(query);

                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.POP_GOODSRETURNED, toSend, copyUD);
                }

//                // Print a services received report with the goods received or goods returned report.
//                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_SERVICES_RECEIVED_REPORT.toString());
//
//                JasperInformation jasperInfo = new JasperInformation();
//                jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorders/ServicesReceivedReport.jrxml");
//                jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorders.servicesreceived.POPServicesReceivedDS");
//                jasperInfo.setReportTitle("Services Received");
//
//                int row = masterDRM.getLastRowAccessed();
//
//                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalLines.class);
//                query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", POPSupplierReceivedJournalLines.class.getName(), "itemId");
//                query.addTableAnd(POPSupplierReceivedJournalMaster.class.getName(), "receivedId", POPSupplierReceivedJournalLines.class.getName(), "receivedId");
//                query.addAnd("receivedId", masterDRM.getFieldValueAt(row, "receivedId"), POPSupplierReceivedJournalMaster.class.getName());
//                query.addAnd("itemType", InventoryItemTypes.SERVICE.toString(), InventoryItemMaster.class.getName());
//
//                ArrayList toSend = new ArrayList();
//                toSend.add(query);
//                EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.SERVICES_RECEIVED, toSend, copyUD);
            }
        };
        pnlButtons.add(button, gbc);
        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
        return pnlButtons;
    }

    private emcJPanel masterOverview() {
        emcJPanel pnlOverview = new emcJPanel();
        pnlOverview.setLayout(new GridLayout(1, 1));

        List masterKeys = new ArrayList();
        masterKeys.add("receivedId");
        masterKeys.add("purchaseOrderId");
        masterKeys.add("type");
        masterKeys.add("supplierId");
        masterKeys.add("supplierName");
        masterKeys.add("supplierOrder");
        masterKeys.add("documentNumber");
        masterKeys.add("receivedDate");

        emcTableModelUpdate m = new emcTableModelUpdate(masterDRM, masterKeys);
        tblMaster = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        };
        tblMaster.setLookupCellEditor(1, new EMCGoToMainTableEditor(new EMCStringDocument(), new emc.menus.pop.menuitems.display.PurchaseOrders()));
        tblMaster.setLookupCellEditor(2, new EMCGoToMainTableEditor(new EMCStringDocument(), new emc.menus.pop.menuitems.display.Suppliers()));
        emcTablePanelUpdate linesScroll = new emcTablePanelUpdate(tblMaster);
        masterDRM.setMainTableComponent(tblMaster);
        this.setTablePanel(linesScroll);
        pnlOverview.add(linesScroll);

        return pnlOverview;
    }

    private emcJTabbedPane linesTabs() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        linesTabs.add("Overview", linesOverview());

        return linesTabs;
    }

    private emcJPanel linesOverview() {
        emcJPanel pnlOverview = new emcJPanel();
        pnlOverview.setLayout(new BorderLayout());

        List linesKeys = new ArrayList();
        linesKeys.add("itemPrimaryReference");
        linesKeys.add("itemDescription");
        linesKeys.add("dimension1");
        linesKeys.add("dimension3");
        linesKeys.add("dimension2");
        linesKeys.add("quantity");
        linesKeys.add("price");
        linesKeys.add("lineAmount");
        linesKeys.add("standardLocation");

        emcTableModelUpdate m = new emcTableModelUpdate(linesDRM, linesKeys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        tblLines = new emcJTableUpdate(m);
        tblLines.setLookupCellEditor(0, new EMCGoToMainTableEditor(new EMCStringDocument(), new emc.menus.inventory.menuitems.display.ItemMaster()));
        tblLines.setLookupCellEditor(2, new EMCGoToMainTableEditor(new EMCStringDocument(), new emc.menus.inventory.menuitems.display.Dimension1()));
        tblLines.setLookupCellEditor(4, new EMCGoToMainTableEditor(new EMCStringDocument(), new emc.menus.inventory.menuitems.display.Dimension2()));
        tblLines.setLookupCellEditor(3, new EMCGoToMainTableEditor(new EMCStringDocument(), new emc.menus.inventory.menuitems.display.Dimension3()));
        linesDRM.setMainTableComponent(tblLines);
        emcTablePanelUpdate linesScroll = new emcTablePanelUpdate(tblLines);

        pnlOverview.add(linesScroll, BorderLayout.CENTER);
        pnlOverview.add(pnlLineButtons(), BorderLayout.EAST);

        return pnlOverview;
    }

    /**
     * Creates the panel for buttons on the lines
     * @return the newly created panel
     */
    private emcJPanel pnlLineButtons() {
        emcJPanel pane = new emcJPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pane.add(new emcStockButton(this, true), gbc);
        y++;
        pane.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pane.setPreferredSize(new Dimension(120, 25));
        return pane;
    }
}
