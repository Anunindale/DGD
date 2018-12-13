/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.batchconsolidation;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryBatchConsolidationLines;
import emc.entity.inventory.InventoryBatchConsolidationMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BatchConsolidationPickingListReportForm extends ReportFrame {

    public BatchConsolidationPickingListReportForm(EMCUserData userData) {
        super("Batch Consolidation Picking List", EnumReports.INVENTORY_BATCH_CONSOLIDATION_PICKING_LIST, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable where = new BaseReportWhereTable();
        where.setTableName(InventoryBatchConsolidationMaster.class.getName());
        where.setField("consolidationNumber");
        whereInformation.add(where);

        where = new BaseReportWhereTable();
        where.setTableName(InventoryBatchConsolidationMaster.class.getName());
        where.setField("warehouse");
        whereInformation.add(where);

        where = new BaseReportWhereTable();
        where.setTableName(InventoryBatchConsolidationMaster.class.getName());
        where.setField("location");
        whereInformation.add(where);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable order = new BaseReportOrderTable();
        order.setTableName(InventoryBatchConsolidationMaster.class.getName());
        order.setField("consolidationNumber");
        orderInformation.add(order);

        order = new BaseReportOrderTable();
        order.setTableName(InventoryBatchConsolidationLines.class.getName());
        order.setField("lineNo");
        orderInformation.add(order);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryBatchConsolidationLines.class.getName(), "consolidationNumber", InventoryBatchConsolidationMaster.class.getName(), "consolidationNumber");
        tables.addTable(InventoryJournalLines.class.getName(), "recordID", InventoryBatchConsolidationLines.class.getName(), "transferJournalLineRecordId");
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", InventoryJournalLines.class.getName(), "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/batchconsolidation/BatchConsolidationPickingListReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.batchconsolidation.BatchConsolidationPickingListReportDS");
        jasperInfo.setReportTitle("Batch Consolidation Picking List");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_BATCH_CONSOLIDATION_PICKING_LIST.toString());
    }
}
