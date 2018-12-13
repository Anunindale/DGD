/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.datasource;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.datasource.InventoryItemMasterDS;
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
public class InventoryItemMasterDSBean extends EMCDataSourceBean implements InventoryItemMasterDSLocal {
    
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryReferenceLocal referenceBean;

    public InventoryItemMasterDSBean() {
        this.setDataSourceClassName(InventoryItemMasterDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return itemBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return itemBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return itemBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryItemMasterDS ds = (InventoryItemMasterDS)dataSourceInstance;
        if(ds.getPlanningSubstituteItem() != null){
            String [] sArray = referenceBean.checkItemReference(ds.getPlanningSubstituteItem(), userData);
            ds.setSubItemPrimaryReference(sArray[1]);
        }
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = itemBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (ret instanceof InventoryItemMaster || (Boolean)ret) {
            InventoryItemMasterDS ds = ret instanceof InventoryItemMaster ? (InventoryItemMasterDS)convertSuperToDataSource(ret, userData) : (InventoryItemMasterDS)theRecord;
            ret = ds;
            if (fieldNameToValidate.equals("subItemPrimaryReference")) {
                //InventoryItemMasterDS ds = ret instanceof InventoryItemMaster ? (InventoryItemMasterDS)convertSuperToDataSource(ret, userData) : (InventoryItemMasterDS)theRecord;
                String[] reference = referenceBean.checkItemReference(ds.getSubItemPrimaryReference(), userData);
                if (reference != null) {
                    ds.setPlanningSubstituteItem(reference[0]);
                    ds.setSubItemPrimaryReference(reference[1]);
                    ret = ds;
                } else {
                    ret = false;
                }
            }
        }
        return ret;
    }
}
