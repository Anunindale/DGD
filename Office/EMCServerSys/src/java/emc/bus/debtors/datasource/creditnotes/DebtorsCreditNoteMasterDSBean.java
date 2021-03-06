/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.creditnotes;

import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.datasource.DebtorsCreditNoteMasterDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsCreditNoteMasterDSBean extends EMCDataSourceBean implements DebtorsCreditNoteMasterDSLocal {

    @EJB
    private DebtorsCreditNoteMasterLocal masterBean;
    @EJB
    private SOPCustomersLocal customerBean;

    /** Creates a new instance of DebtorsCreditNoteMasterDSBean */
    public DebtorsCreditNoteMasterDSBean() {
        this.setDataSourceClassName(DebtorsCreditNoteMasterDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsCreditNoteMasterDS ds = (DebtorsCreditNoteMasterDS) dataSourceInstance;

//        populateCustomerInfo(ds, userData);

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = masterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (ret instanceof DebtorsCreditNoteMaster || (ret instanceof Boolean && (Boolean) ret)) {
            if (ret instanceof DebtorsCreditNoteMaster) {
                theRecord = convertSuperToDataSource(ret, userData);
            }
//            populateCustomerInfo((DebtorsCreditNoteMasterDS) theRecord, userData);

            return theRecord;
        } else {
            return ret;
        }
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    /**
     * Populates customer information on the given data source instance.
     * @param ds Datasource instance to populate.
     * @param userData User data.
     */
//    private void populateCustomerInfo(DebtorsCreditNoteMasterDS ds, EMCUserData userData) {
//        if (!isBlank(ds.getCustomerNo())) {
//            SOPCustomers customer = customerBean.findCustomer(ds.getCustomerNo(), userData);
//
//            if (customer != null) {
//                ds.setCustomerName(customer.getCustomerName());
//            } else {
//                logMessage(Level.WARNING, ServerDebtorsMessageEnum.CUST_NOT_FOUND, userData, ds.getCustomerNo());
//            }
//        }
//
//        if (!isBlank(ds.getInvoiceToCustNo())) {
//            SOPCustomers invoiceToCustomer = customerBean.findCustomer(ds.getInvoiceToCustNo(), userData);
//
//            if (invoiceToCustomer != null) {
//                ds.setInvoiceToCustomerName(invoiceToCustomer.getCustomerName());
//            } else {
//                logMessage(Level.WARNING, ServerDebtorsMessageEnum.CUST_NOT_FOUND, userData, ds.getInvoiceToCustNo());
//            }
//        }
//    }
}
