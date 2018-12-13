/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.inventorywithnodemand;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.transactions.InventorySummary;
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
public class InventoryWithNoDemandReportForm extends ReportFrame {

    /** Creates a new instance of  InventoryWithNoDemandReportForm*/
    public InventoryWithNoDemandReportForm(EMCUserData userData) {
        super("Inventory With No Demand", EnumReports.INVENTORY_WITH_NO_DEMAND, userData);
        addReportParameter("fromDate", new DateParameterObject("From Date"));
        addReportParameter("toDate", new DateParameterObject("To Date"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemGroup");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("ItemReference");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventorySummary.class.getName());
        whereTable.setField("dimension1");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventorySummary.class.getName());
        whereTable.setField("dimension2");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventorySummary.class.getName());
        whereTable.setField("dimension3");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryItemMaster.class.getName());
        orderTable.setField("itemGroup");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryItemMaster.class.getName());
        orderTable.setField("itemReference");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryDimension1.class.getName());
        orderTable.setField("sortCode");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryDimension3.class.getName());
        orderTable.setField("sortCode");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryDimension2.class.getName());
        orderTable.setField("sortCode");
        orderList.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventorySummary.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        tables.addTable(InventoryDimension1.class.getName(), "dimensionId", InventorySummary.class.getName(), "dimension1", true);
        tables.addTable(InventoryDimension2.class.getName(), "dimensionId", InventorySummary.class.getName(), "dimension2", true);
        tables.addTable(InventoryDimension3.class.getName(), "dimensionId", InventorySummary.class.getName(), "dimension3", true);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/inventorywithnodemand/InventoryWithNoDemandReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.inventorywithnodemandreport.InventoryWithNoDemandReportDS");
        jasperInfo.setReportTitle("Inventory With No Demand");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_INVENTORY_WITH_NO_DEMANS.toString());
    }
}
