/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.overdueorders;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import emc.reporttools.EMCReportDimensionSetup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPOverdueSalesOrdersReportBean extends EMCReportBean implements SOPOverdueSalesOrdersReportLocal {

    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);
        EMCReportDimensionSetup setup = (EMCReportDimensionSetup) queryList.get(2);
        query.addField("requiredDate", SOPSalesOrderMaster.class.getName());
        query.addField("salesOrderNo", SOPSalesOrderMaster.class.getName());
        query.addField("customerNo", SOPSalesOrderMaster.class.getName());
        query.addField("itemId", SOPSalesOrderLines.class.getName());
        query.addField("dimension1", SOPSalesOrderLines.class.getName());
        query.addField("dimension2", SOPSalesOrderLines.class.getName());
        query.addField("dimension3", SOPSalesOrderLines.class.getName());
        query.addField("quantity", SOPSalesOrderLines.class.getName());
        return manipulateQueryResult(executeNativeQuery(query, setup, userData), null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        SOPOverdueSalesOrdersReportDS ds;
        Object[] data;
        Map<String, SOPCustomers> customerMap = new HashMap<String, SOPCustomers>();
        SOPCustomers customer;
        Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();
        InventoryItemMaster item;
        List reportData = new ArrayList();
        for (Object o : queryResult) {
            data = (Object[]) o;
            ds = new SOPOverdueSalesOrdersReportDS();
            ds.setTransDate(Functions.date2String((Date) data[0]));
            ds.setSalesOrder((String) data[1]);
            ds.setCustomerId((String) data[2]);
            customer = customerMap.get(ds.getCustomerId());
            if (customer == null) {
                customer = customerBean.findCustomer(ds.getCustomerId(), userData);
                customerMap.put(ds.getCustomerId(), customer);
            }
            if (customer != null) {
                ds.setCustomerName(customer.getCustomerName());
            }
            ds.setItemId((String) data[3]);
            item = itemMap.get(ds.getItemId());
            if (item == null) {
                item = itemBean.findItem(ds.getItemId(), userData);
                itemMap.put(ds.getItemId(), item);
            }
            if (item != null) {
                ds.setItemId(item.getItemReference());
                ds.setItemDescription(item.getDescription());
            }
            ds.setDimension1((String) data[4]);
            ds.setDimension2((String) data[5]);
            ds.setDimension3((String) data[6]);
            ds.setQuantity((BigDecimal) data[7]);
            reportData.add(ds);
        }
        return reportData;
    }
}
