/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.datasource;

import emc.bus.inventory.InventoryBarcodeLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.datasource.InventoryBarcodesDS;
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
public class InventoryBarcodeDSBean extends EMCDataSourceBean implements InventoryBarcodeDSLocal{
    
@EJB
    private  InventoryBarcodeLocal superBean;
    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    public InventoryBarcodeDSBean() {
        this.setDataSourceClassName(InventoryBarcodesDS.class.getName());
    }
    
     @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return superBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return superBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return superBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
    

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryBarcodesDS ds = (InventoryBarcodesDS) dataSourceInstance;
        String[] sArray = referenceBean.checkItemReference(ds.getItemId(), userData);
        ds.setItemPrimaryReference(sArray[1]);
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        if (fieldNameToValidate.equals("itemPrimaryReference")) {
            InventoryBarcodesDS ds = (InventoryBarcodesDS) theRecord;
            String[] reference = referenceBean.checkItemReference(ds.getItemPrimaryReference(), userData);
            if (reference != null) {
                ds.setItemId(reference[0]);
                ds.setItemPrimaryReference(reference[1]);
                ds.setItemDesc(itemBean.findItemDescription(reference[0], userData));
                return ds;
            } else {
                return false;
            }
        }
        return superBean.validateField(fieldNameToValidate, theRecord, userData);
    }
}
