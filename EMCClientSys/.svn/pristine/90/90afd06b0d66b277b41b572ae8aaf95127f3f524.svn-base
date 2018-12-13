/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.ReportOKButton;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.app.reporttools.parameters.StringParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.sop.SOPSalesRepGroups;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.customerstatement.IgnoreBalanceEnum;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class DebtorsAgeAnalysisByAgentReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsAgeAnalysisByReportForm */
    public DebtorsAgeAnalysisByAgentReportForm(EMCUserData userData) {
        super("Agent's Customer Activity", EnumReports.DEBTORS_AGE_ANALYIS_BY_REP, userData);
        this.addReportParameter("atDate", new DateParameterObject("At Date"));
        String[] agingModes = new String[]{DebtorsAgingMode.DATE.toString(), DebtorsAgingMode.NONE.toString(), DebtorsAgingMode.OLDEST.toString()};
        this.addReportParameter("unallocatedCreditAgingMode", new EMCJComboBoxParameterObject("Unallocated Credit Aging Mode", agingModes));

        String[] balanceParameters = new String[]{"", IgnoreBalanceEnum.CREDIT_BALANCE.toString(), IgnoreBalanceEnum.ZERO_BALANCE.toString(), IgnoreBalanceEnum.BOTH.toString()};
        this.addReportParameter("ignore", new EMCJComboBoxParameterObject("Ignore Customers", balanceParameters));

        this.addReportParameter("include_customers_with_no_sales_orders", new BooleanParameterObject("Include Customers without Open Sales Orders"));

        this.addReportParameter("order_status", new StringParameterObject("Order Status"));
        this.addReportParameter("order_from", new DateParameterObject("Orders From"));
        this.addReportParameter("order_to", new DateParameterObject("Orders To"));

        this.addReportParameter("printSODetail", new BooleanParameterObject("Include Sales Orders"));

        this.addReportParameter("splitOnCust", new BooleanParameterObject("Page Break Per Customer"));

        this.setOkButton(new ReportOKButton(this) {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                DebtorsAgeAnalysisByAgentReportForm form = DebtorsAgeAnalysisByAgentReportForm.this;
                JasperInformation jasperInfo = form.getJasperInfo();

                if ((Boolean) form.getReportParameterValue("splitOnCust")) {
                    jasperInfo.setJasperTemplate("/emc/reports/debtors/ageanalysisbyagent/DebtorsAgeAnalysisByAgentBreakPerCust.jrxml");
                } else {
                    jasperInfo.setJasperTemplate("/emc/reports/debtors/ageanalysisbyagent/DebtorsAgeAnalysisByAgent.jrxml");
                }

                super.doActionPerformed(evt);
            }
        });
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        String employeeClassName = BaseEmployeeTable.class.getName();

        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(employeeClassName);
        whereTable.setField("employeeNumber");

        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(SOPSalesRepGroups.class.getName(), "groupManager", BaseEmployeeTable.class.getName(), "employeeNumber");

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/ageanalysisbyagent/DebtorsAgeAnalysisByAgent.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.ageanalysisbyagent.DebtorsAgeAnalysisByAgentReportDS");
        jasperInfo.setReportTitle("Agent's Customer Activity");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_AGE_BY_AGENT.toString());
    }
}
