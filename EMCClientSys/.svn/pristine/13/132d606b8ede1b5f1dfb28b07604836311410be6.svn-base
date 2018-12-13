/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.transferjournals;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
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
public class InventoryTransferJournalsReportForm extends ReportFrame {

    /**
     * Creates a new instance of InventoryWarehouseLocationTransferReportForm.
     */
    public InventoryTransferJournalsReportForm(EMCUserData userData) {
        super("Warehouse Location Transfer", EnumReports.INVENTORY_WAREHOUSE_LOCATION_TRANSFER, userData);

        addReportParameter("printToWarehouse", new BooleanParameterObject("Print To Warehouse"));
        addReportParameter("printToLocation", new BooleanParameterObject("Print To Location"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryJournalMaster.class.getName());
        whereTable.setField("journalNumber");
        whereTable.setFieldValue(null);
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryJournalMaster.class.getName());
        orderTable.setField("journalNumber");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryJournalLines.class.getName());
        orderTable.setField("lineNo");
        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryJournalLines.class.getName(), "journalNumber", InventoryJournalMaster.class.getName(), "journalNumber");
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", InventoryJournalLines.class.getName(), "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/transferjournals/InventoryTransferJournalsReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.warehouselocationtransfer.InventoryWarehouseLocationTransferReportDS");
        jasperInfo.setReportTitle("Warehouse Location Transfer");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_WAREHOUSE_LOCATION_TRANSFER.toString());
    }
}
