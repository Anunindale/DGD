/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.journal;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.base.journals.JournalStatus;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryJournalMovementSummaryReportFrame extends ReportFrame {

    public InventoryJournalMovementSummaryReportFrame(EMCUserData userData) {
        super("Journal Movement - Summary", EnumReports.INVENTORY_MOVEMENT_JOURNAL_SUMMARY, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryJournalMaster.class.getName());
        whereTable.setField("journalPostedDate");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemReference");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryJournalMaster.class.getName());
        whereTable.setField("journalStatus");
        whereTable.setFieldValue(JournalStatus.POSTED.toString());
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE_ALLOW_BLANK.toString());
        whereInformation.add(whereTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", InventoryJournalLines.class.getName(), "itemId");
        tables.addTable(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/journals/InventoryJournalMovementSummaryReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.journals.InventoryMovementJournalSummaryDS");
        jasperInfo.setReportTitle("Journal Movement - Summary");
        jasperInfo.setDateRangeDisplay(InventoryJournalMaster.class.getName(), "journalPostedDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_MOVEMENT_JOURNAL_SUMMARY.toString());
    }
}
