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
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : Report frame for the Debtors Balance report.
 *
 * @date        : 01 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsBalanceReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsBalanceReportForm */
    public DebtorsBalanceReportForm(EMCUserData userData) {
        super("Debtors Balance", EnumReports.DEBTORS_BALANCE_REPORT, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        String transactionsClass = DebtorsTransactions.class.getName();

        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(transactionsClass);
        whereTable.setField("transactionDate");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE.toString());

        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();

        jasperInfo.setJasperTemplate("/emc/reports/debtors/balance/DebtorsBalanceReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.debtorsbalance.DebtorsBalanceReportDS");
        jasperInfo.setReportTitle("Debtors Balance");
        jasperInfo.setDateRangeDisplay(DebtorsTransactions.class.getName(), "transactionDate");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_DEBTORS_BALANCE.toString());
    }

    @Override
    public EMCQuery getActiveQuery() {
        EMCQuery query = super.getActiveQuery().copyQuery();

        query.addField("referenceType");
        query.addFieldAggregateFunction("debit", "SUM");
        query.addFieldAggregateFunction("credit", "SUM");
        
        query.addGroupBy("referenceType");

        return query;
    }
}
