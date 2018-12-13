/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.itemdimensions;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riaan
 */
public class ItemDimensionReportForm extends ReportFrame {

    public ItemDimensionReportForm(EMCUserData userData) {
        super("Item Dimensions", EnumReports.INVENTORY_ITEM_DIMENSIONS, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        String combinationsClassName = InventoryItemDimensionCombinations.class.getName();
        String itemMasterClassName = InventoryItemMaster.class.getName();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(combinationsClassName);
        whereTable.setField("active");
        whereTable.setFieldValue(String.valueOf(true));
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(itemMasterClassName);
        whereTable.setField("itemReference");
        whereTable.setFieldValue("");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemGroup");
        whereTable.setFieldValue("");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryDimension1.class.getName());
        orderTable.setField("sortCode");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryDimension3.class.getName());
        orderTable.setField("sortCode");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(InventoryDimension2.class.getName());
        orderTable.setField("sortCode");
        orderInformation.add(orderTable);


        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(itemMasterClassName, "itemId", combinationsClassName, "itemId");
        tables.addTable(InventoryDimension1.class.getName(), null, combinationsClassName, null);
        tables.addTable(InventoryDimension2.class.getName(), null, combinationsClassName, null);
        tables.addTable(InventoryDimension3.class.getName(), null, combinationsClassName, null);
        setupDimensions("ItemCombinationReport", combinationsClassName, "dimension1Id", combinationsClassName, "dimension2Id", combinationsClassName, "dimension3Id");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/itemdimensions/ItemDimensionReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.itemdimensions.InventoryItemDimensionReportDS");
        jasperInfo.setReportTitle("Item Dimensions");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_ITEM_DIMENSION_REPORT.toString());
    }
}
