/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.output.priceaudittrail;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPPriceAuditTrail;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.sop.ServerSOPMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPPriceAuditTrailApprovalReportForm extends ReportFrame {

    /** Creates a new instance of  SOPPriceAuditTrailPriceChangeReportForm*/
    public SOPPriceAuditTrailApprovalReportForm(EMCUserData userData) {
        super("Price Audit Trail - Approval", EnumReports.SOP_PRICE_AUDIT_TRAIL_APPROVAL, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPPriceAuditTrail.class.getName());
        whereTable.setField("logDate");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPSalesOrderMaster.class.getName());
        whereTable.setField("salesOrderNo");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemReference");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPCustomers.class.getName());
        whereTable.setField("customerId");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPPriceAuditTrail.class.getName());
        orderTable.setField("logDate");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPSalesOrderMaster.class.getName());
        orderTable.setField("salesOrderNo");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPCustomers.class.getName());
        orderTable.setField("customerId");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryItemMaster.class.getName());
        orderTable.setField("itemReference");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPPriceAuditTrail.class.getName());
        orderTable.setField("dimension1");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPPriceAuditTrail.class.getName());
        orderTable.setField("dimension3");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryDimension2.class.getName());
        orderTable.setField("sortCode");
        orderList.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(SOPCustomers.class.getName(), "customerId", SOPPriceAuditTrail.class.getName(), "customerId", true);
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", SOPPriceAuditTrail.class.getName(), "itemId", true);
        tables.addTable(InventoryDimension2.class.getName(), "dimensionId", SOPPriceAuditTrail.class.getName(), "dimension2", true);
        tables.addTable(SOPSalesOrderMaster.class.getName(), "recordID", SOPPriceAuditTrail.class.getName(), "sourceRecordId", false);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/sop/priceaudittrail/SOPPriceAuditTrailApprovalReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.sop.priceaudittrail.SOPPriceAuditTrailReportDS");
        jasperInfo.setReportTitle("Price Audit Trail - Approval");
        jasperInfo.setDateRangeDisplay(SOPPriceAuditTrail.class.getName(), "logDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.PRINT_APPROVAL_AUDIT_TRAIL.toString());
    }

    @Override
    public EMCUserData getUserData() {
        EMCUserData userData = super.getUserData();
        userData.setUserData(0, 2);
        return userData;
    }
}
