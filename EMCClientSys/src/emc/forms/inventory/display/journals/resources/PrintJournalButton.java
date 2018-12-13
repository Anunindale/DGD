/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.wsmanager.EMCReportWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;

/**
 *
 * @author wikus
 */
public class PrintJournalButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate drm;

    public PrintJournalButton(emcDataRelationManagerUpdate drm) {
        super("Print", drm.getTheForm());
        this.addMenuItem("Journal", null, 0, false);
        this.addMenuItem("Crate Label", null, 0, false);
        this.addMenuItem("FGB Label", null, 0, false);
        this.drm = drm;
    }

    @Override
    public void executeCmd(String theCmd) {
        if (theCmd.equals("Journal")) {
            printJournal();
        } else {
            super.executeCmd(theCmd);
        }
    }

    private void printJournal() {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_JOURNALS.toString());

        ArrayList toSend = new ArrayList();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        query.addAnd("journalNumber", drm.getFieldValueAt(drm.getLastRowAccessed(), "journalNumber"));
        query.addAnd("companyId", drm.getUserData().getCompanyId());
        toSend.add(query.toString());

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/stockjournals/StockJournalsReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.stockjournalreport.StockJournalReportDS");
        jasperInfo.setReportTitle("Stock Journals");

        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.INVENTORY_STOCK_JOURNALS, toSend, drm.getUserData());
    }
}
