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
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsTransactions;
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
public class SOPCommissionSummaryReport extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingDetailedReportForm */
    public SOPCommissionSummaryReport(EMCUserData userData) {
        super("Commission By Rep", EnumReports.SOP_COMMISSION_SUMMARY, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsTransactions.class.getName());
        whereTable.setField("transactionDate");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsTransactions.class.getName());
        whereTable.setField("salesRep");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsTransactions.class.getName());
        whereTable.setField("customerId");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsTransactions.class.getName());
        whereTable.setField("referenceNumber");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsTransactions.class.getName());
        orderTable.setField("salesRep");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsTransactions.class.getName());
        orderTable.setField("customerId");
        orderList.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(SOPSalesOrderMaster.class.getName(), "salesOrderNo", DebtorsTransactions.class.getName(), "salesOrderNo", true);
        tables.addTable(DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber", DebtorsTransactions.class.getName(), "referenceNumber", true);
        tables.addTable(DebtorsCreditNoteLines.class.getName(), "invCNNumber", DebtorsTransactions.class.getName(), "referenceNumber", true);
        tables.addTable(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsTransactions.class.getName(), "referenceNumber", true);
        tables.addTable(DebtorsCreditNoteMaster.class.getName(), "invCNNumber", DebtorsTransactions.class.getName(), "referenceNumber", true);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/sop/commission/SOPCommissionSummaryReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.sop.commission.SOPCommissionReportDS");
        jasperInfo.setReportTitle("Commission By Rep");
        jasperInfo.setDateRangeDisplay(DebtorsTransactions.class.getName(), "transactionDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.PRINT_COMMISSION_SUMMARY_REPORT.toString());
    }

    @Override
    public EMCUserData getUserData() {
        EMCUserData userData = super.getUserData();
        userData.setUserData(0, 1);
        return userData;
    }
}
