package emc.forms.developertools.output.taskmanagement;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.util.ArrayList;
import java.util.List;

public class DevDevelopmentSummaryForm extends ReportFrame {

    public DevDevelopmentSummaryForm(EMCUserData userData) {
        super("Development Summary", EnumReports.DEV_DEVELOPMENT_SUMMARY, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List whereInformation = new ArrayList();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DevBugsTable.class.getName());
        whereTable.setField("bugNumber");
        whereInformation.add(whereTable);

        List orderInformation = new ArrayList();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DevBugsTable.class.getName());
        orderTable.setField("bugNumber");
        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/developertools/taskmanagement/DevDevelopmentSummary.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.developertools.taskmanagement.developmentsummary.DevDevelopmentSummaryDS");
        jasperInfo.setReportTitle("Development Summary");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.PRINT_DEVELOPMENT_SUMMARY_SHEET.toString());
    }

}
