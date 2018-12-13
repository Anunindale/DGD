/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.output.cancelledpurchaseorders;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderLines;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.pop.ServerPOPMethods;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name         : CancelledPurchaseOrderReportForm
 *
 * @Date         : 04 Jun 2009
 * 
 * @Description  : Report form for the cancelled Purchase Orders report.
 *
 * @author       : riaan
 */
public class CancelledPurchaseOrderReportForm extends ReportFrame {

    /** Creates a new instance of CancelledPurchaseOrderReportForm. */
    public CancelledPurchaseOrderReportForm(EMCUserData userData) {
        super("Cancelled Purchase Orders", EnumReports.CANCELLED_PO, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();


        String masterClassName = POPCancelledPurchaseOrderMaster.class.getName();
        String linesClassName = POPCancelledPurchaseOrderLines.class.getName();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("purchaseOrderId");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(linesClassName);
        whereTable.setField("itemId");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(masterClassName);
        whereTable.setField("supplier");
        whereInformation.add(whereTable);

        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(masterClassName);
        orderTable.setField("purchaseOrderId");
        orderInformation.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(linesClassName);
        orderTable.setField("itemId");
        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(masterClassName, "purchaseOrderId", linesClassName, "purchaseOrderId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setReportTitle("Cancelled Purchase Order");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");
        jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderPostingReport.jrxml");

        jasperInfo.addParameter("cancel", "CANCELLED");
        
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_CANCELLED_PO.toString());
    }
}
