/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcMenuButton;
import emc.app.reporttools.JasperInformation;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.invoice.DebtorsInvoicePrintType;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @description : This button is used to post a customer invoice.
 *
 * @date        : 26 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class PostButton extends emcMenuButton {

    private CustomerInvoiceMasterDRM masterDRM;

    /** Creates a new instance of PostButton */
    public PostButton(CustomerInvoiceMasterDRM masterDRM) {
        super("Post", null, masterDRM.getTheForm(), 0, false);
        this.masterDRM = masterDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        
        if (EMCDialogFactory.createQuestionDialog(masterDRM.getTheForm(), "Post Invoice?", "Are you sure that you wish to post the selected invoice?") == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.POST_DEBTORS_CUSTOMER_INVOICE);

            List toSend = new ArrayList();
            toSend.add(masterDRM.getLastFieldValueAt("invCNNumber"));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterDRM.getUserData());

            //Always refresh.  If success, new status will be Posted, if not, it may be Held.
            masterDRM.refreshRecordIgnoreFocus(masterDRM.getLastRowAccessed());

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Successfully posted Invoice.");

                //Print Invoice
                EMCUserData userData = masterDRM.getUserData().copyUserData();

                userData.setUserData(0, DebtorsInvoicePrintType.TAX_INVOICE.toString());

                cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_CUSTOMER_INVOICE.toString());

                JasperInformation jasperInfo = new JasperInformation();
                jasperInfo.setJasperTemplate("/emc/reports/debtors/customerinvoice/DebtorsCustomerInvoiceReport.jrxml");
                jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerinvoice.DebtorsCustomerInvioceReportDS");
                jasperInfo.setReportTitle("Customer Invoice");

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
                query.addAnd("invCNNumber", masterDRM.getLastFieldValueAt("invCNNumber"));

                toSend = new ArrayList();
                toSend.add(query);

                EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEBTORS_CUSTOMER_INVOICE, toSend, true, userData);

                //Only printed Credit Note if Invoice is posted.
                if (DebtorsInvoiceStatus.fromString((String) masterDRM.getLastFieldValueAt("status")) == DebtorsInvoiceStatus.POSTED) {
                    //Apart from type and report title, use same parameters as previous report.
                    jasperInfo = new JasperInformation();
                    jasperInfo.setJasperTemplate("/emc/reports/debtors/customerinvoice/DebtorsCustomerInvoiceReport.jrxml");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerinvoice.DebtorsCustomerInvioceReportDS");
                    jasperInfo.setReportTitle("Delivery Note");
                    userData = userData.copyUserData();
                    userData.setUserData(0, DebtorsInvoicePrintType.DELIVERY_NOTE.toString());

                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEBTORS_CUSTOMER_INVOICE, toSend, true, userData);
                }
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to post Invoice.");
            }

        }
        //Do not attempt to do super class relation
        return;
    }
}
