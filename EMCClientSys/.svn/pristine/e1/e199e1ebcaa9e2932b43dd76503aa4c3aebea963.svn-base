/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditnotes.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.invoice.DebtorsInvoicePrintType;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CreditNotePrintButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate dataManager;

    public CreditNotePrintButton(BaseInternalFrame form) {
        super("Print Credit Note", form);
        this.dataManager = (emcDataRelationManagerUpdate) form.getDataManager();
        //this.addMenuItem("Original", null, 0, false);
        this.addMenuItem("Copy", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        EMCUserData userData = dataManager.getUserData().copyUserData();
        if (theCmd.equals("Original")) {
            userData.setUserData(0, DebtorsInvoicePrintType.CREDIT_NOTE.toString());
        } else if (theCmd.equals("Copy")) {
            userData.setUserData(0, DebtorsInvoicePrintType.CREDIT_NOTE_COPY.toString());
        }
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_CUSTOMER_INVOICE.toString());
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/customerinvoice/DebtorsCustomerInvoiceReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerinvoice.DebtorsCustomerInvioceReportDS");
        jasperInfo.setReportTitle("Credit Note");
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
        query.addAnd("invCNNumber", dataManager.getLastFieldValueAt("invCNNumber"));
        List toSend = new ArrayList();
        toSend.add(query);//Record is updated on the server.  Wait for the report to finish before refreshing.
        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEBTORS_CUSTOMER_INVOICE, toSend, true, userData);
        dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
    }
}
