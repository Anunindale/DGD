/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.trec.output.ergphrases;

import emc.forms.trec.output.cargocheck.*;
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
import emc.entity.trec.TRECPhrases;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris
 */
public class ErgPhrasesReportForm extends ReportFrame  {

    public ErgPhrasesReportForm(EMCUserData userData) {
        super("ERG Phrases", EnumReports.PRINT_ERG_PHRASES_REPORT, userData);
    }
    
     @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(TRECPhrases.class.getName());
        whereTable.setField("ergNumber");
        whereTable.setWhereCondition(EMCQueryConditions.LIKE.toString());
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }


    @Override
    protected JasperInformation createJasperInfo() {

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/trec/ergtrecphrases/ERGPhrases.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.ergphrases.TRECErgPhrasesDS");
        jasperInfo.setReportTitle("TREC ERG PHRASES REPORT");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_ERG_PHRASES.toString());
    }

}
