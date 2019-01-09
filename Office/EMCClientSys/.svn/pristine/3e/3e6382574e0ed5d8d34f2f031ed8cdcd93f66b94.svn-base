/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.output.itemmaster;

import emc.app.components.emcHelpFile;
import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class ItemMasterPrintForm extends ReportFrame {
     //private ItemMasterOKButton run;
     
     public ItemMasterPrintForm(EMCUserData userData) {
        super("Item List Report", EnumReports.INVENTORY_ITEMLIST, userData);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryItemListReport.html"));
     }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();
        //Report id has to be set manually
        mainQueryInformation.setReportId(EnumReports.INVENTORY_ITEMLIST.toString());
        
        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(InventoryItemMaster.class.getName());
        whereTable.setField("itemRange");
        
        whereInformation.add(whereTable);
        
        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        
        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/itemmaster/ItemList.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.entity.inventory.InventoryItemMaster");
        jasperInfo.setReportTitle("Item List");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return null;
    }
}
