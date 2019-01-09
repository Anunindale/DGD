/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.grnreprint;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.reporttools.barcodeprinter.StaticBarcodePrinting;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportPrintOptions;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.grnreprint.POPGRNReprintTemp;
import emc.entity.pop.grnreprint.datasource.POPGRNReprintTempDS;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.menus.base.menuItems.display.ReportPrintOptionMenu;
import emc.menus.inventory.menuitems.display.Journals;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.pop.menuitems.display.Suppliers;
import emc.methods.pop.ServerPOPMethods;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class POPGRNReprintTempForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData userData;

    public POPGRNReprintTempForm(EMCUserData userData) {
        super("GRN Label Reprint", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 300);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(), new POPGRNReprintTempDS(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemReference");
            dataRelation.setFormTextId2("itemDescription");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("GRN Label Print", tablePane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(buttonPane(), BorderLayout.EAST);
        this.add(thePanel);
    }

    private emcJPanel buttonPane() {
        emcJButton btnPrint = new emcJButton("Print Labels") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPGRNReprintTemp.class.getName());
                query.addAnd("companyId", userData.getCompanyId());
                query.addAnd("createdBy", userData.getUserName());
                List toSend = new ArrayList();
                toSend.add(query.toString());
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.REPRINT_LABLES.toString());
                String grnString = (String) EMCWSManager.executeGenericWS(cmd, toSend, userData).get(1);
                grnString = new EMCXMLHandler().reinstateNewLines(grnString);
                StaticBarcodePrinting.print(EnumReports.POP_LABELS, null, grnString, userData);
                if (!Functions.checkBlank(grnString)) {
                    dataRelation.refreshData();
                }
            }
        };
        emcJButton btnOptions = new emcJButton("Options") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportPrintOptions.class.getName());
                query.addAnd("createdBy", userData.getUserName());
                query.addAnd("reportId", EnumReports.POP_LABELS.toString());
                query.addAnd("companyId", userData.getCompanyId());

                userData.setUserData(0, query.toString());
                userData.setUserData(1, query.getCountQuery());
                userData.setUserData(2, EnumReports.POP_LABELS.toString());

                ReportPrintOptionMenu repM = new ReportPrintOptionMenu();
                repM.setDoNotOpenForm(false);
                POPGRNReprintTempForm.this.getDeskTop().createAndAdd(repM, -1, -1, userData, POPGRNReprintTempForm.this, -1001);
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnPrint);
        buttonList.add(btnOptions);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel tablePane() {
        List keys = new ArrayList();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("batch");
        keys.add("serial");
        keys.add("width");
        keys.add("grnumber");
        keys.add("supplierCode");
        keys.add("suppName");
        keys.add("dateRecieved");
        keys.add("numLables");
        keys.add("standardLocation");

        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnEditable("itemDescription", false);
        table.setColumnEditable("suppName", false);
        setLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }

    private void setLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpItem = EMCItemLookupFactory.createItemLookup(userData);
        EMCLookupJTableComponent lkpDim1 = EMCDimensionLookupFactory.createDimension1JTableLookup(userData);
        EMCLookupJTableComponent lkpDim2 = EMCDimensionLookupFactory.createDimension2JTableLookup(userData);
        EMCLookupJTableComponent lkpDim3 = EMCDimensionLookupFactory.createDimension3JTableLookup(userData);
        EMCLookupJTableComponent lkpBatch = EMCDimensionLookupFactory.createBatchJTableLookup(userData);
        EMCLookupJTableComponent lkpSerial = EMCDimensionLookupFactory.createSerialJTableLookup(userData);
        List keys = new ArrayList();
        keys.add("journalNumber");
        keys.add("journalDescription");
        keys.add("journalDate");
        EMCLookupPopup grnPop = new EMCLookupPopup(new InventoryJournalMaster(), "journalNumber", keys, userData);
        EMCLookupJTableComponent lkpGRN = new EMCLookupJTableComponent(new Journals());
        lkpGRN.setPopup(grnPop);
        keys = new ArrayList();
        keys.add("supplierId");
        keys.add("supplierName");
        EMCLookupPopup suppPop = new EMCLookupPopup(new POPSuppliers(), "supplierId", keys, userData);
        EMCLookupJTableComponent lkpSupp = new EMCLookupJTableComponent(new Suppliers());
        lkpSupp.setPopup(suppPop);

        EMCLookupJTableComponent lkpLocation = new EMCLookupJTableComponent(new LocationMenu());
        lkpLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", userData));
        //Only show unique locations
        lkpLocation.getTheQuery().addGroupBy("locationId");

        table.setLookupToColumn("itemReference", lkpItem);
        table.setLookupToColumn("dimension1", lkpDim1);
        table.setLookupToColumn("dimension2", lkpDim2);
        table.setLookupToColumn("dimension3", lkpDim3);
        table.setLookupToColumn("batch", lkpBatch);
        table.setLookupToColumn("serial", lkpSerial);
        table.setLookupToColumn("grnumber", lkpGRN);
        table.setLookupToColumn("supplierCode", lkpSupp);
        table.setLookupToColumn("standardLocation", lkpSupp);

        GRNReprintLRM lrm = new GRNReprintLRM();
        lrm.addLookup(lkpItem, "item");
        lrm.addLookup(lkpDim1, "dimension1");
        lrm.addLookup(lkpDim2, "dimension2");
        lrm.addLookup(lkpDim3, "dimension3");
        lrm.initializeLookups();
    }
}
