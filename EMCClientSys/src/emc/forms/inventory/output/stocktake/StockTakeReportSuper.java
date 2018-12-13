/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.stocktake;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.JournalStatus;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class StockTakeReportSuper extends ReportFrame {

    public StockTakeReportSuper(String title, EnumReports enumR, EMCUserData userData) {
        super(title, enumR, userData);
        this.addReportParameter("splitPerLocation", new BooleanParameterObject("Split Per Location"));
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
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryJournalMaster.class.getName());
        whereTable.setField("journalNumber");
        whereInformation.add(whereTable);



        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable order = new BaseReportOrderTable();
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EMCQuery getActiveQuery() {
        EMCQuery query = super.getActiveQuery().copyQuery();
        query.addAnd("journalNumber", InventoryJournalLines.class.getName(), EMCQueryConditions.EQUALS, "masterId", InventoryStocktakeRegister.class.getName());

        query.addTableAnd(InventoryWarehouse.class.getName(), "warehouse", InventoryJournalLines.class.getName(), "warehouseId");

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
        query.addField("countQOH", InventoryJournalLines.class.getName());
        query.addField("onHandQty", InventoryStocktakeRegister.class.getName());
        query.addField("quantity", InventoryStocktakeRegister.class.getName());
        query.addField("cost", InventoryJournalLines.class.getName());
        query.addField("lineNo", InventoryJournalLines.class.getName());
        query.addField("recordID", InventoryStocktakeRegister.class.getName());
        query.addField("originalCountedQty", InventoryStocktakeRegister.class.getName());
        query.addField("stockTakeQuantityDiff", InventoryWarehouse.class.getName());
        query.addField("stockTakeValueDiff", InventoryWarehouse.class.getName());
        query.addField("pageNumber", InventoryStocktakeRegister.class.getName());

        return query;
    }
}
