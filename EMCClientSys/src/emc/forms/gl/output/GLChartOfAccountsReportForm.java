/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.output;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.gl.GLChartOfAccounts;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.gl.ServerGLMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author claudette
 */
public class GLChartOfAccountsReportForm extends ReportFrame {

    public GLChartOfAccountsReportForm(EMCUserData userData) {
        super("Chart of Accounts", EnumReports.GL_CHART_OF_ACCOUNTS, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("accountNumber");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("description");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("recordType");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("accountType");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("accountLocked");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("accountClosed");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("analysisCode1");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("analysisCode2");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("analysisCode3");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("analysisCode4");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("analysisCode5");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("securityRules");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("analysisRules");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLChartOfAccounts.class.getName());
        whereTable.setField("allocationRules");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/gl/chartofaccounts/ChartOfAccountsReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.gl.chartofaccounts.GLChartOfAccountsReportDS");
        jasperInfo.setReportTitle("Chart of Accounts");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.GENERAL_LEDGER.getId(), ServerGLMethods.PRINT_CHART_OF_ACCOUNTS.toString());
    }
}
