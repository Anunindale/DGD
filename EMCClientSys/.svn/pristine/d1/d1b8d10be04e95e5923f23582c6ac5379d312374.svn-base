/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.output.journals;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.journals.GLJournalMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.gl.ServerGLMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author claudette
 */
public class GLJournalReportForm extends ReportFrame {

    public GLJournalReportForm(EMCUserData userData) {
        super("Journal", EnumReports.GL_JOURNAL_REPORT, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLJournalMaster.class.getName());
        whereTable.setField("journalNumber");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLJournalMaster.class.getName());
        whereTable.setField("journalDate");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLJournalMaster.class.getName());
        whereTable.setField("journalPostedDate");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(GLJournalMaster.class.getName());
        whereTable.setField("journalStatus");
        whereTable.setFieldValue(JournalStatus.POSTED.toString());
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(GLJournalLines.class.getName(), "journalNumber", GLJournalMaster.class.getName(), "journalNumber");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/gl/journals/GLJournalReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.gl.journals.GLJournalReportDS");
        jasperInfo.setReportTitle("Journal");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.GENERAL_LEDGER.getId(), ServerGLMethods.PRINT_JOURNAL_REPORT.toString());
    }
}
