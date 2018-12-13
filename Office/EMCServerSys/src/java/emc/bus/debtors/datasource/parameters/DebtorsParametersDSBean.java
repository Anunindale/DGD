/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.parameters;

import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.debtors.datasource.DebtorsParametersDS;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Data source bean for DebtorsParameters.
 *
 * @date : 23 Jul 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
@Stateless
public class DebtorsParametersDSBean extends EMCDataSourceBean implements DebtorsParametersDSLocal {

    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    /**
     * Creates a new instance of DebtorsParametersDSBean
     */
    public DebtorsParametersDSBean() {
        this.setDataSourceClassName(DebtorsParametersDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return parametersBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return parametersBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return parametersBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsParametersDS ds = (DebtorsParametersDS) dataSourceInstance;

        InventoryItemMaster item = itemBean.findItem(ds.getHandlingChargeItem(), userData);

        if (item != null) {
            ds.setHandlingChargeItemRef(item.getItemReference());
        }

        item = itemBean.findItem(ds.getDeliveryChargeItem(), userData);

        if (item != null) {
            ds.setDeliveryChargeItemRef(item.getItemReference());
        }

        item = itemBean.findItem(ds.getDefaultItem(), userData);

        if (item != null) {
            ds.setDefaultItemRef(item.getItemReference());
        }

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = parametersBean.validateField(fieldNameToValidate, theRecord, userData);

        if (ret instanceof Boolean && !(Boolean) ret) {
            return ret;
        } else {
            DebtorsParametersDS ds = (DebtorsParametersDS) theRecord;

            if (fieldNameToValidate.equals("handlingChargeItemRef")) {
                String[] itemRef = refBean.checkItemReference(ds.getHandlingChargeItemRef(), userData);

                if (itemRef != null) {
                    ds.setHandlingChargeItem(itemRef[0]);
                    ds.setHandlingChargeItemRef(itemRef[1]);
                } else {
                    return false;
                }

                InventoryItemMaster item = itemBean.findItem(ds.getHandlingChargeItem(), userData);

                if (!InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.HAND_ITEM_TYPE, userData, InventoryItemTypes.SERVICE.toString());
                    return false;
                }
            } else if (fieldNameToValidate.equals("deliveryChargeItemRef")) {
                String[] itemRef = refBean.checkItemReference(ds.getDeliveryChargeItemRef(), userData);

                if (itemRef != null) {
                    ds.setDeliveryChargeItem(itemRef[0]);
                    ds.setDeliveryChargeItemRef(itemRef[1]);
                } else {
                    return false;
                }

                InventoryItemMaster item = itemBean.findItem(ds.getDeliveryChargeItem(), userData);

                if (!InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.DEL_ITEM_TYPE, userData, InventoryItemTypes.SERVICE.toString());
                    return false;
                }
            } else if (fieldNameToValidate.equals("defaultItemRef")) {
                String[] itemRef = refBean.checkItemReference(ds.getDefaultItemRef(), userData);

                if (itemRef != null) {
                    ds.setDefaultItem(itemRef[0]);
                    ds.setDefaultItemRef(itemRef[1]);
                } else {
                    return false;
                }

                InventoryItemMaster item = itemBean.findItem(ds.getDefaultItem(), userData);

                if (!InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.DEL_ITEM_TYPE, userData, InventoryItemTypes.SERVICE.toString());
                    return false;
                }
            }

            return ds;
        }
    }
}
