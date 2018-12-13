/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.factoryshop;

import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.app.reporttools.parameters.EMCLookupParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.JournalDefinitions;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryFactoryShopReportForm extends ReportFrame {

    public InventoryFactoryShopReportForm(EMCUserData userData) {
        super("Factory Shop Report", EnumReports.INVENTORY_FACTORY_SHOP_REPORT, userData);
        this.addReportParameter("fromDate", new DateParameterObject("From Date"));
        this.addReportParameter("toDate", new DateParameterObject("To Date"));

        EMCLookup lkpCageDef = new EMCLookup(new JournalDefinitions());
        lkpCageDef.setPopup(new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData));
        EMCLookupParameterObject cageDef = new EMCLookupParameterObject("Cage Definition", lkpCageDef);
        cageDef.setValue("CGS_FS");
        this.addReportParameter("cageDef", cageDef);

        EMCLookup lkpFinGoodsDef = new EMCLookup(new JournalDefinitions());
        lkpFinGoodsDef.setPopup(new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData));
        EMCLookupParameterObject finDef = new EMCLookupParameterObject("Finished Goods Definition", lkpFinGoodsDef);
        finDef.setValue("FS_ISS");
        this.addReportParameter("finDef", finDef);

        EMCLookup lkpFSCustomer1 = new EMCLookup(new SOPCustomersMenu());
        lkpFSCustomer1.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        EMCLookupParameterObject fsCust1 = new EMCLookupParameterObject("Factory Store Customer", lkpFSCustomer1);
        fsCust1.setValue("005649, 005648");
        this.addReportParameter("fsCust", fsCust1);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("classificationClassGroup1");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("productGroup");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/factoryshop/InventoryFactoryShopReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.factoryshop.InventoryFactoryShopReportDS");
        jasperInfo.setReportTitle("Factory Shop Report");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_FACTORY_SHOP_REPORT.toString());
    }
}
