/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.repgroups;

import emc.bus.sop.parameters.SOPParametersLocal;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesRepCommission;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesRepGroupsBean extends EMCEntityBean implements SOPSalesRepGroupsLocal {

    @EJB
    private SOPParametersLocal sopParametersBean;

    /** Creates a new instance of SOPSalesRepGroupsBean. */
    public SOPSalesRepGroupsBean() {
    }

    /**
     * Returns a list of all the customers that the specified rep can sell to.
     * @param repId Rep id.
     * @param invoiceOnly Indicates whether only Invoice to Customers should be selected.
     * @param invoiceToCustomer Indicates that customers with the specified Invoice to Customer should be selected.  This may be null, in which case it will be ignored.
     * @param userData User data.
     * @return A list of all the customers that the specified rep can sell to.
     */
    public List<SOPCustomers> getCustomersForRep(String repId, boolean invoiceOnly, String invoiceToCustomer, EMCUserData userData) {
        SOPParameters params = sopParametersBean.getParameterRecord(userData);

        if (params == null) {
            logMessage(Level.SEVERE, "SOP Parameters not found.", userData);
            return new ArrayList<SOPCustomers>();
        }

        String customerClassName = SOPCustomers.class.getName();

        EMCQuery customerQuery = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);

        List<String> customerFields = new ArrayList<String>();
        List<String> commissionFields = new ArrayList<String>();

        if (customerClassName.equals(params.getCustomerItemTable1())) {
            customerFields.add(params.getCustomerItemField1());
            commissionFields.add("customerItemField1");
        }

        if (customerClassName.equals(params.getCustomerItemTable2())) {
            customerFields.add(params.getCustomerItemField2());
            commissionFields.add("customerItemField2");
        }

        if (customerClassName.equals(params.getCustomerItemTable3())) {
            customerFields.add(params.getCustomerItemField3());
            commissionFields.add("customerItemField3");
        }

        if (customerClassName.equals(params.getCustomerItemTable4())) {
            customerFields.add(params.getCustomerItemField4());
            commissionFields.add("customerItemField4");
        }

        if (customerClassName.equals(params.getCustomerItemTable5())) {
            customerFields.add(params.getCustomerItemField5());
            commissionFields.add("customerItemField5");
        }

        if (customerClassName.equals(params.getCustomerItemTable6())) {
            customerFields.add(params.getCustomerItemField6());
            commissionFields.add("customerItemField6");
        }

        if (!customerFields.isEmpty()) {
            String commissionClassName = SOPSalesRepCommission.class.getName();
            customerQuery.addTable(commissionClassName);
            customerQuery.addAnd("repId", repId, commissionClassName);

            customerQuery.openAndConditionBracket();
            customerQuery.openConditionBracket(EMCQueryBracketConditions.NONE);

            for (int i = 0; i < customerFields.size(); i++) {
                //Join on all required fields
                customerQuery.addAnd(customerFields.get(i), customerClassName, EMCQueryConditions.EQUALS, commissionFields.get(i), commissionClassName);
            }

            customerQuery.closeConditionBracket();
        }

        //Customer form logic should ensure that rep only has a value for the 'Single' rep service type.
        customerQuery.openConditionBracket(EMCQueryBracketConditions.OR);
        customerQuery.addOr("salesRep", repId);
        customerQuery.closeConditionBracket();

        if (!customerFields.isEmpty()) {
            customerQuery.closeConditionBracket();
        }

        if (invoiceOnly) {
            //Only select invoice to customers
            customerQuery.addAnd("customerId", EMCQueryConditions.EQUALS, "invoiceToCustomer", customerClassName);
        }

        if (invoiceToCustomer != null) {
            customerQuery.addAnd("invoiceToCustomer", invoiceToCustomer);
        }

        customerQuery.addOrderBy("customerName");

        //Select unique customer id's
        customerQuery.addGroupBy("customerId");

        return (List<SOPCustomers>) util.executeGeneralSelectQuery(customerQuery, userData);
    }
}
