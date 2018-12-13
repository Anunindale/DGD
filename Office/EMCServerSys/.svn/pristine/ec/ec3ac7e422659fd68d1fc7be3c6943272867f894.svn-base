/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.opendispatchorders;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryOpenDispatchOrdersBean extends EMCReportBean implements InventoryOpenDispatchOrdersLocal {

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);

        query.addTableAnd(SOPSalesOrderLines.class.getName(), "transId", InventoryPickingListLines.class.getName(), "inventTransId");
        query.addAnd("salesOrderNo", SOPSalesOrderMaster.class.getName(), EMCQueryConditions.EQUALS, "salesOrderNo", SOPSalesOrderLines.class.getName());
        //query.addAnd("status", PickingListStatusses.OPEN.toString(), InventoryPickingListMaster.class.getName());

        query.addGroupBy(InventoryPickingListMaster.class.getName(), "pickingListId");
        query.addGroupBy(InventoryPickingListLines.class.getName(), "itemId");
        query.addGroupBy(InventoryPickingListLines.class.getName(), "dimension1");
        query.addGroupBy(InventoryPickingListLines.class.getName(), "dimension2");
        query.addGroupBy(InventoryPickingListLines.class.getName(), "dimension3");
        query.addGroupBy(InventoryPickingListLines.class.getName(), "warehouse");

        query.addField("originalRequiredDate", SOPSalesOrderMaster.class.getName());
        query.addField("pickingListId", InventoryPickingListMaster.class.getName());
        query.addField("createdDate", InventoryPickingListMaster.class.getName());
        query.addField("customerNo", SOPSalesOrderMaster.class.getName());
        query.addField("customerName", SOPCustomers.class.getName());
        query.addField("salesOrderNo", SOPSalesOrderMaster.class.getName());
        query.addField("customerOrderNo", SOPSalesOrderMaster.class.getName());
        query.addField("reference", SOPSalesOrderMaster.class.getName());
        query.addFieldAggregateFunction("orderQty", InventoryPickingListLines.class.getName(), "SUM");
        query.addField("classificationClassGroup6", InventoryItemMaster.class.getName());


        String p = query.getTableAlias(InventoryPickingListLines.class);
        String s = query.getTableAlias(SOPSalesOrderLines.class);

        query.addCustomField("SUM(" + p + ".orderQty * " + s + ".price)", false);
        query.addField("requiredDate", SOPSalesOrderMaster.class.getName());

        List<Object[]> dataList = util.executeGeneralSelectQuery(query, userData);

        InventoryOpenDispatchOrdersDS ds;
        Map<String, InventoryOpenDispatchOrdersDS> reportData = new TreeMap<String, InventoryOpenDispatchOrdersDS>();

        for (Object[] data : dataList) {
            ds = reportData.get(Functions.date2String((Date) data[0]) + (String) data[1]);
            if (ds == null) {
                ds = new InventoryOpenDispatchOrdersDS();
                ds.setDeliveryDate(Functions.date2String((Date) data[0]));
                ds.setPickingList((String) data[1]);
                ds.setCreatedDate(Functions.date2String((Date) data[2]));
                ds.setCustomer((String) data[3]);
                ds.setCustomerName((String) data[4]);
                ds.setSalesOrder((String) data[5]);
                ds.setCustomerOrder((String) data[6]);
                ds.setCustomerReference((String) data[7]);
                ds.setExtDeliveryDate(Functions.date2String((Date) data[11]));
            }


            ds.setPacks(ds.getPacks().add(util.getBigDecimal((Double) data[8])));
            if (!isBlank(data[9])) {
                ds.setUnits(ds.getUnits().add(util.getBigDecimal((Double) data[8] * new Double((String) data[9]))));
            } else {
                ds.setUnits(BigDecimal.ZERO);
            }

            ds.setCost(ds.getCost().add(util.getBigDecimal((Double) data[10])));

            reportData.put(Functions.date2String((Date) data[0]) + (String) data[1], ds);
        }


        return new ArrayList(reportData.values());
    }
}
