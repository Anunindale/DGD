/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.debtors.DebtorsTransactions;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DebtorsTransactionTotalsReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingDetailedReportForm */
    public DebtorsTransactionTotalsReportForm(EMCUserData userData) {
        super("Transaction Totals", EnumReports.DEBTORS_TRANSACTION_TOTALS, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsTransactions.class.getName());
        whereTable.setField("transactionDate");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE.toString());
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsTransactions.class.getName());
        orderTable.setField("referenceType");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsTransactions.class.getName());
        orderTable.setField("transactionDate");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsTransactions.class.getName());
        orderTable.setField("customerId");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsTransactions.class.getName());
        orderTable.setField("referenceNumber");
        orderList.add(orderTable);



        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/transactiontotals/DebtorsTransactionTotalsReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.transactiontotals.DebtorsTransactionTotalsReportDS");
        jasperInfo.setReportTitle("Transaction Audit Trail");
        jasperInfo.setDateRangeDisplay(DebtorsTransactions.class.getName(), "transactionDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_TRANSACTION_TOTALS.toString());
    }
}
