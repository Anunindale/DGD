/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.customerinvoice;

import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceLinesLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.logic.InventoryItemLogicLocal;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceLinesDS;
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
public class DebtorsCustomerInvoiceLinesDSBean extends EMCDataSourceBean implements DebtorsCustomerInvoiceLinesDSLocal {

    @EJB
    private DebtorsCustomerInvoiceLinesLocal invoiceLinesBean;
    @EJB
    private InventoryItemLogicLocal itemLogicBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal invoiceMasterBean;
    @EJB
    private DebtorsParametersLocal debtorsParametersBean;

    /** Creates a new instance of DebtorsCustomerInvoiceLinesDSBean */
    public DebtorsCustomerInvoiceLinesDSBean() {
        this.setDataSourceClassName(DebtorsCustomerInvoiceLinesDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return invoiceLinesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return invoiceLinesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return invoiceLinesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsCustomerInvoiceLinesDS ds = (DebtorsCustomerInvoiceLinesDS) dataSourceInstance;

        String itemId = ds.getItemId();
        String[] itemRef = refBean.checkItemReference(itemId, userData);
        if (itemRef != null) {
            ds.setItemReference(itemRef[1]);
            ds.setItemDescription(itemBean.findItemDescription(itemId, userData));
        } else {
            ds.setItemReference(itemId);
        }

        //Get Debtors Parameters and store in user data to prevent repeated fetching.
        //This is used on the Credit Held form.
        DebtorsParameters debtorsParameters = null;

        if (userData.getUserData(7) instanceof DebtorsParameters) {
             debtorsParameters = (DebtorsParameters)userData.getUserData(7);
        } else {
             debtorsParameters = debtorsParametersBean.getDebtorsParameters(userData);
             userData.setUserData(7, debtorsParameters);
        }

        if (debtorsParameters.isCreditCheckIncludeVAT()) {
            ds.setTotalHeld(ds.getTotalCost().add(ds.getVatAmount()));
        } else {
            ds.setTotalHeld(ds.getTotalCost());
        }

        ds.setVATIncluded(debtorsParameters.isCreditCheckIncludeVAT());

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        DebtorsCustomerInvoiceLinesDS ds = (DebtorsCustomerInvoiceLinesDS) theRecord;

        if (fieldNameToValidate.equals("itemReference")) {
            DebtorsCustomerInvoiceMaster invoiceMaster = invoiceMasterBean.getInvoiceMaster(ds.getInvCNNumber(), userData);
            if (!refBean.processItemReference(ds, null, invoiceMaster.getCustomerNo(), userData)) {
                return false;
            }

            itemLogicBean.setItemUOM(ds, userData);
            itemLogicBean.setItemWarehouse(ds, userData);
        }

        Object ret = invoiceLinesBean.validateField(fieldNameToValidate, theRecord, userData);

        if (ret instanceof DebtorsCustomerInvoiceLinesDS) {
            return ret;
        } else if (ret instanceof DebtorsCustomerInvoiceLines) {
            return populateDataSourceFields((DebtorsCustomerInvoiceLinesDS)convertSuperToDataSource(ret, userData), userData);
        } else {
            return ret;
        }
    }
}
