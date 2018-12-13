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
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.sop.ServerSOPMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class SOPOrderScheduleByItemReportForm extends ReportFrame {

    /** Creates a new instance of SOPOrderScheduleByItemReportForm. */
    public SOPOrderScheduleByItemReportForm(EMCUserData userData) {
        super("Order Schedule by Item", EnumReports.SOP_ORDER_SCHEDULE_BY_ITEM, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("requiredDate");
        whereTable.setFieldValue(Functions.date2String(Functions.nowDate(), "yyyy/MM/dd"));
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE.toString());
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("salesOrderNo");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("salsesOrderStatus");
        whereList.add(whereTable);
        
        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPCustomers.class.getName());
        whereTable.setField("deliveryRules");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("salesOrderType");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPCustomers.class.getName());
        whereTable.setField("customerName");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemReference");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("classificationClassGroup1");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderLines.class.getName());
        whereTable.setField("dimension1");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderLines.class.getName());
        whereTable.setField("dimension3");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryItemMaster.class.getName());
        orderTable.setField("itemReference");
        orderTable.setRank(0);
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPSalesOrderMaster.class.getName());
        orderTable.setField("requiredDate");
        orderTable.setRank(0);
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPSalesOrderMaster.class.getName());
        orderTable.setField("salesOrderNo");
        orderTable.setRank(0);
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPSalesOrderLines.class.getName());
        orderTable.setField("dimension1");
        orderTable.setRank(0);
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPSalesOrderLines.class.getName());
        orderTable.setField("dimension3");
        orderTable.setRank(0);
        orderList.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(SOPCustomers.class.getName(), "customerId", SOPSalesOrderMaster.class.getName(), "customerNo");
        tables.addTable(SOPSalesOrderLines.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", SOPSalesOrderLines.class.getName(), "itemId");

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/sop/orderschedule/byitem/SOPOrderScheduleByItemReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.sop.orderschedule.byitem.SOPOrderScheduleByItemReportDS");
        jasperInfo.setReportTitle("Order Schedule by Item");

        jasperInfo.setDateRangeDisplay(SOPSalesOrderMaster.class.getName(), "requiredDate");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.PRINT_ORDER_SCHEDULE_BY_ITEM.toString());
    }
}
