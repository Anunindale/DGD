/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.safetystock.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.safetystock.InventorySafetyStockLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.datasource.InventorySafetyStockDS;
import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventorySafetyStockDSBean extends EMCDataSourceBean implements InventorySafetyStockDSLocal {

    @EJB
    private InventorySafetyStockLocal masterBean;
    @EJB
    private InventoryReferenceLocal itemReferenceBean;

    public InventorySafetyStockDSBean() {
        this.setDataSourceClassName(InventorySafetyStockDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventorySafetyStockDS ds = (InventorySafetyStockDS) dataSourceInstance;
        //Set Customer Name
        if (!isBlank(ds.getCustomerId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            query.addAnd("customerId", ds.getCustomerId());
            query.addField("customerName");
            ds.setCustomerName((String) util.executeSingleResultQuery(query, userData));
        }
        //Set Item Reference and Description
        if (!isBlank(ds.getItemId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addAnd("itemId", ds.getItemId());
            query.addField("itemReference");
            query.addField("description");
            Object[] itemInfo = (Object[]) util.executeSingleResultQuery(query, userData);
            if (itemInfo != null) {
                if (itemInfo.length >= 1 && !isBlank(itemInfo[0])) {
                    ds.setItemReference((String) itemInfo[0]);
                } else {
                    ds.setItemReference(ds.getItemId());
                }
                if (itemInfo.length >= 2 && !isBlank(itemInfo[1])) {
                    ds.setItemDescription((String) itemInfo[1]);
                }

            }
        }
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object valid = super.validateField(fieldNameToValidate, theRecord, userData);

        if (valid instanceof Boolean && (Boolean) valid) {
            InventorySafetyStockDS ds = (InventorySafetyStockDS) theRecord;

            if (fieldNameToValidate.equals("itemReference")) {
                if (!isBlank(ds.getItemReference())) {
                    String[] itemInfo = itemReferenceBean.checkItemReference(ds.getItemReference(), userData);
                    if (itemInfo == null) {
                        return false;
                    } else {
                        ds.setItemId(itemInfo[0]);
                        ds.setItemReference(itemInfo[1]);

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
                        query.addAnd("itemId", ds.getItemId());
                        query.addField("description");

                        ds.setItemDescription((String) util.executeSingleResultQuery(query, userData));
                    }
                } else {
                    ds.setItemDescription(null);
                    ds.setItemId(null);
                    ds.setItemReference(null);
                }
                return ds;
            } else if (fieldNameToValidate.equals("customerId")) {
                if (!isBlank(ds.getCustomerId())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                    query.addAnd("customerId", ds.getCustomerId());
                    query.addField("customerName");
                    ds.setCustomerName((String) util.executeSingleResultQuery(query, userData));
                } else {
                    ds.setCustomerName(null);
                }
                return ds;
            }
        }
        return valid;
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
}
