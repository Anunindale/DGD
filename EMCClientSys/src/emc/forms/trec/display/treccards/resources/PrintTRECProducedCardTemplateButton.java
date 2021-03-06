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
public class PrintTRECProducedCardTemplateButton extends emcJButton {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public PrintTRECProducedCardTemplateButton(emcDataRelationManagerUpdate dataManager, EMCUserData userData) {
        super("Print Template");
        this.dataManager = dataManager;
        this.userData = userData;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if ((Long) dataManager.getLastFieldValueAt("recordID") != 0L) {
            Map<String, Object> parametersMap = new HashMap<String, Object>();
           

            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_SINGLE_TREC_TEMPLATE.toString());

            JasperInformation jasperInfo = new JasperInformation();
            jasperInfo.setJasperTemplate("/emc/reports/trec/template/TrecTemplate.jrxml");
            jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.customerselection.TRECProducedCardTemplateDS");
            jasperInfo.setReportTitle("TREC Template");

            EMCReportConfig config = new EMCReportConfig(EnumReports.TREC_REPORT, EnumReports.FROM_CLIENT, "Custom", parametersMap);
            cmd.setReportConfig(config);
            
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class.getName());
            query.addAnd("masterId", dataManager.getLastFieldValueAt("masterId"));
            query.addAnd("recordID", dataManager.getLastFieldValueAt("recordID"));

            ArrayList toSend = new ArrayList();
            toSend.add(query);
            

            EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.TREC_TEMPLATE_REPORT, toSend, true, userData);
            
            dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
        }
    }
}
