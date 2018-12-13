/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.opendispatchorders;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.inventory.pickinglist.PickingListStatusses;
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
public class InventoryOpenDispatchOrdersForm extends ReportFrame {

    public InventoryOpenDispatchOrdersForm(EMCUserData userData) {
        super("Despatch Orders", EnumReports.INVENTORY_OPEN_DISPATCH_ORDERS, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryPickingListMaster.class.getName());
        whereTable.setField("deliveryDate");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryPickingListMaster.class.getName());
        whereTable.setField("status");
        whereTable.setFieldValue(PickingListStatusses.OPEN.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("salesOrderNo");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPCustomers.class.getName());
        whereTable.setField("customerId");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("fullyReserved");
        whereTable.setFieldValue(String.valueOf(true));
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(SOPSalesOrderMaster.class.getName(), "salesOrderNo", InventoryPickingListMaster.class.getName(), "orderId");
        tables.addTable(SOPCustomers.class.getName(), "customerId", SOPSalesOrderMaster.class.getName(), "customerNo");
        tables.addTable(InventoryPickingListLines.class.getName(), "pickingListId", InventoryPickingListMaster.class.getName(), "pickingListId");
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", InventoryPickingListLines.class.getName(), "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/opendispatchorders/InventoryOpenDispatchOrders.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.opendispatchorders.InventoryOpenDispatchOrdersDS");
        jasperInfo.setReportTitle("Despatch Orders");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_OPEN_DISPATCH_ORDERS.toString());
    }
}
