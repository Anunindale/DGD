/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.cancelpurchaseorders.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.reporttools.JasperInformation;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderLines;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.methods.pop.ServerPOPMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @Name         : PrintButton
 *
 * @Date         : 04 Jun 2009
 * 
 * @Description  : This button is used to print a cancelled Purchase Order.
 *
 * @author       : riaan
 */
public class PrintButton extends emcJButton {

    private emcDataRelationManagerUpdate cancelledPOMasterDRM;

    /** Creates a new instance of PrintButton. */
    public PrintButton(emcDataRelationManagerUpdate cancelledPOMasterDRM) {
        super("Print");
        this.cancelledPOMasterDRM = cancelledPOMasterDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if (EMCDialogFactory.createQuestionDialog(cancelledPOMasterDRM.getTheForm(), "Print Cancelled Purchase Order?", "Do you want to print the cancelled Purchase Order?") == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_CANCELLED_PO.toString());

            JasperInformation jasperInfo = new JasperInformation();
            jasperInfo.setReportTitle("Cancelled Purchase Order");
            jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");

            jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderPostingReport.jrxml");

            jasperInfo.addParameter("cancel", "CANCELLED");

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPCancelledPurchaseOrderLines.class.getName());
            query.addAnd("purchaseOrderId", cancelledPOMasterDRM.getFieldValueAt(cancelledPOMasterDRM.getLastRowAccessed(), "purchaseOrderId"));

            List toSend = new ArrayList();
            toSend.add(query.toString());

            EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.CANCELLED_PO, toSend, cancelledPOMasterDRM.getUserData());
        }
    }
}
