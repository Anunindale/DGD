/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.output.goodsreturned;

import emc.forms.pop.output.goodsreceived.*;
import emc.forms.pop.output.outstandingpurchaseorders.*;
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
public class GoodsReturnedPrintForm extends ReportFrame {

    public GoodsReturnedPrintForm(EMCUserData userData) {
        super("Goods Returned", EnumReports.POP_GOODSRETURNED, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        String transactionClassName = InventoryTransactions.class.getName();
        String masterClassName = POPPurchaseOrderMaster.class.getName();
        String linesClassName = POPPurchaseOrderLines.class.getName();
        String itemMasterClassName = InventoryItemMaster.class.getName();
        
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();
        
        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(transactionClassName);
        whereTable.setField("physicalDate");
        whereInformation.add(whereTable);
        
        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("purchaseOrderId");
        whereInformation.add(whereTable);
        
        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(linesClassName);
        whereTable.setField("itemId");
        whereInformation.add(whereTable);
        
        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(transactionClassName);
        whereTable.setField("refType");
        whereTable.setFieldValue(emc.enums.inventory.transactions.InventoryTransactionsRefType.Return.toString());
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);
        
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
        
        tables.addTable(masterClassName, "purchaseOrderId", transactionClassName, "refNumber");
        tables.addTable(linesClassName, "purchaseOrderId", masterClassName, "purchaseOrderId");
        tables.addTable(itemMasterClassName, "itemId", linesClassName, "itemId");
        
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorders/GoodsReturnedReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorders.POPGoodsReceivedDS");
        jasperInfo.setReportTitle("Goods Returned");
        jasperInfo.setDateRangeDisplay(InventoryTransactions.class.getName(), "physicalDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_GOODSRETURNEDREPORT.toString());
    }

}
