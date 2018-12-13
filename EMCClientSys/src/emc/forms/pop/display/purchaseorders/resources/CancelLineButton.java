/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcDialogue;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.reporttools.JasperInformation;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderLines;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.pop.display.purchaseorders.PurchaseOrderLinesDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.pop.ServerPOPMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class CancelLineButton extends emcJButton {

    private PurchaseOrderLinesDRM linesDRM;

    /** Creates a new instance of CancelLineButton. */
    public CancelLineButton(PurchaseOrderLinesDRM linesDRM) {
        super("Cancel Line");
        linesDRM.setCancelButton(this);
        this.linesDRM = linesDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        emcDialogue dialog = new emcDialogue("Are You Sure", "Cancel Line?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (dialog.getDialogueResult() == 0) {
            EMCUserData userData = linesDRM.getUserData();

            if (!Functions.checkBlank(linesDRM.getLastFieldValueAt("blanketOrderLineId"))) {
                userData.setUserData(3, EMCDialogFactory.createQuestionDialog(linesDRM.getTheForm(), "Update Blanket Order?", "Do you want the quantity on the selected line to be added back to the Blanket Order from which it was released?") == JOptionPane.YES_OPTION);
            }
            
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.CANCEL_PURCHASEORDERLINE.toString());

            List toSend = new ArrayList();
            toSend.add(linesDRM.getRowCache(linesDRM.getLastRowAccessed()));

            int lastRow = linesDRM.getLastRowAccessed();

            Date now = Functions.nowDate();
            String purchaseOrderId = (String) linesDRM.getFieldValueAt(lastRow, "purchaseOrderId");

            List ret = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (ret != null && ret.size() > 1 && ret.get(1) instanceof Boolean && (Boolean) ret.get(1)) {
                if (EMCDialogFactory.createQuestionDialog(linesDRM.getTheForm(), "Print Cancelled Purchase Order?", "Do you want to print the cancelled Purchase Order?") == JOptionPane.YES_OPTION) {
                    cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_CANCELLED_PO.toString());

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setReportTitle("Cancelled Purchase Order");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");

                    jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderPostingReport.jrxml");

                    jasperInfo.addParameter("cancel", "CANCELLED");

                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPCancelledPurchaseOrderLines.class.getName());
                    query.addAnd("purchaseOrderId", purchaseOrderId);
                    query.addAnd("createdDate", Functions.date2String(now, "yyyy-MM-dd"), emc.enums.emcquery.EMCQueryConditions.GREATER_THAN_EQ);
                    query.addAnd("createdTime", Functions.date2String(now, "HH:mm"), emc.enums.emcquery.EMCQueryConditions.GREATER_THAN_EQ);

                    toSend = new ArrayList();
                    toSend.add(query.toString());

                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.CANCELLED_PO, toSend, userData);
                }

                emcDataRelationManagerUpdate master = linesDRM.getHeaderTable();
                master.refreshRecordIgnoreFocus(-1);
            }
        }

    }
}
