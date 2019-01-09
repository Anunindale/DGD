/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.output.treclowhazard;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author claudette
 */
public class TRECLowHazardReportForm extends ReportFrame {

    public TRECLowHazardReportForm(EMCUserData userData) {
        super("TREC Low Hazard", EnumReports.TREC_LOW_HAZARD_REPORT, userData);
        this.addReportParameter("printLines", new BooleanParameterObject("Print Red Lines"));
        this.addReportParameter("boldExpDate", new BooleanParameterObject("Print Exp Date in Bold"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(TRECTrecCardsMaster.class.getName());
        whereTable.setField("masterId");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(TRECTrecCardsLines.class.getName());
        whereTable.setField("unNumber");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(TRECTrecCardsMaster.class.getName(), "masterId", TRECTrecCardsLines.class.getName(), "masterId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/trec/treclowhazard/TrecLowHazardReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.treccard.TRECTrecCardDS");
        jasperInfo.setReportTitle("TREC Low Hazard");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_TREC.toString());
    }
}
