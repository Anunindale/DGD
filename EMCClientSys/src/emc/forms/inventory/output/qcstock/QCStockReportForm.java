/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.qcstock;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
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
 * @author riaan
 */
public class QCStockReportForm extends ReportFrame {

    public QCStockReportForm(EMCUserData userData) {
        super("QC Stock", EnumReports.INVENTORY_QC_STOCK, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {

        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemReference");
        whereTable.setFieldValue("");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemGroup");
        whereTable.setFieldValue("");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventorySummary.class.getName());
        whereTable.setField("location");
        whereTable.setFieldValue(InventoryLocationsEnum.QUALITY_CHECK.toString());
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/qcstock/QCStockReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.stockbylocation.StockByLocationReportDS");
        jasperInfo.setReportTitle("QC Stock");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_BY_LOCATION_REPORT.toString());
    }

    @Override
    public EMCQuery getActiveQuery() {
        String itemMasterClass = InventoryItemMaster.class.getName();
        String summaryClass = InventorySummary.class.getName();

        EMCQuery query = super.getActiveQuery().copyQuery();

        query.addField("warehouse");
        query.addField("location");
        query.addField("itemId");
        query.addField("dimension3");
        query.addField("dimension2");
        query.addField("batch");
        query.addField("serialNo");
        query.addFieldAggregateFunction("qcAvailable", "SUM");

        query.addGroupBy("warehouse");
        query.addGroupBy("location");
        query.addGroupBy(itemMasterClass, "itemReference");
        query.addGroupBy("dimension3");
        query.addGroupBy("dimension2");
        query.addGroupBy("batch");
        query.addGroupBy("serialNo");

        query.addOrderBy("warehouse");
        query.addOrderBy("location");
        query.addOrderBy("itemReference", itemMasterClass);
        query.addOrderBy("dimension3");
        query.addOrderBy("batch");
        query.addOrderBy("serialNo");

        query.addOrHavingAggregateFunction("SUM", summaryClass, "qcAvailable", EMCQueryConditions.GREATER_THAN, 0);

        System.out.println(query);

        return query;
    }
}
