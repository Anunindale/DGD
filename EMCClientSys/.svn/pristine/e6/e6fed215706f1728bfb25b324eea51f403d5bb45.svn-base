/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DebtorsInvoiceRegisterDetailReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingDetailedReportForm */
    public DebtorsInvoiceRegisterDetailReportForm(EMCUserData userData) {
        super("Invoice Register Detail", EnumReports.DEBTORS_INVOICE_REGISTER_DETAIL, userData);
        this.addReportParameter("breakPerRep", new BooleanParameterObject("Break Per Rep"));
        this.addReportParameter("invoiceCreditNote", new EMCJComboBoxParameterObject("Invoices/Credit Notes", new String[]{"Invoices", "Credit Notes", "Both"}));
        this.addReportParameter("salesManual", new EMCJComboBoxParameterObject("Sales/Manual", new String[]{"Sales", "Manual", "Both"}));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        whereTable.setField("invoiceDate");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE_ALLOW_BLANK.toString());
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        whereTable.setField("invCNNumber");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        whereTable.setField("salesOrderNo");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPCustomers.class.getName());
        whereTable.setField("customerId");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        whereTable.setField("salesRep");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        whereTable.setField("status");
        whereTable.setFieldValue(DebtorsInvoiceStatus.POSTED.toString());
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        orderTable.setField("invoiceDate");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPCustomers.class.getName());
        orderTable.setField("customerId");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        orderTable.setField("invCNNumber");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        orderTable.setField("salesOrderNo");
        orderList.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber", DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber");
        tables.addTable(SOPCustomers.class.getName(), "customerId", DebtorsCustomerInvoiceMaster.class.getName(), "customerNo");
        tables.addTable(BaseEmployeeTable.class.getName(), "employeeNumber", DebtorsCustomerInvoiceMaster.class.getName(), "salesRep", true);
        tables.addTable(InventoryDimension1.class.getName(), "dimensionId", DebtorsCustomerInvoiceLines.class.getName(), "dimension1", true);
        tables.addTable(InventoryDimension2.class.getName(), "dimensionId", DebtorsCustomerInvoiceLines.class.getName(), "dimension2", true);
        tables.addTable(InventoryDimension3.class.getName(), "dimensionId", DebtorsCustomerInvoiceLines.class.getName(), "dimension3", true);
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", DebtorsCustomerInvoiceLines.class.getName(), "itemId");

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/invoiceregister/DebtorsInvoiceRegisterDetailedReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.invoiceregisterdetail.DebtorsInvoiceRegisterReportDetailDS");
        jasperInfo.setReportTitle("Invoice Register Detail");
        jasperInfo.setDateRangeDisplay(DebtorsCustomerInvoiceMaster.class.getName(), "invoiceDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_INVOICE_REGISTER_DETAIL.toString());
    }
}
