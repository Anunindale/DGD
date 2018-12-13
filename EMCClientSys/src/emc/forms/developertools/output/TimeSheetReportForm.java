/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.output;

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
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class TimeSheetReportForm extends ReportFrame {

    public TimeSheetReportForm(EMCUserData userData) {
        super("Time Sheet", EnumReports.DEV_TOOLS_TIME_SHEET, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DevBugsTable.class.getName());
        whereTable.setField("responsible");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE_ALLOW_BLANK.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DevBugsTable.class.getName());
        whereTable.setField("completed");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE.toString());
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DevBugsTable.class.getName());
        orderTable.setField("responsible");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DevBugsTable.class.getName());
        orderTable.setField("completed");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DevBugsTable.class.getName());
        orderTable.setField("startTime");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DevBugsTable.class.getName());
        orderTable.setField("timeTaken");
        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/developertools/TimeSheetReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.developertools.timesheet.TimeSheetReportDS");
        jasperInfo.setReportTitle("Time Sheet");
        jasperInfo.setDateRangeDisplay(DevBugsTable.class.getName(), "completed");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.PRINT_TIME_SHEET.toString());
    }
}
