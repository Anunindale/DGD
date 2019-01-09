/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.output.rollingsalesreport;

import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.EMCLookupParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLFinancialPeriods;
import emc.methods.sop.ServerSOPMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPRollingSalesReportForm extends ReportFrame {

    public SOPRollingSalesReportForm(EMCUserData userData) {
        super("Rolling Sales", EnumReports.SOP_ROLLING_SALES, userData);
        EMCLookup lkpFromPeriod = new EMCLookup(new GLFinancialPeriods());
        lkpFromPeriod.setPopup(new EMCLookupPopup(new emc.entity.gl.GLFinancialPeriods(), "periodId", userData));
        this.addReportParameter("fromPeriod", new EMCLookupParameterObject("From Period", lkpFromPeriod));

        EMCLookup lkpToPeriod = new EMCLookup(new GLFinancialPeriods());
        lkpToPeriod.setPopup(new EMCLookupPopup(new emc.entity.gl.GLFinancialPeriods(), "periodId", userData));
        this.addReportParameter("toPeriod", new EMCLookupParameterObject("To Period", lkpToPeriod));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemReference");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        whereTable.setField("customerNo");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(DebtorsCustomerInvoiceLines.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        tables.addTable(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber");

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/sop/rollingsalesreport/SOPRollingSalesReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.sop.rollingsalesreport.SOPRollingSalesReportDS");
        jasperInfo.setReportTitle("Rolling Sales");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.PRINT_ROLLING_SALES_REPORT.toString());
    }
}
