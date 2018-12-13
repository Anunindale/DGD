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
import emc.app.reporttools.parameters.DateParameterObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : Report frame for the Debtors Customer Aging Summary report.
 *
 * @date        : 01 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsCustomerAgingSummaryReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingSummaryReportForm */
    public DebtorsCustomerAgingSummaryReportForm(EMCUserData userData) {
        super("Debtors Age Analysis - Summary", EnumReports.DEBTORS_CUSTOMER_AGING_SUMMARY, userData);

        this.addReportParameter("atDate", new DateParameterObject("At Date"));
        String[] agingModes = new String[]{DebtorsAgingMode.DATE.toString(), DebtorsAgingMode.NONE.toString(), DebtorsAgingMode.OLDEST.toString()};
        this.addReportParameter("unallocatedCreditAgingMode", new EMCJComboBoxParameterObject("Unallocated Credit Aging Mode", agingModes));

       this.addReportParameter("ignore_zero", new BooleanParameterObject("Exclude All Zero"));
       this.addReportParameter("internalAgeing", new BooleanParameterObject("Internal Age Analysis"));
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

        jasperInfo.setJasperTemplate("/emc/reports/debtors/customeragingsummary/DebtorsCustomerAgingSummary.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customeragingsummary.DebtorsCustomerAgingSummaryReportDS");
        jasperInfo.setReportTitle("Debtors Age Analysis - Summary");

        //Get Debtors parameters to determine bin names
        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.GET_DEBTORS_PARAMETERS);

        List toSend = new ArrayList();

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof DebtorsParameters) {
            DebtorsParameters parameters = (DebtorsParameters) toSend.get(1);

            if (parameters.getAgingCurrentBinName() != null) {
                jasperInfo.addParameter("currentBinName", parameters.getAgingCurrentBinName());
            }
            if (parameters.getAgingBin1Name() != null) {
                jasperInfo.addParameter("bin1Name", parameters.getAgingBin1Name());
            }
            if (parameters.getAgingBin2Name() != null) {
                jasperInfo.addParameter("bin2Name", parameters.getAgingBin2Name());
            }
            if (parameters.getAgingBin3Name() != null) {
                jasperInfo.addParameter("bin3Name", parameters.getAgingBin3Name());
            }
            if (parameters.getAgingBin4Name() != null) {
                jasperInfo.addParameter("bin4Name", parameters.getAgingBin4Name());
            }
            if (parameters.getAgingBin5Name() != null) {
                jasperInfo.addParameter("bin5Name", parameters.getAgingBin5Name());
            }
            if (parameters.getAgingBin6Name() != null) {
                jasperInfo.addParameter("bin6Name", parameters.getAgingBin6Name());
            }
        }

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_AGING_SUMMARY.toString());
    }

    @Override
    public EMCQuery getActiveQuery() {
        //Set internal ageing parameter in JasperInfo
        JasperInformation jasperInfo = getJasperInfo();
        jasperInfo.addParameter("internalAgeing", getReportParameterValue("internalAgeing"));

        return super.getActiveQuery();
    }
}
