/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.output.pricevariance;

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
import emc.enums.emcquery.EMCQueryConditions;
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
 * @author wikus
 */
public class PriceVariancePrintForm extends ReportFrame {

    public PriceVariancePrintForm(EMCUserData userData) {
        super("Price Variances", EnumReports.POP_PRICE_VARIANCE, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();


        String masterClassName = POPPurchaseOrderMaster.class.getName();
        String linesClassName = POPPurchaseOrderLines.class.getName();

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
        tables.addTable(linesClassName, "purchaseOrderId", masterClassName, "purchaseOrderId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/pop/pricevariance/POPPriceVariances.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.pricevariance.POPPriceVarianceDS");
        jasperInfo.setReportTitle("Price Variance");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_PRICEVARIANCE.toString());
    }
}
