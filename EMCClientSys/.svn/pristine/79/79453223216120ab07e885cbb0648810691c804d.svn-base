/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.stocktakevariance;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.JournalStatus;
import emc.enums.inventory.journals.StockTakeVarianceType;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryStocktakeVarianceByWarehouseReportForm extends ReportFrame {

    public InventoryStocktakeVarianceByWarehouseReportForm(EMCUserData userData) {
        super("Stock Take Variance By Warehouse", EnumReports.INVENTORY_STOCK_TAKE_VARIANCE_BY_WAREHOUSE, userData);
        addReportParameter("reportType", new EMCJComboBoxParameterObject("Variance Type", StockTakeVarianceType.values()));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(BaseJournalDefinitionTable.class.getName());
        whereTable.setField("journalType");
        whereTable.setFieldValue(InventoryJournalTypes.STOCKTAKE.toString());
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryJournalMaster.class.getName());
        whereTable.setField("journalStatus");
        whereTable.setWhereCondition("NOT");
        whereTable.setFieldValue(JournalStatus.POSTED.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryJournalMaster.class.getName());
        whereTable.setField("journalNumber");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable order = new BaseReportOrderTable();
        order.setTableName(InventoryJournalLines.class.getName());
        order.setField("warehouse");
        orderInformation.add(order);

        order = new BaseReportOrderTable();
        order.setTableName(InventoryJournalMaster.class.getName());
        order.setField("journalNumber");
        orderInformation.add(order);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryJournalMaster.class.getName(), "journalDefinitionId", BaseJournalDefinitionTable.class.getName(), "journalDefinitionId");
        tables.addTable(InventoryJournalLines.class.getName(), "journalNumber", InventoryJournalMaster.class.getName(), "journalNumber");
        tables.addTable(InventoryStocktakeRegister.class.getName(), "transId", InventoryJournalLines.class.getName(), "transId");

        setupDimensions("StockTake", InventoryJournalLines.class.getName(), "dimension1", InventoryJournalLines.class.getName(), "dimension2", InventoryJournalLines.class.getName(), "dimension3");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/stocktake/InventoryStockTakeVarianceByWarehouseReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS");
        jasperInfo.setReportTitle("Stock Take Variance By Warehouse");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_TAKE_VARIANCE.toString());
    }

    @Override
    public EMCQuery getActiveQuery() {
        EMCQuery query = super.getActiveQuery().copyQuery();
        query.addAnd("journalNumber", InventoryJournalLines.class.getName(), EMCQueryConditions.EQUALS, "masterId", InventoryStocktakeRegister.class.getName());
        query.addField("journalNumber", InventoryJournalLines.class.getName());
        query.addField("transId", InventoryJournalLines.class.getName());
        query.addField("itemId", InventoryJournalLines.class.getName());
        query.addField("dimension1", InventoryJournalLines.class.getName());
        query.addField("dimension2", InventoryJournalLines.class.getName());
        query.addField("dimension3", InventoryJournalLines.class.getName());
        query.addField("warehouse", InventoryJournalLines.class.getName());
        query.addField("location", InventoryStocktakeRegister.class.getName());
        query.addField("batch", InventoryStocktakeRegister.class.getName());
        query.addField("serial", InventoryStocktakeRegister.class.getName());
        query.addField("pallet", InventoryStocktakeRegister.class.getName());

        query.addFieldAggregateFunction("countQOH", InventoryJournalLines.class.getName(), "SUM");
        query.addFieldAggregateFunction("onHandQty", InventoryStocktakeRegister.class.getName(), "SUM");
        query.addFieldAggregateFunction("quantity", InventoryStocktakeRegister.class.getName(), "SUM");
        query.addFieldAggregateFunction("cost", InventoryJournalLines.class.getName(), "SUM");

        query.addField("lineNo", InventoryJournalLines.class.getName());
        query.addField("recordID", InventoryStocktakeRegister.class.getName());

        query.addFieldAggregateFunction("originalCountedQty", InventoryStocktakeRegister.class.getName(), "SUM");//17


        query.addTableAnd(InventoryWarehouse.class.getName(), "warehouse", InventoryJournalLines.class.getName(), "warehouseId");

        query.addField("stockTakeQuantityDiff", InventoryWarehouse.class.getName());//18
        query.addField("stockTakeValueDiff", InventoryWarehouse.class.getName());//19
        query.addField("pageNumber", InventoryStocktakeRegister.class.getName());//20

        query.addGroupBy(InventoryJournalLines.class.getName(), "itemId");
        query.addGroupBy(InventoryJournalLines.class.getName(), "dimension1");
        query.addGroupBy(InventoryJournalLines.class.getName(), "dimension2");
        query.addGroupBy(InventoryJournalLines.class.getName(), "dimension3");
        query.addGroupBy(InventoryJournalLines.class.getName(), "warehouse");
        query.addGroupBy(InventoryStocktakeRegister.class.getName(), "location");
        query.addGroupBy(InventoryStocktakeRegister.class.getName(), "batch");
        query.addGroupBy(InventoryStocktakeRegister.class.getName(), "serial");
        query.addGroupBy(InventoryStocktakeRegister.class.getName(), "pallet");
        return query;
    }
}
