/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.output;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.modules.enumEMCModules;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.sop.ServerSOPMethods;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPOverdueSalesOrderReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingDetailedReportForm */
    public SOPOverdueSalesOrderReportForm(EMCUserData userData) {
        super("Overdue Sales Orders", EnumReports.SOP_OVERDUE_ORDERS, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("salesOrderNo");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemReference");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPSalesOrderMaster.class.getName());
        orderTable.setField("salesOrderNo");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryItemMaster.class.getName());
        orderTable.setField("itemReference");
        orderList.add(orderTable);

        setupDimensions(null, SOPSalesOrderLines.class.getName(), "dimension1", SOPSalesOrderLines.class.getName(), "dimension2", SOPSalesOrderLines.class.getName(), "dimension3");

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(SOPSalesOrderLines.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", SOPSalesOrderLines.class.getName(), "itemId");

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/sop/overdue/SOPOverdueSalesOrdersReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.sop.overdueorders.SOPOverdueSalesOrdersReportDS");
        jasperInfo.setReportTitle("Overdue Sales Orders");
        jasperInfo.setDateRangeDisplay(SOPSalesOrderMaster.class.getName(), "requiredDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.PRINT_SOP_OVERDUE_ORDERS.toString());
    }

    @Override
    public EMCQuery getActiveQuery() {
        EMCQuery query = super.getActiveQuery();
        List<String> statuses = new ArrayList<String>();
        statuses.add(SalesOrderStatus.CANCELLED.toString());
        statuses.add(SalesOrderStatus.INVOICED.toString());
        query.addAndInList("salsesOrderStatus", SOPSalesOrderMaster.class.getName(), statuses, true, true);
        query.addAnd("requiredDate", Functions.date2SQLString(new Date()), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.LESS_THAN);
        return query;
    }
}
