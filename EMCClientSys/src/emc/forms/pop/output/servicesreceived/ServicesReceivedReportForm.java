/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.output.servicesreceived;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.pop.ServerPOPMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author claudette
 */
public class ServicesReceivedReportForm extends ReportFrame {

    public ServicesReceivedReportForm(EMCUserData userData) {
        super("Services Received", EnumReports.SERVICES_RECEIVED, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();
        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(POPSupplierReceivedJournalLines.class.getName());
        whereTable.setField("receivedId");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemReference");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemType");
        whereTable.setFieldValue(InventoryItemTypes.SERVICE.toString());
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_EDIT.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(POPSupplierReceivedJournalLines.class.getName());
        whereTable.setField("transactionNumber");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(POPSupplierReceivedJournalLines.class.getName());
        whereTable.setField("receivedDate");
        whereInformation.add(whereTable);

        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(POPSupplierReceivedJournalLines.class.getName());
        orderTable.setField("receivedId");
        orderInformation.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(POPSupplierReceivedJournalMaster.class.getName(), "receivedId", POPSupplierReceivedJournalLines.class.getName(), "receivedId");
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", POPSupplierReceivedJournalLines.class.getName(), "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorders/ServicesReceivedReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorders.servicesreceived.POPServicesReceivedDS");
        jasperInfo.setReportTitle("Services Received");

        jasperInfo.setDateRangeDisplay(POPSupplierReceivedJournalLines.class.getName(), "receivedDate");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_SERVICES_RECEIVED_REPORT.toString());
    }
}
