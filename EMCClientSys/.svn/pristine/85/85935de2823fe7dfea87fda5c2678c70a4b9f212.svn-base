/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.output.millpurchase;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
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
public class POPMillPurchseOrderReportForm extends ReportFrame {

    public POPMillPurchseOrderReportForm(EMCUserData userData) {
        super("Mill Purchase", EnumReports.POP_MILL_PURCHASE, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(POPPurchaseOrderMaster.class.getName());
        whereTable.setField("purchaseOrderId");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE.toString());
        whereInformation.add(whereTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/pop/millpurchaseorder/POPMillPurchaseOrderReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorders.mill.POPMillPurchaseOrderReportDS");
        jasperInfo.setReportTitle("Mill Purchase");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_MILL_PURCHASE_REPORT.toString());
    }
}
