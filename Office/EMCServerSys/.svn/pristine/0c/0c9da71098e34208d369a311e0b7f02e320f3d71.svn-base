package emc.bus.sop.customers;

import emc.entity.sop.SOPCustomers;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.helpers.customeractivity.CustomerActivityHelper;
import java.util.List;
import javax.ejb.Local;

/**
 * 
 * @Author wikus
 */
@Local
public interface SOPCustomersLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.sop.SOPCustomers createCustomerFromProspect(emc.entity.crm.CRMProspect prospect, emc.framework.EMCUserData userData);

    /**
     * Returns a customer record for the customer with the specified customer id.
     * @param customerId Id of customer to search for.
     * @param userData User data.
     * @return A customer record for the customer with the specified customer id or null, if no record is found.
     */
    public SOPCustomers findCustomer(String customerId, EMCUserData userData);

    /**
     * Returns a CustomerActivityHelper instance containing customer activity information.
     * @param customerId Customer id.
     * @param userData User data.
     * @return A CustomerActivityHelper instance containing customer activity information for the specified customer.
     */
    public CustomerActivityHelper getCustomerActivity(String customerId, EMCUserData userData);

    /**
     * Validates the specified invoiceToCustomer.
     * @param customerId Customer for which invoice to customer is being changed.
     * @param invoiceToCustomer New invoice to customer.
     * @param userData User data.
     * @return A boolean indicating whether the invoice to customer may be changed.
     */
    public boolean validateInvoiceToCustomer(String customerId, String invoiceToCustomer, EMCUserData userData);

    public List<String> getCustomersForAdminTool(EMCUserData userData);

    public boolean doWebRegistration(emc.helpers.debtors.DebtorsWebRegistrationHelper helper, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean findWebPortalUser(emc.helpers.debtors.DebtorsWebRegistrationHelper helper, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean createWebPortalUser(emc.entity.sop.SOPCustomers customer, java.lang.String password, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
