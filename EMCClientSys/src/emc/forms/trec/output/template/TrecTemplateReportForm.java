/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.trec.output.template;

import emc.forms.trec.output.ergphrases.*;
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
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
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
public class TrecTemplateReportForm extends ReportFrame  {

    public TrecTemplateReportForm(EMCUserData userData) {
        super("TREC Card Template", EnumReports.TREC_TEMPLATE_REPORT, userData);
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
        
        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(TRECTrecCardsLines.class.getName());
        whereTable.setField("properShipping");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(TRECTrecCardsMaster.class.getName(), "masterId", TRECTrecCardsLines.class.getName(), "masterId");
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }


    @Override
    protected JasperInformation createJasperInfo() {

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/trec/template/TrecTemplate.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.customerselection.TRECProducedCardTemplateDS");
        jasperInfo.setReportTitle("TREC Template");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_SINGLE_TREC_TEMPLATE.toString());
    }

}
