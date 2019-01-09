/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.output.ageing;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rico
 */
public class AgeingFullDetailReportForm extends ReportFrame {

    public AgeingFullDetailReportForm(EMCUserData userData) {
        super("Inventory Ageing - Full Detail", EnumReports.INVENTORY_AGEING_FULL_DETAIL, userData);

        super.addReportParameter("useCurrentCost", new BooleanParameterObject("Use Current Cost"));
        super.addReportParameter("mustBeOlderThan", new DateParameterObject("Report On Stock Older Than (Incl.)"));
        super.addReportParameter("subtractReserved", new BooleanParameterObject("Subtract Reserved Stock"));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable where = new BaseReportWhereTable();
        where.setTableName(InventoryItemMaster.class.getName());
        where.setField("itemGroup");

        whereInformation.add(where);

        BaseReportWhereTable whereItem = new BaseReportWhereTable();
        whereItem.setTableName(InventoryItemMaster.class.getName());
        whereItem.setField("itemReference");

        whereInformation.add(whereItem);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable order = new BaseReportOrderTable();
        order.setTableName(InventoryItemMaster.class.getName());
        order.setField("itemGroup");
        orderInformation.add(order);

        order = new BaseReportOrderTable();
        order.setTableName(InventoryItemMaster.class.getName());
        order.setField("itemReference");
        orderInformation.add(order);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryTransactions.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        tables.addTable(InventoryDimensionTable.class.getName(), "recordID", InventoryTransactions.class.getName(), "itemDimId");
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/ageing/InventoryAgeingFullDetailedRepor.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.ageing.InventoryAgeingDS");
        jasperInfo.setReportTitle("Inventory Ageing - Full Detail");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_INVENTAGEING_FULL_DETAIL.toString());
    }

    @Override
    public EMCQuery getActiveQuery() {
        EMCCommandClass getBinsCmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.GET_BIN_LABELS.toString());

        JasperInformation jasperInfo = getJasperInfo();

        EMCUserData userData = getUserData();

        List list = new ArrayList();

        list = EMCWSManager.executeGenericWS(getBinsCmd, list, userData);

        if (list != null && !list.isEmpty()) {
            //Remove command
            list.remove(0);

            for (int i = 0; i < list.size(); i++) {
                int val = i + 1;
                jasperInfo.addParameter("bin" + val + "Title", list.get(i));
            }
        }

        //Set atDate parameter
        Date atDate = Functions.nowDate();
        jasperInfo.addParameter("atDate", Functions.date2String(atDate));

        return super.getActiveQuery();
    }
}
