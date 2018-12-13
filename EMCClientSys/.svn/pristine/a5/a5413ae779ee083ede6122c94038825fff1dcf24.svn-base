/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.output.disciplinaryaction;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.hr.HRDiciplaneryActions;
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
public class HRDisciplinaryActionReportForm extends ReportFrame {

    public HRDisciplinaryActionReportForm(EMCUserData userData) {
        super("Disciplinary Action", EnumReports.HR_DISCIPLINARY_ACTION, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(HRDiciplaneryActions.class.getName());
        whereTable.setField("offenceDate");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(HRDiciplaneryActions.class.getName());
        whereTable.setField("employeeNumber");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(BaseEmployeeTable.class.getName());
        whereTable.setField("gender");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(BaseEmployeeTable.class.getName());
        whereTable.setField("race");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(HRDiciplaneryActions.class.getName());
        whereTable.setField("diciplaneryResult");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(HRDiciplaneryActions.class.getName());
        whereTable.setField("status");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(BaseEmployeeTable.class.getName());
        orderTable.setField("gender");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(BaseEmployeeTable.class.getName());
        orderTable.setField("race");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(HRDiciplaneryActions.class.getName());
        orderTable.setField("employeeNumber");
        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(BaseEmployeeTable.class.getName(), "employeeNumber", HRDiciplaneryActions.class.getName(), "employeeNumber");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/hr/disciplinaryaction/HRDisciplinaryActionReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.hr.disciplinaryaction.HRDisciplinaryActionReportDS");
        jasperInfo.setReportTitle("Disciplinary Action");
        jasperInfo.setDateRangeDisplay(HRDiciplaneryActions.class.getName(), "offenceDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.HR.getId(), ServerHRMethods.PRINT_DISCIPLINARY_ACTION.toString());
    }
}
