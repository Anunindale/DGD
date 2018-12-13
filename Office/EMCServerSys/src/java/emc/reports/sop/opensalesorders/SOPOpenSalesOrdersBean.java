/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.opensalesorders;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.sop.salesorders.SalesOrderStatus;
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
public class SOPOpenSalesOrdersBean extends EMCReportBean implements SOPOpenSalesOrdersLocal {

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);

        query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);

        query.addGroupBy(SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        query.addGroupBy(SOPSalesOrderLines.class.getName(), "itemId");

        query.addField("requiredDate", SOPSalesOrderMaster.class.getName());
        query.addField("salesOrderNo", SOPSalesOrderMaster.class.getName());
        query.addField("salsesOrderStatus", SOPSalesOrderMaster.class.getName());
        query.addField("customerNo", SOPSalesOrderMaster.class.getName());
        query.addField("customerName", SOPCustomers.class.getName());
        query.addField("customerOrderNo", SOPSalesOrderMaster.class.getName());
        query.addField("reference", SOPSalesOrderMaster.class.getName());
        query.addField("salesRep", SOPSalesOrderMaster.class.getName());
        query.addFieldAggregateFunction("quantity", SOPSalesOrderLines.class.getName(), "SUM");
        query.addField("classificationClassGroup6", InventoryItemMaster.class.getName());
        String salesLineAlias = query.getTableAlias(SOPSalesOrderLines.class);
        query.addCustomField("SUM(" + salesLineAlias + ".lineTotal)", false);
        query.addField("originalRequiredDate", SOPSalesOrderMaster.class.getName());
        List<Object[]> dataList = util.executeGeneralSelectQuery(query, userData);

        SOPOpenSalesOrdersDS ds;
        Map<String, SOPOpenSalesOrdersDS> reportData = new TreeMap<String, SOPOpenSalesOrdersDS>();

        for (Object[] data : dataList) {
            ds = reportData.get(Functions.date2String((Date) data[0]) + (String) data[1]);
            if (ds == null) {
                ds = new SOPOpenSalesOrdersDS();
                ds.setRequiredDate(Functions.date2String((Date) data[0]));
                ds.setSalesOrder((String) data[1]);
                ds.setStatus((String) data[2]);
                ds.setCustomer((String) data[3]);
                ds.setCustomerName((String) data[4]);
                ds.setCustomerOrder((String) data[5]);
                ds.setCustomerReference((String) data[6]);
                ds.setSalesRep((String) data[7]);
                ds.setOriginalDate(data[11] == null ? "" : Functions.date2String((Date) data[11]));
            }

            ds.setPacks(ds.getPacks().add((BigDecimal) data[8]));
            ds.setUnits(ds.getUnits().add(((BigDecimal) data[8]).multiply(util.getBigDecimal(new Double((String) data[9])))));
            ds.setValue(ds.getValue().add((BigDecimal) data[10]));

            reportData.put(Functions.date2String((Date) data[0]) + (String) data[1], ds);
        }


        return new ArrayList(reportData.values());
    }
}
