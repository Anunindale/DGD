/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output;

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
import emc.menus.debtors.menuitems.display.DebtorsMarketingGroup;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class DebtorsRoyaltyReportForm extends ReportFrame {

    /** Creates a new instance of DebtorsRoyaltyReportForm */
    public DebtorsRoyaltyReportForm(EMCUserData userData) {
        super("Royalty Report", EnumReports.DEBTORS_ROYALTY_REPORT, userData);

        //N & L specific.  Allow exclusion of Jockey Shops
        EMCLookup lkpMarketingGroup = new EMCLookup(new DebtorsMarketingGroup());
        lkpMarketingGroup.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsMarketingGroup(), "marketingGroup", userData));
        this.addReportParameter("exclude_marketing_group", new EMCLookupParameterObject("Subtract total for Marketing Group (Jockey Shops)", lkpMarketingGroup));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        //Note that this query is manipulated on the server.
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCustomerInvoiceMaster.class.getName());
        whereTable.setField("invoiceDate");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber");
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", DebtorsCustomerInvoiceLines.class.getName(), "itemId");
        
        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/royalty/DebtorsRoyaltyReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.royalty.DebtorsRoyaltyReportDS");
        jasperInfo.setReportTitle("Royalty Report");

        jasperInfo.setDateRangeDisplay(DebtorsCustomerInvoiceMaster.class.getName(), "invoiceDate");
        
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_ROYALTY_REPORT.toString());
    }
}
