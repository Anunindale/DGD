/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.belowminimum;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryBelowMinimumReportForm extends ReportFrame {

    public InventoryBelowMinimumReportForm(EMCUserData userData) {
        super("Stock Below Minimum", EnumReports.INVENTORY_BELOW_MINIMUM, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable where = new BaseReportWhereTable();
        where.setTableName(InventoryItemDimensionCombinations.class.getName());
        where.setField("itemId");
        whereInformation.add(where);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimensionCombinations.class.getName(), "itemId");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/belowminimum/InventoryBelowMinimumReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.belowminimum.InventoryBelowMinimumReportDS");
        jasperInfo.setReportTitle("Stock Below Minimum");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_BELOW_MINIMUM.toString());
    }
}
