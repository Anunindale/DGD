/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.datasource;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.datasource.InventoryReferenceDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryReferenceDSBean extends EMCDataSourceBean implements InventoryReferenceDSLocal {

    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private InventoryItemMasterLocal itemBean; 

    public InventoryReferenceDSBean() {
        this.setDataSourceClassName(InventoryReferenceDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return refBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return refBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return refBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryReferenceDS ds = (InventoryReferenceDS) dataSourceInstance;
        String[] sArray = refBean.checkItemReference(ds.getItemId(), userData);
        ds.setItemPrimaryReference(sArray[1]);
        ds.setItemDesc(itemBean.findItemDescription(ds.getItemId(), userData));
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryReferenceDS ds = (InventoryReferenceDS) theRecord;

        if (fieldNameToValidate.equals("itemPrimaryReference")) {
            String[] reference = refBean.checkItemReference(ds.getItemPrimaryReference(), userData);
            if (reference != null) {
                ds.setItemId(reference[0]);
                ds.setItemPrimaryReference(reference[1]);
                return ds;
            } else {
                return false;
            }
        }
        Object ret = refBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (ret instanceof InventoryReference) {
            return convertSuperToDataSource(ret, userData);
        } else if (ret == Boolean.TRUE) {
            return populateDataSourceFields(ds, userData);
        } else {
            return ret;
        }
    }
}
