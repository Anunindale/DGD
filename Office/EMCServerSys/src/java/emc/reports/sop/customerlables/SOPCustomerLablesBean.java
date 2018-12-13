/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.customerlables;

import emc.entity.base.BaseEmployeeTable;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesRepCommission;
import emc.enums.emcquery.EMCQueryFieldTypes;
import emc.enums.enumQueryTypes;
import emc.enums.sop.SOPCustomerLablesType;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPCustomerLablesBean extends EMCBusinessBean implements SOPCustomerLablesLocal {

    @Override
    public String printCustomerLables(EMCQuery query, String customerType, EMCUserData userData) {
        query.setSelectDistinctAll(true);
        List<SOPCustomers> customerList = util.executeGeneralSelectQuery(query, userData);

        List<String> salesReps = query.getFieldValue(BaseEmployeeTable.class.getName(), "employeeNumber", EMCQueryFieldTypes.STRING, false);
        if (!salesReps.isEmpty()) {
            query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            query.addAndInList("salesRep", salesReps, true, false);
            query.setSelectDistinctAll(true);
            customerList.addAll(util.executeGeneralSelectQuery(query, userData));
        }

        List<String> customerTracker = new ArrayList<String>();
        SOPCustomerLablesType type = SOPCustomerLablesType.fromString(customerType);
        Map<String, SOPCustomers> invoiceCustomers = new HashMap<String, SOPCustomers>();
        SOPCustomers invoiceCustomer;
        List<String> customerReps;
        String rep;
        boolean first;

        StringBuilder builder = new StringBuilder();

        for (SOPCustomers customer : customerList) {
            switch (type) {
                case BOTH:
                case INVOICE_TO:
                    if (customerTracker.contains(customer.getInvoiceToCustomer())) {
                        continue;
                    }
                    customerTracker.add(customer.getInvoiceToCustomer());
                    invoiceCustomer = invoiceCustomers.get(customer.getInvoiceToCustomer());
                    if (invoiceCustomer == null) {
                        query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                        query.addAnd("customerId", customer.getInvoiceToCustomer());
                        invoiceCustomer = (SOPCustomers) util.executeSingleResultQuery(query, userData);
                        invoiceCustomers.put(invoiceCustomer.getCustomerId(), invoiceCustomer);
                    }

                    rep = invoiceCustomer.getSalesRep();
                    if (isBlank(rep)) {
                        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                        query.addAnd("customerItemField1", invoiceCustomer.getCustomerId());
                        query.addField("repId");
                        query.setSelectDistinctAll(true);
                        customerReps = util.executeGeneralSelectQuery(query, userData);
                        first = true;
                        rep = "";
                        for (String r : customerReps) {
                            if (!first) {
                                rep += ", ";
                            }
                            rep += r;
                            first = false;
                        }
                    }
                    builder.append(generateLableString(invoiceCustomer.getCustomerId(), rep, invoiceCustomer.getOrderContactName(),
                            isBlank(invoiceCustomer.getTrandingAs()) ? invoiceCustomer.getCustomerName() : invoiceCustomer.getTrandingAs(), invoiceCustomer.getPostalAddresLine1(),
                            invoiceCustomer.getPostalAddresLine2(), invoiceCustomer.getPostalAddresLine3(),
                            invoiceCustomer.getPostalPostalCode()));
                    if (!type.equals(SOPCustomerLablesType.BOTH)) {
                        break;
                    }
                case SHIP_TO:
                    if (customerTracker.contains(customer.getCustomerId())) {
                        continue;
                    }
                    customerTracker.add(customer.getCustomerId());

                    rep = customer.getSalesRep();
                    if (isBlank(rep)) {
                        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                        query.addAnd("customerItemField1", customer.getCustomerId());
                        query.addField("repId");
                        query.setSelectDistinctAll(true);
                        customerReps = util.executeGeneralSelectQuery(query, userData);
                        first = true;
                        rep = "";
                        for (String r : customerReps) {
                            if (!first) {
                                rep += ", ";
                            }
                            rep += r;
                            first = false;
                        }
                    }
                    builder.append(generateLableString(customer.getCustomerId(), rep, customer.getOrderContactName(),
                            isBlank(customer.getTrandingAs()) ? customer.getCustomerName() : customer.getTrandingAs(), customer.getPostalAddresLine1(),
                            customer.getPostalAddresLine2(), customer.getPostalAddresLine3(),
                            customer.getPostalPostalCode()));
                    if (!type.equals(SOPCustomerLablesType.BOTH)) {
                        break;
                    }
            }
        }
        return builder.toString();
    }

    private String generateLableString(String customerNumber, String salesRep, String contactName, String customerNameTA, String addressLine1,
            String addressLine2, String addressLine3, String addressLineCode) {
        if (customerNumber == null) customerNumber = "";
        if (salesRep == null) salesRep = "";
        if (contactName == null) contactName = "";
        if (customerNameTA == null) customerNameTA = "";
        if (addressLine1 == null) addressLine1 = "";
        if (addressLine2 == null) addressLine2 = "";
        if (addressLine3 == null) addressLine3 = "";
        if (addressLineCode == null) addressLineCode = "";

        StringBuilder builder = new StringBuilder();
        builder.append("~BARCODESTART~L");
        builder.append("H20");
        builder.append("~BARCODERETURN~");
        builder.append("PG");
        builder.append("~BARCODERETURN~");
        builder.append("D11");
        builder.append("~BARCODERETURN~");
        builder.append("121100000850159" + customerNumber + "(" + salesRep + ")");
        builder.append("~BARCODERETURN~");
        builder.append("131100000710001" + "Att: " + contactName);
        builder.append("~BARCODERETURN~");
        builder.append("131100000540001" + customerNameTA);
        builder.append("~BARCODERETURN~");
        builder.append("131100000370001" + addressLine1);
        builder.append("~BARCODERETURN~");
        builder.append("131100000200001" + addressLine2);
        builder.append("~BARCODERETURN~");
        builder.append("131100000030001" + addressLine3 + " " + addressLineCode);
        builder.append("~BARCODERETURN~");
        builder.append("E");
        builder.append("~BARCODERETURN~");
        return builder.toString();
    }
}
