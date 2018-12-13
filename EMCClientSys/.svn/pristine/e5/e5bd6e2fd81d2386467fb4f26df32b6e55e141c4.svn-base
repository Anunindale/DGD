/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.trec.output.placard;

import emc.forms.trec.output.template.*;
import emc.forms.trec.output.ergphrases.*;
import emc.forms.trec.output.cargocheck.*;
import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.app.reporttools.parameters.StringParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECPhrases;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.enums.trec.TRECSpecialPrintEnum;
import emc.forms.trec.output.treccard.TrecReportFormOkButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris
 */
public class TrecPlacardReportForm extends ReportFrame  {

    public TrecPlacardReportForm(EMCUserData userData) {
        super("TREC Placard", EnumReports.TREC_PLACARD_REPORT, userData);
        
        this.addReportParameter("operatorTel", new StringParameterObject("Operator Tel No"));
        this.addReportParameter("SpecialistTel", new StringParameterObject("Specialist Tel No"));
        
       
    }
    
     @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(TRECChemicals.class.getName());
        whereTable.setField("unNumber");
        whereInformation.add(whereTable);

   
        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }


    @Override
    protected JasperInformation createJasperInfo() {
        String fileName = "diamonds";
        String path = System.getProperty("java.class.path");//appPath.toString();
        String[] pathSplit = path.split(":");
        path = pathSplit[pathSplit.length - 1];

        File appPath = new File(path);
        try {
            appPath = appPath.getCanonicalFile().getParentFile();
        } catch (IOException e) {
            if (EMCDebug.getDebug()) {
                System.out.println("Failed to get the path for servername");
            }
        }

        path = appPath.getPath();

        String filePath = path + File.separator + fileName;

        //Windows seems to append an ';' at the end of the app path.  This is a hack to enable this code to work on Windows.
        filePath = filePath.replaceAll(";", "");
        boolean p = new File(filePath).exists();
        String fp = "";
        if (p) {
            fp = filePath;
        } else {
            //Logger.getLogger("emc").log(Level.SEVERE, "Hazardous Class Diamonds File does not exist.", this.getUserData());
        }
       
         

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/trec/placards/TRECPlacard.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.placard.TRECPlacardDS");
        jasperInfo.setReportTitle("TREC Placards");
        jasperInfo.addParameter("clasPicPath", fp);
        
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_PLACARD.toString());
    }

}
