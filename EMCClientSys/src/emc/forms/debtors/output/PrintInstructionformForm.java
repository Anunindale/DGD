/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.invoice.DebtorsInvoicePrintType;
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
public class PrintInstructionformForm extends ReportFrame {

    /** Creates a new instance of DebtorsCustomerAgingDetailedReportForm */
    public PrintInstructionformForm(EMCUserData userData) {
        super("Print Instruction", EnumReports.PRINTINTSRUCTIONREPORT, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsBasketMaster.class.getName());
        whereTable.setField("invoiceId");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/PrintInvoice/PrintInstructionReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.printinstruction.PrintInstructionReportDS");
        jasperInfo.setReportTitle("Print Instruction");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_PRINTINSTRUCTIONREPORT.toString());
    }

    @Override
    public EMCUserData getUserData() {
        EMCUserData userData = super.getUserData();
        userData.setUserData(0, DebtorsInvoicePrintType.PRINT_INSTRUCTION.toString());
        return userData;
    }
}
