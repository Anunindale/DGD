/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.dimension1;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.lines.InventoryDimension1Lines;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class InventoryDimension1ReportForm extends ReportFrame {

    public InventoryDimension1ReportForm(EMCUserData userData) {
        super("Configurations Report", EnumReports.INVENTORY_DIMENSION1, userData);
        this.addReportParameter("noLines", new BooleanParameterObject("Has No Color Setup"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {

        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryDimension1.class.getName());
        whereTable.setField("dimensionId");
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryDimension3.class.getName());
        whereTable.setField("dimensionId");
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryDimension1Lines.class.getName(), "dimension1Id", InventoryDimension1.class.getName(), "dimensionId");
        tables.addTable(InventoryDimension3.class.getName(), "dimensionId", InventoryDimension1Lines.class.getName(), "dimension3Id");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/dimension1/InventoryDimension1Report.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.dimension1.InventoryDimension1ReportDS");
        jasperInfo.setReportTitle("Configurations Report");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_DIMENSION1_REPORT.toString());
    }
}
