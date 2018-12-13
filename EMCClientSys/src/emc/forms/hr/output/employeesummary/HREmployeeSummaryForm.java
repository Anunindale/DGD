/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.output.employeesummary;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.hr.ServerHRMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HREmployeeSummaryForm extends ReportFrame {

    public HREmployeeSummaryForm(EMCUserData userData) {
        super("Employment Summary", EnumReports.HR_EMPLOYMENT_SUMMARY, userData);
        this.addReportParameter("fromDate", new DateParameterObject("From Date"));
        this.addReportParameter("toDate", new DateParameterObject("To Date"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(BaseEmployeeTable.class.getName());
        whereTable.setField("employeeNumber");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/hr/employeesummary/HREmployeeSummaryReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.hr.employeesummary.HREmployeeSummaryDS");
        jasperInfo.setReportTitle("Employment Summary");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.HR.getId(), ServerHRMethods.PRINT_EMPLOYMENT_SUMMARY.toString());
    }
}
