/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.grnreprint.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.pop.POPSupplierLocal;
import emc.bus.pop.grnreprint.POPGRNReprintTempLocal;
import emc.entity.pop.grnreprint.datasource.POPGRNReprintTempDS;
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
public class POPGRNReprintTempDSBean extends EMCDataSourceBean implements POPGRNReprintTempDSLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimLogicBean;
    @EJB
    private POPGRNReprintTempLocal masterBean;
    @EJB
    private POPSupplierLocal suppBean;

    public POPGRNReprintTempDSBean() {
        this.setDataSourceClassName(POPGRNReprintTempDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPGRNReprintTempDS record = (POPGRNReprintTempDS) dataSourceInstance;
        if (!isBlank(record.getItemId())) {
            referenceBean.populateDSFields(record, userData);
        }
        if (!isBlank(record.getSupplierCode())) {
            record.setSuppName(suppBean.getSupplierName(record.getSupplierCode(), userData));
        }
        return record;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        boolean retDS = false;
        if (ret) {
            POPGRNReprintTempDS ds = (POPGRNReprintTempDS) theRecord;
            if (fieldNameToValidate.equals("itemReference")) {
                referenceBean.doItemRefValidation(ds, userData);
                retDS = true;
            }
            if (fieldNameToValidate.equals("supplierCode")) {
                ds.setSuppName(suppBean.getSupplierName(ds.getSupplierCode(), userData));
                retDS = true;
            }
            if (fieldNameToValidate.equals("dimension1") || fieldNameToValidate.equals("dimension2") || fieldNameToValidate.equals("dimension3")) {
                ret = dimLogicBean.validateDimensionValues(ds.getItemId(), ds.getDimension1(), ds.getDimension2(), ds.getDimension3(), userData);
                retDS = false;
            }
            if (ret && isBlank(ds.getDateRecieved())) {
                ds.setDateRecieved(new java.util.Date());
                retDS = true;
            }
            if (retDS && ret) {
                return ds;
            }
        }
        return ret;
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
            return masterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
            return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}
