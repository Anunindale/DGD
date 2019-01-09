/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.output.treccard;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECSpecialPrintEnum;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class TRECTrecCardReportForm extends ReportFrame {

    public TRECTrecCardReportForm(EMCUserData userData) {
        super("TREC", EnumReports.TREC_REPORT, userData);
        this.addReportParameter("printLines", new BooleanParameterObject("Print Red Lines"));
        this.addReportParameter("boldExpDate", new BooleanParameterObject("Print Exp Date in Bold"));
        String[] printHazZone = new String[] {TRECSpecialPrintEnum.NONE.toString(), TRECSpecialPrintEnum.PRINT_DIAMOND.toString(), TRECSpecialPrintEnum.PRINT_PLACCARD.toString()};
        this.addReportParameter("printHazardZone", new EMCJComboBoxParameterObject("Print Placard",printHazZone));
        this.setOkButton(new TrecReportFormOkButton(this));
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
        jasperInfo.setJasperTemplate("/emc/reports/trec/treccard/TrecCard.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.treccard.TRECTrecCardDS");
        jasperInfo.setReportTitle("TREC");
        jasperInfo.addParameter("clasPicPath", fp);
        jasperInfo.addParameter("hazardPic1Path", fp);
        jasperInfo.addParameter("hazardPic2Path", fp);
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_TREC.toString());

    }
}
