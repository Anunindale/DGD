/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class PrintPOButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate dataManager;

    /** Creates a new instance of PrintPOButton. */
    public PrintPOButton(BaseInternalFrame form) {
        super("Print PO", form);
        this.dataManager = (emcDataRelationManagerUpdate) form.getDataManager();
        this.addMenuItem("Copy", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        EMCUserData userData = dataManager.getUserData().copyUserData();
        
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_CUSTOMER_INVOICE.toString());
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/pop/customerinvoice/DebtorsCustomerInvoiceReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerinvoice.DebtorsCustomerInvioceReportDS");
        jasperInfo.setReportTitle("Customer Invoice");
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        query.addAnd("invCNNumber", dataManager.getLastFieldValueAt("invCNNumber"));
        List toSend = new ArrayList();
        toSend.add(query);
        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEBTORS_CUSTOMER_INVOICE, toSend, userData);

    }
}
