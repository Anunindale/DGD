/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.output.outstandingpurchaseorders;

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
public class OutstandingPOPrintForm extends ReportFrame {

    public OutstandingPOPrintForm(EMCUserData userData) {
        super("Outstanding Purchase Orders", EnumReports.POP_OUTSTANDINGPO, userData);
        
        this.addReportParameter("noDemand", new BooleanParameterObject("Only items with no demand"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        String masterClassName = POPPurchaseOrderMaster.class.getName();
        String linesClassName = POPPurchaseOrderLines.class.getName();
        String itemMasterClassName = InventoryItemMaster.class.getName();

        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("status");
        whereTable.setFieldValue(PurchaseOrderStatus.ORDERED + "," + PurchaseOrderStatus.PARTIALLY_RECEIVED);
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("supplier");
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
        whereTable.setTableName(itemMasterClassName);
        whereTable.setField("itemGroup");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("confirmedDeliveryDate");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(masterClassName);
        orderTable.setField("supplier");

        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(masterClassName);
        orderTable.setField("purchaseOrderId");

        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(itemMasterClassName);
        orderTable.setField("itemId");

        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(masterClassName);
        orderTable.setField("confirmedDeliveryDate");

        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(linesClassName, "purchaseOrderId", tables.getMainTable(), "purchaseOrderId");
        tables.addTable(itemMasterClassName, "itemId", linesClassName, "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorders/OutstandingPO.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorders.POPOutstandingPODS");
        jasperInfo.setReportTitle("Outstanding Purchase Orders");
        jasperInfo.setDateRangeDisplay(POPPurchaseOrderMaster.class.getName(), "confirmedDeliveryDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_OUTSTANDINGPOREPORT.toString());
    }
}
