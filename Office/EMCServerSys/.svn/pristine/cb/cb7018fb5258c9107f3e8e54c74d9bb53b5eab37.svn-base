/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.postlines.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.sop.postlines.SOPSalesOrderPostLinesLocal;
import emc.entity.sop.datasource.SOPSalesOrderPostLinesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesOrderPostLinesDSBean extends EMCDataSourceBean implements SOPSalesOrderPostLinesDSLocal {

    @EJB
    private SOPSalesOrderPostLinesLocal masterBean;
    @EJB
    private InventoryReferenceLocal itemReferenceBean;
    @EJB
    private InventoryDimensionTableLocal itemDimensionBean;

    public SOPSalesOrderPostLinesDSBean() {
        this.setDataSourceClassName(SOPSalesOrderPostLinesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPSalesOrderPostLinesDS ds = (SOPSalesOrderPostLinesDS) dataSourceInstance;
        itemReferenceBean.populateDSFields(ds, userData);
        itemDimensionBean.populateDimensions(ds, userData);
        return ds;
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

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            SOPSalesOrderPostLinesDS record = (SOPSalesOrderPostLinesDS) theRecord;
            if (fieldNameToValidate.equals("pickQuantity") || fieldNameToValidate.equals("assembleQuantity")) {
                if (record.getMaxQuantity().compareTo(record.getPickQuantity().add(record.getAssembleQuantity()).add(record.getKimbleQuantity())) < 0) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The sum of the pick, assemble and kimble quantity may not be greater than " + record.getMaxQuantity(), userData);
                    return false;
                }
            } else if (fieldNameToValidate.equals("backOrderQuantity")) {
                if (record.getMaxQuantity().compareTo(record.getBackOrderQuantity()) < 0) {
                              Logger.getLogger("emc").log(Level.SEVERE, "The back order quantity may not be greater than " + record.getMaxQuantity(), userData);
                    return false;

                }
            } else if (fieldNameToValidate.equals("distributionOrderReleaseQty")) {
                if (record.getMaxQuantity().compareTo(record.getDistributionOrderReleaseQty()) < 0) {
                              Logger.getLogger("emc").log(Level.SEVERE, "The distribution quantity may not be greater than " + record.getMaxQuantity(), userData);
                    return false;

                }
            }
        }
        return ret;
    }
}
