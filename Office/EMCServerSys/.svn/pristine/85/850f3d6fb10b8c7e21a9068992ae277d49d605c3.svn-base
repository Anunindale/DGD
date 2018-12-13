/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.discountsetup.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.sop.discountsetup.SOPDiscountSetupLocal;
import emc.entity.sop.SOPDiscountSetup;
import emc.entity.sop.datasource.SOPDiscountSetupDS;
import emc.enums.sop.discountsetup.CustomerSelectionType;
import emc.enums.sop.discountsetup.ItemSelectionType;
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
public class SOPDiscountSetupDSBean extends EMCDataSourceBean implements SOPDiscountSetupDSLocal {

    @EJB
    private SOPDiscountSetupLocal discountSetupBean;
    @EJB
    private InventoryReferenceLocal itemRefBean;

    /** Creates a new instance of SOPDiscountSetupDSBean. */
    public SOPDiscountSetupDSBean() {
        this.setDataSourceClassName(SOPDiscountSetupDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return discountSetupBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return discountSetupBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return discountSetupBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        SOPDiscountSetupDS ds = (SOPDiscountSetupDS) theRecord;

        if (fieldNameToValidate.equals("customerField")) {
            CustomerSelectionType customerSelectType = CustomerSelectionType.fromString(ds.getCustomerSelectType());

            if (customerSelectType == CustomerSelectionType.GROUP) {
                ds.setCustomerDiscGroup(ds.getCustomerField());
                ds.setCustomerId(null);
            } else if (customerSelectType == CustomerSelectionType.CUSTOMER) {
                ds.setCustomerId(ds.getCustomerField());
                ds.setCustomerDiscGroup(null);
            }
        }

        if (fieldNameToValidate.equals("itemField")) {
            ItemSelectionType itemSelectType = ItemSelectionType.fromString(ds.getItemSelectType());

            if (itemSelectType == ItemSelectionType.GROUP) {
                ds.setItemDiscGroup(ds.getItemField());
                ds.setItemId(null);
            } else if (itemSelectType == ItemSelectionType.ITEM) {
                if (!isBlank(ds.getItemField())) {
                    String[] refInfo = itemRefBean.checkItemReference(ds.getItemField(), userData);
                    if (refInfo != null && refInfo.length == 2) {
                        ds.setItemId(refInfo[0]);
                        ds.setItemField(refInfo[1]);
                    }
                    ds.setItemDiscGroup(null);
                }
            }
        }

        Object ret = discountSetupBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (ret instanceof SOPDiscountSetup) {
            return populateDataSourceFields((EMCEntityClass) convertSuperToDataSource(ret, userData), userData);
        } else {
            return ret;
        }
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPDiscountSetupDS ds = (SOPDiscountSetupDS) dataSourceInstance;

        CustomerSelectionType customerSelectType = CustomerSelectionType.fromString(ds.getCustomerSelectType());

        if (customerSelectType == CustomerSelectionType.GROUP) {
            ds.setCustomerField(ds.getCustomerDiscGroup());
        } else if (customerSelectType == CustomerSelectionType.CUSTOMER) {
            ds.setCustomerField(ds.getCustomerId());
        }

        ItemSelectionType itemSelectType = ItemSelectionType.fromString(ds.getItemSelectType());

        if (itemSelectType == ItemSelectionType.GROUP) {
            ds.setItemField(ds.getItemDiscGroup());
        } else if (itemSelectType == ItemSelectionType.ITEM) {
            //Get item id
            String itemId = ds.getItemId();
            if (!isBlank(itemId)) {
                String[] refInfo = itemRefBean.checkItemReference(itemId, userData);

                if (refInfo != null && refInfo.length == 2) {
                    ds.setItemField(refInfo[1]);
                }
            }
        }

        return ds;
    }
}
