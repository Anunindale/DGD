/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.salesorders;

import emc.bus.base.BaseCompanyLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.pop.POPDeliveryModesLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.base.BaseCompanyTable;
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
public class SOPSalesOrdersReportBean extends EMCReportBean implements SOPSalesOrdersReportLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    POPDeliveryModesLocal deleveryMethodBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private BaseCompanyLocal companyBean;
    @EJB
    private InventoryDimension1Local dim1Bean;
    @EJB
    private InventoryDimension3Local dim3Bean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);

        query.addField("salesOrderNo", SOPSalesOrderMaster.class.getName()); //0
        query.addField("customerOrderNo", SOPSalesOrderMaster.class.getName()); //1
        query.addField("reference", SOPSalesOrderMaster.class.getName()); //2
        query.addField("salesRep", SOPSalesOrderMaster.class.getName()); //3

        query.addField("deliveryMethod", SOPSalesOrderMaster.class.getName()); //4
        query.addField("requiredDate", SOPSalesOrderMaster.class.getName()); //5

        query.addField("customerNo", SOPSalesOrderMaster.class.getName()); //6
        query.addField("deliveryAddress1", SOPSalesOrderMaster.class.getName()); //7
        query.addField("deliveryAddress2", SOPSalesOrderMaster.class.getName()); //8
        query.addField("deliveryAddress3", SOPSalesOrderMaster.class.getName()); //9
        query.addField("deliveryAddress4", SOPSalesOrderMaster.class.getName()); //10
        query.addField("deliveryAddress5", SOPSalesOrderMaster.class.getName()); //11
        query.addField("deliveryAddressCode", SOPSalesOrderMaster.class.getName()); //12

        query.addField("invoiceToCustomerNo", SOPSalesOrderMaster.class.getName()); //13
        query.addField("invoiceAddress1", SOPSalesOrderMaster.class.getName()); //14
        query.addField("invoiceAddress2", SOPSalesOrderMaster.class.getName()); //15
        query.addField("invoiceAddress3", SOPSalesOrderMaster.class.getName()); //16
        query.addField("invoiceAddress4", SOPSalesOrderMaster.class.getName()); //17
        query.addField("invoiceAddress5", SOPSalesOrderMaster.class.getName()); //18
        query.addField("invoiceAddressCode", SOPSalesOrderMaster.class.getName()); //19

        query.addField("itemId", SOPSalesOrderLines.class.getName()); //20
        query.addField("dimension1", SOPSalesOrderLines.class.getName()); //21
        query.addField("dimension2", SOPSalesOrderLines.class.getName()); //22
        query.addField("dimension3", SOPSalesOrderLines.class.getName()); //23
        query.addField("quantity", SOPSalesOrderLines.class.getName()); //24
        query.addField("lineTotal", SOPSalesOrderLines.class.getName()); //25

        query.addField("comments", SOPSalesOrderMaster.class.getName()); //26

        query.addField("originalRequiredDate", SOPSalesOrderMaster.class.getName()); //28

        EMCReportDimensionSetup setup = (EMCReportDimensionSetup) queryList.get(2);
        return manipulateQueryResult(executeNativeQuery(query, setup, userData), null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        SOPSalesOrdersReportDS ds;
        Object[] data;

        List reportData = new ArrayList();

        Map<String, SOPCustomers> customerMap = new HashMap<String, SOPCustomers>();
        SOPCustomers customer;
        Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();
        InventoryItemMaster item;
        List<String> address;

        BaseCompanyTable comp = companyBean.getUserCompany(userData);

        for (Object o : queryResult) {
            data = (Object[]) o;
            ds = new SOPSalesOrdersReportDS();
            ds.setSalesOrderNo((String) data[0]);
            ds.setCustomerOrderNo((String) data[1]);
            ds.setReference((String) data[2]);
            ds.setSalesRep((String) data[3]);
            ds.setSalesRepName(employeeBean.findEmployeeNameAndSurname(ds.getSalesRep(), userData));
            ds.setDeliveryMethod((String) data[4]);
            ds.setDeliveryMethodDescription(deleveryMethodBean.findDescription(ds.getDeliveryMethod(), userData));
            ds.setDeliveryDate(Functions.date2String((Date) data[5]));
            ds.setCustomerNumber((String) data[6]);

            ds.setCustomerId((String) data[6]);
            customer = customerMap.get(ds.getCustomerId());
            if (customer == null) {
                customer = customerBean.findCustomer(ds.getCustomerId(), userData);
                customerMap.put(ds.getCustomerId(), customer);
            }
            if (customer != null) {
                ds.setCustomerName(isBlank(customer.getTrandingAs()) ? customer.getCustomerName() : customer.getTrandingAs());
            }
            address = concertinaAddress((String) data[7], (String) data[8], (String) data[9], (String) data[10], (String) data[11], (String) data[12]);
            ds.setCustomerDeliveryAddress1(address.get(0));
            ds.setCustomerDeliveryAddress2(address.get(1));
            ds.setCustomerDeliveryAddress3(address.get(2));
            ds.setCustomerDeliveryAddress4(address.get(3));
            ds.setCustomerDeliveryAddress5(address.get(4));
            ds.setCustomerDeliveryAddressCode(address.get(5));

            ds.setCustomerId_1((String) data[13]);
            customer = customerMap.get(ds.getCustomerId_1());
            if (customer == null) {
                customer = customerBean.findCustomer(ds.getCustomerId_1(), userData);
                customerMap.put(ds.getCustomerId_1(), customer);
            }
            if (customer != null) {
                ds.setCustomerName_1(customer.getCustomerName());
            }
            address = concertinaAddress((String) data[14], (String) data[15], (String) data[16], (String) data[17], (String) data[18], (String) data[19]);
            ds.setCustomerInvoiceAddress1(address.get(0));
            ds.setCustomerInvoiceAddress2(address.get(1));
            ds.setCustomerInvoiceAddress3(address.get(2));
            ds.setCustomerInvoiceAddress4(address.get(3));
            ds.setCustomerInvoiceAddress5(address.get(4));
            ds.setCustomerInvoiceAddressCode(address.get(5));

            ds.setItemId((String) data[20]);
            item = itemMap.get(ds.getItemId());
            if (item == null) {
                item = itemBean.findItem(ds.getItemId(), userData);
                itemMap.put(ds.getItemId(), item);
            }
            if (item != null) {
                ds.setItemId(item.getItemReference());
                ds.setItemDescription(item.getDescription());
            }
            ds.setDimension1((String) data[21]);
            if (!isBlank(ds.getDimension1())) {
                ds.setDimension1Description(dim1Bean.findDimensionDescription(ds.getDimension1(), userData));
            }
            ds.setDimension2((String) data[22]);
            ds.setDimension3((String) data[23]);
            if (!isBlank(ds.getDimension3())) {
                ds.setDimension1Description(dim3Bean.findDimensionDescription(ds.getDimension3(), userData));
            }
            ds.setQuantity((BigDecimal) data[24]);
            ds.setPrice((BigDecimal) data[25]);

            ds.setCompanyPhone(comp.getCoPhoneNr());
            ds.setCompanyFax(comp.getCoFaxNr());
            ds.setCompanyVATNumber(comp.getCoVatRegNr());

            address = concertinaAddress(comp.getCoPostAdrs1(), comp.getCoPostAdrs2(), comp.getCoPostAdrs3(), comp.getCoPostAdrs4(),
                    comp.getCoPostAdrs5(), comp.getCoPostCode());
            ds.setCompanyPostalAddress1(address.get(0));
            ds.setCompanyPostalAddress2(address.get(1));
            ds.setCompanyPostalAddress3(address.get(2));
            ds.setCompanyPostalAddress4(address.get(3));
            ds.setCompanyPostalAddress5(address.get(4));
            ds.setCompanyPostalAddressCode(address.get(5));

            address = concertinaAddress(comp.getCoPhysAdrs1(), comp.getCoPhysAdrs2(), comp.getCoPhysAdrs3(), comp.getCoPhysAdrs4(),
                    comp.getCoPhysAdrs5(), comp.getCoPhysPostCode());
            ds.setCompanyPhysicalAddress1(address.get(0));
            ds.setCompanyPhysicalAddress2(address.get(1));
            ds.setCompanyPhysicalAddress3(address.get(2));
            ds.setCompanyPhysicalAddress4(address.get(3));
            ds.setCompanyPhysicalAddress5(address.get(4));
            ds.setCompanyPhysicalAddressCode(address.get(5));

            ds.setComments((String) data[26]);
            ds.setRequiredDate(dateHandler.date2String((Date) data[5]));
            ds.setOriginalRequiredDate(dateHandler.date2String((Date) data[27]));

            reportData.add(ds);
        }
        return reportData;
    }
}
