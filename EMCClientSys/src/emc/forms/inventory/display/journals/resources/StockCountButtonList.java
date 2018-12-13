/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.menus.inventory.menuitems.display.GenerateStockCountJournalMenu;
import emc.methods.inventory.ServerInventoryMethods;
import emc.reporttools.EMCReportDimensionSetup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class StockCountButtonList extends emcMenuButtonList {

    private emcDataRelationManagerUpdate drm;

    public StockCountButtonList(BaseInternalFrame form) {
        super("Stock Count", form);
        this.drm = (emcDataRelationManagerUpdate) form.getDataManager();
        GenerateStockCountJournalMenu generateMenu = new GenerateStockCountJournalMenu();
        generateMenu.setDoNotOpenForm(false);
        this.addMenuItem("Generate", generateMenu, 0, false);
        this.addMenuItem("Count Sheet", null, 1, false);
        this.addMenuItem("Variance", null, 2, false);
        this.addMenuItem("Recount Sheet", null, 3, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        List toSend = new ArrayList();
        JasperInformation jasperInfo;
        EMCCommandClass cmd;
        EMCQuery query;
        if (theCmd.equals("Count Sheet")) {
            cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_TAKE_REPORT.toString());

            jasperInfo = new JasperInformation();
            jasperInfo.setJasperTemplate("/emc/reports/inventory/stocktake/InventoryStockTakeReport.jrxml");
            jasperInfo.setXmlNodePath("/emcmsg/emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS");
            jasperInfo.setReportTitle("Stock Take");
            jasperInfo.addParameter("titleP", "Count Sheet");

            query = new InventoryStocktakeCaptureDS().getQuery();
            query.removeAnd("journalNumber", InventoryJournalLines.class.getName());
            query.addAnd("journalNumber", drm.getLastFieldValueAt("journalNumber"), InventoryJournalLines.class.getName());

            toSend.add(query);
            toSend.add(new EMCReportDimensionSetup("StockTake", InventoryJournalLines.class.getName(), "dimension1", InventoryJournalLines.class.getName(), "dimension2", InventoryJournalLines.class.getName(), "dimension3"));

            EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.INVENTORY_STOCK_TAKE, toSend, drm.getUserData());
        } else if (theCmd.equals("Variance")) {
            cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_TAKE_VARIANCE.toString());

            jasperInfo = new JasperInformation();
            jasperInfo.setJasperTemplate("/emc/reports/inventory/stocktake/InventoryStockTakeVarianceReport.jrxml");
            jasperInfo.setXmlNodePath("/emcmsg/emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS");
            jasperInfo.setReportTitle("Stock Take Variance");

            query = new InventoryStocktakeCaptureDS().getQuery();
            query.removeAnd("journalNumber", InventoryJournalLines.class.getName());
            query.addAnd("journalNumber", drm.getLastFieldValueAt("journalNumber"), InventoryJournalLines.class.getName());

            toSend.add(query);
            toSend.add(new EMCReportDimensionSetup("StockTake", InventoryJournalLines.class.getName(), "dimension1", InventoryJournalLines.class.getName(), "dimension2", InventoryJournalLines.class.getName(), "dimension3"));

            EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.INVENTORY_STOCK_TAKE_VARIANCE, toSend, drm.getUserData());
        } else if (theCmd.equals("Recount Sheet")) {
            cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_TAKE_RECOUNT_REPORT.toString());

            jasperInfo = new JasperInformation();
            jasperInfo.setJasperTemplate("/emc/reports/inventory/stocktake/InventoryStockTakeReport.jrxml");
            jasperInfo.setXmlNodePath("/emcmsg/emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS");
            jasperInfo.setReportTitle("Stock Take");
            jasperInfo.addParameter("titleP", "Recount Sheet");

            query = new InventoryStocktakeCaptureDS().getQuery();
            query.removeAnd("journalNumber", InventoryJournalLines.class.getName());
            query.addAnd("journalNumber", drm.getLastFieldValueAt("journalNumber"), InventoryJournalLines.class.getName());

            toSend.add(query);
            toSend.add(new EMCReportDimensionSetup("StockTake", InventoryJournalLines.class.getName(), "dimension1", InventoryJournalLines.class.getName(), "dimension2", InventoryJournalLines.class.getName(), "dimension3"));

            EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.INVENTORY_STOCK_TAKE_RECOUNt, toSend, drm.getUserData());
        } else {
            super.executeCmd(theCmd);
        }
    }
}
