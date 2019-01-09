/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.stocktake;

import emc.app.reporttools.JasperInformation;
import emc.commands.EMCCommands;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;

/**
 *
 * @author wikus
 */
public class InventoryStocktakeReducedReportFor extends StockTakeReportSuper {

    public InventoryStocktakeReducedReportFor(EMCUserData userData) {
        super("Stock Take - Portrait", EnumReports.INVENTORY_STOCK_TAKE, userData);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/stocktake/InventoryStockTakeReducedReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS");
        jasperInfo.setReportTitle("Stock Take - Portrait");
        jasperInfo.addParameter("titleP", "Count Sheet");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_TAKE_REPORT.toString());
    }

    
}
