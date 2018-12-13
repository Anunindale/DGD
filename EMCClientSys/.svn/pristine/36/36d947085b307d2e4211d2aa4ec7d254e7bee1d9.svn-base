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
public class DebtorsInvoiceRegisterReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingDetailedReportForm */
    public DebtorsInvoiceRegisterReportForm(EMCUserData userData) {
        super("Invoice Register", EnumReports.DEBTORS_INVOICE_REGISTER, userData);
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

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/invoiceregister/DebtorsInvoiceRegisterReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.invoiceregister.DebtorsInvoiceRegisterReportDS");
        jasperInfo.setReportTitle("Invoice Register");
        jasperInfo.setDateRangeDisplay(DebtorsCustomerInvoiceMaster.class.getName(), "invoiceDate");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_INVOICE_REGISTER.toString());
    }
}
