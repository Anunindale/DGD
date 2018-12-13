/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.planning;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.pop.planning.datasource.POPPlannedPurchaseOrdersDS;
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
public class POPPlannedPurchaseOrdersDSBean extends EMCDataSourceBean implements POPPlannedPurchaseOrdersDSLocal {

    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private POPPlannedPurchaseOrdersLocal masterBean;

    public POPPlannedPurchaseOrdersDSBean() {
        this.setDataSourceClassName(POPPlannedPurchaseOrdersDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPPlannedPurchaseOrdersDS ds = (POPPlannedPurchaseOrdersDS) dataSourceInstance;
        referenceBean.populateDSFields(ds, userData);

        if (!isBlank(ds.getItemId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addAnd("itemId", ds.getItemId());
            query.addField("itemGroup");
            ds.setItemGroup((String)util.executeSingleResultQuery(query, userData));
        }
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        if (fieldNameToValidate.equals("itemReference")) {
            POPPlannedPurchaseOrdersDS ds = (POPPlannedPurchaseOrdersDS) theRecord;
            if (referenceBean.doItemRefValidation(ds, userData)) {
                InventoryItemMaster item = itemBean.findItem(ds.getItemId(), userData);
                ds.setUom(item.getPurchaseUOM());
                ds.setVatCode(item.getPurchaseVatCode());
                ds.setItemPrice(item.getPurchasePrice());
                return ds;
            } else {
                return false;
                
            }
        }
        return masterBean.validateField(fieldNameToValidate, theRecord, userData);
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
