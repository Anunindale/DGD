/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.trec.output.cargocheck;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class CargoCheckReportForm extends ReportFrame  {

    public CargoCheckReportForm(EMCUserData userData) {
        super("Cargo Check", EnumReports.PRINT_CARGO_CHECK_REPORT, userData);
    }
    
     @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(TRECCargoCheckMaster.class.getName());
        whereTable.setField("cargoCheckNumber");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(TRECCargoCheckLines.class.getName(), "cargoCheckNumber", TRECCargoCheckMaster.class.getName(), "cargoCheckNumber");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }


    @Override
    protected JasperInformation createJasperInfo() {

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/trec/cargocheck/CargoCheckReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.cargocheck.CargoCheckReportDS");
        jasperInfo.setReportTitle("CARGO CHECK REPORT");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_CARGO_CHECK_REPORT.toString());
    }

}
