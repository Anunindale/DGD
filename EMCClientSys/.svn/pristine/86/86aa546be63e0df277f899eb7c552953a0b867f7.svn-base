/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.output.cratelabels.resources;

import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportOKButton;
import emc.app.reporttools.barcodeprinter.StaticBarcodePrinting;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.pop.ServerPOPMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class POPCrateLabelsOKButton extends ReportOKButton {

    private ReportFrame reportFrame;
    private EMCUserData userData;

    public POPCrateLabelsOKButton(ReportFrame reportFrame, EMCUserData userData) {
        super(reportFrame);
        this.reportFrame = reportFrame;
        this.userData = userData;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        doWork(null); //will get it from the active query reportframe should be available
    }

    /**
     * This mehod is created to allow ProductionDocumentPrintHelper to also
     * access the same code
     */
    public void doWork(String postMasterId) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_CRATE_LABELS.toString());
        if (reportFrame != null) {
            EMCQuery qu = reportFrame.getActiveQuery();
            try {
                postMasterId = EMCQuery.getFieldValueFromQuery("postMasterId", qu.toString());
            } catch (Exception ex) {
                if (EMCDebug.getDebug()) {
                    utilFunctions.logMessage(Level.WARNING, "Failed to  get Post Master ID from query.", userData);
                }
            }
        }
        List toSend = new ArrayList();
        toSend.add(postMasterId);
        List retList = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (retList.size() > 1) {
            String result = retList.get(1).toString();
            result = new EMCXMLHandler().reinstateNewLines(result);
            StaticBarcodePrinting.print(EnumReports.POP_CRATE_LABELS, reportFrame == null ? null : reportFrame.getQueryName(), result, userData);
        }
    }
}
