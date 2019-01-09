/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.output.goodsreceived;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.pop.ServerPOPMethods;
import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @author riaan
 */
public class GoodsReceivedPrintForm extends ReportFrame {

    public GoodsReceivedPrintForm(EMCUserData userData) {
        super("Goods Received", EnumReports.POP_GOODSRECEIVED, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        String transactionClassName = InventoryTransactions.class.getName();

        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(transactionClassName);
        orderTable.setField("supplierId");

        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(transactionClassName);
        orderTable.setField("refNumber");

        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(transactionClassName);
        orderTable.setField("physicalDate");

        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(transactionClassName);
        orderTable.setField("itemId");

        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        //This is temporary, until the dynamic lookup works on transactions, which seems to be a problem related to the entity class and data source
        //not being mapped seperately on the inventory method mapper bean
        String masterClassName = POPPurchaseOrderMaster.class.getName();
        String linesClassName = POPPurchaseOrderLines.class.getName();
        String itemMasterClassName = InventoryItemMaster.class.getName();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("status");
        whereTable.setFieldValue(PurchaseOrderStatus.RECEIVED + "," + PurchaseOrderStatus.PARTIALLY_RECEIVED);
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());

        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("purchaseOrderId");
        whereTable.setFieldValue("?");

        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(linesClassName);
        whereTable.setField("itemId");
        whereTable.setFieldValue("?");

        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(itemMasterClassName);
        whereTable.setField("itemGroup");
        whereTable.setFieldValue("?");

        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(transactionClassName);
        whereTable.setField("physicalDate");
        whereTable.setFieldValue("?");

        whereInformation.add(whereTable);

        tables.addTable(masterClassName, "purchaseOrderId", tables.getMainTable(), "refNumber");
        tables.addTable(linesClassName, "purchaseOrderId", masterClassName, "purchaseOrderId");
        tables.addTable(itemMasterClassName, "itemId", linesClassName, "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);


    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorders/GoodsReceivedReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorders.POPGoodsReceivedDS");
        jasperInfo.setReportTitle("Goods Received");

        jasperInfo.setDateRangeDisplay(InventoryTransactions.class.getName(), "physicalDate");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_GOODSRECEIVEDREPORT.toString());
    }
}
