/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.output.customerlables;

import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportOKButton;
import emc.app.reporttools.barcodeprinter.StaticBarcodePrinting;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.sop.ServerSOPMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class OKButton extends ReportOKButton {

    private ReportFrame reportFrame;
    private EMCUserData userData;

    public OKButton(ReportFrame reportFrame, EMCUserData userData) {
        super(reportFrame);
        this.reportFrame = reportFrame;
        this.userData = userData;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        EMCQuery qu = reportFrame.getActiveQuery();
        String ct = (String) reportFrame.getReportParameterValue("customerType");
        doWork(qu, ct);
    }

    /**
     * Print Customer Lables
     *
     * @param query To Select SOPCustomer entities.
     * @param custType SOPCustomerLablesType enum value
     */
    public void doWork(EMCQuery query, String custType) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.PRINT_CUSTOMER_LABLES.toString());
        List toSend = new ArrayList();
        toSend.add(query);
        toSend.add(custType);
        List retList = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (retList.size() > 1) {
            String result = retList.get(1).toString();
            result = new EMCXMLHandler().reinstateNewLines(result);
            StaticBarcodePrinting.print(EnumReports.SOP_CUSTOMER_LABLES, reportFrame == null ? null : reportFrame.getQueryName(), result, userData);
        }
    }
}
