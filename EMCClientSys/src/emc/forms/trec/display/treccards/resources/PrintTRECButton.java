/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.reporttools.JasperInformation;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.trec.TRECTrecCardsLines;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import emc.reporttools.EMCReportConfig;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class PrintTRECButton extends emcJButton {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public PrintTRECButton(emcDataRelationManagerUpdate dataManager, EMCUserData userData) {
        super("Print");
        this.dataManager = dataManager;
        this.userData = userData;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if ((Long) dataManager.getLastFieldValueAt("recordID") != 0L) {

            boolean printRedLines = EMCDialogFactory.createQuestionDialog(this, "Red Lines", "Print with red lines?") == JOptionPane.YES_OPTION;
            //boolean printBoldExpDate = EMCDialogFactory.createQuestionDialog(this, "Bold Exp Date", "Print with the Exp Date in Bold?") == JOptionPane.YES_OPTION;
            //boolean printHazardZone = EMCDialogFactory.createQuestionDialog(this, "Hazard Zone", "Print Hazard Zone?") == JOptionPane.YES_OPTION;

            String fileName = "diamonds";
            String path = System.getProperty("java.class.path");//appPath.toString();
            String[] pathSplit = path.split(":");
            path = pathSplit[pathSplit.length - 1];

            File appPath = new File(path);
            try {
                appPath = appPath.getCanonicalFile().getParentFile();
            } catch (IOException e) {
                if (EMCDebug.getDebug()) {
                    System.out.println("Failed to get the path to diamonds");
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
                //Logger.getLogger("emc").log(Level.SEVERE, "Hazardous Class Diamonds File does not exist.", userData);
            }

            Map<String, Object> parametersMap = new HashMap<String, Object>();
            parametersMap.put("printLines", printRedLines);
            parametersMap.put("boldExpDate", true);
            parametersMap.put("clasPicPath", fp);
            parametersMap.put("printHazardZone", "");
            parametersMap.put("hazardPic1Path", fp);
            parametersMap.put("hazardPic2Path", fp);

            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_SINGLE_TREC.toString());

            JasperInformation jasperInfo = new JasperInformation();
            jasperInfo.setJasperTemplate("/emc/reports/trec/treccard/TrecCard.jrxml");
            jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.treccard.TRECTrecCardDS");
            jasperInfo.setReportTitle("TREC");

            EMCReportConfig config = new EMCReportConfig(EnumReports.TREC_REPORT, EnumReports.FROM_CLIENT, "Custom", parametersMap);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class.getName());
            query.addAnd("masterId", dataManager.getLastFieldValueAt("masterId"));
            query.addAnd("recordID", dataManager.getLastFieldValueAt("recordID"));

            ArrayList toSend = new ArrayList();
            toSend.add(query);
            toSend.add(printRedLines);

            EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.TREC_REPORT, toSend, config, userData);
        }
    }
}
