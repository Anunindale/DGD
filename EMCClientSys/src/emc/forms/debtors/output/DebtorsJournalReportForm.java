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
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : Report frame for the Debtors Journal report.
 *
 * @date        : 12 Aug 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsJournalReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsJournalReportForm */
    public DebtorsJournalReportForm(EMCUserData userData) {
        super("Debtors Journal", EnumReports.DEBTORS_JOURNAL_REPORT, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsJournalMaster.class.getName());
        whereTable.setField("journalNumber");
       
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/journals/DebtorsJournalsReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.journals.DebtorsJournalsReportDS");
        jasperInfo.setReportTitle("Debtors Journals");
        jasperInfo.setDateRangeDisplay(DebtorsTransactions.class.getName(), "transactionDate");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_DEBTORS_JOURNALS.toString());
    }
}
