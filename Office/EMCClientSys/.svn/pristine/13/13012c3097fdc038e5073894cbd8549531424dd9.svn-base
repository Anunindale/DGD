/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.customerstatement.IgnoreBalanceEnum;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DebtorsCustomerStatementReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingDetailedReportForm */
    public DebtorsCustomerStatementReportForm(EMCUserData userData) {
        super("Customer Statement", EnumReports.DEBTORS_CUSTOMER_STATEMENT, userData);
        this.addReportParameter("atDate", new DateParameterObject("At Date"));
        String[] agingModes = new String[] {DebtorsAgingMode.DATE.toString(), DebtorsAgingMode.NONE.toString(), DebtorsAgingMode.OLDEST.toString()};
        this.addReportParameter("unallocatedCreditAgingMode" , new EMCJComboBoxParameterObject("Unallocated Credit Aging Mode", agingModes));

        String[] balanceParameters = new String[] {"", IgnoreBalanceEnum.CREDIT_BALANCE.toString(), IgnoreBalanceEnum.ZERO_BALANCE.toString(), IgnoreBalanceEnum.BOTH.toString()};
        this.addReportParameter("ignore", new EMCJComboBoxParameterObject("Ignore Customers", balanceParameters));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        String customerClassName = SOPCustomers.class.getName();

        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(customerClassName);
        whereTable.setField("customerId");

        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/custmerstatement/DebtorsCustometStatementReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerstatement.DebtorsCustomerStatementReportDS");
        jasperInfo.setReportTitle("Customer Statement");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_CUSTOMER_STATEMENT.toString());
    }
}
