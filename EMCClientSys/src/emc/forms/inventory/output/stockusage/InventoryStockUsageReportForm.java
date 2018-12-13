/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.stockusage;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.app.reporttools.parameters.EMCLookupParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryStockUsageReportForm extends ReportFrame {

    public InventoryStockUsageReportForm(EMCUserData userData) {
        super("Stock Usage", EnumReports.INVENTORY_STOCK_USAGE, userData);
        addReportParameter("lastUsedDate", new DateParameterObject("Not Used Since"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable where = new BaseReportWhereTable();
        where.setTableName(InventoryItemMaster.class.getName());
        where.setField("itemReference");
        whereInformation.add(where);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimensionCombinations.class.getName(), "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/stockusage/InventoryStockUsageReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.stockusage.InventoryStockUsageReportDS");
        jasperInfo.setReportTitle("Stock Usage");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_USAGE.toString());
    }
}
