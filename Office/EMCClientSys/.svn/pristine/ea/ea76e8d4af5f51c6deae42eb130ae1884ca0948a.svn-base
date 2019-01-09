/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.journals.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.reporttools.JasperInformation;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 *
 * @author riaan
 */
public class PrintButton extends emcJButton {

    private emcDataRelationManagerUpdate drm;
    
    /** Creates a new instance of PrintButton. */
    public PrintButton(emcDataRelationManagerUpdate drm) {
        super("Print");
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_DEBTORS_JOURNALS.toString());

        ArrayList toSend = new ArrayList();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);
        query.addAnd("journalNumber", drm.getFieldValueAt(drm.getLastRowAccessed(), "journalNumber"));
        query.addAnd("companyId", drm.getUserData().getCompanyId());
        toSend.add(query);

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/journals/DebtorsJournalsReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.journals.DebtorsJournalsReportDS");
        jasperInfo.setReportTitle("Debtors Journals");

        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEBTORS_JOURNAL_REPORT, toSend, drm.getUserData());
    }
}
