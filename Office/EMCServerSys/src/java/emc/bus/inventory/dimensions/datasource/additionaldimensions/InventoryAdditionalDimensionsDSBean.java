/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource.additionaldimensions;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensionsLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.additionaldimensions.datasource.InventoryAdditionalDimensionsDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : InventoryAdditionalDimensionsDSBean
 *
 * @Date         : 25 Jun 2009
 * 
 * @Description  : Bean for the InventoryAdditionalDimensionsDS datasource.
 *
 * @author       : riaan
 */
@Stateless
public class InventoryAdditionalDimensionsDSBean extends EMCDataSourceBean implements InventoryAdditionalDimensionsDSLocal {

    @EJB
    private InventoryAdditionalDimensionsLocal additionalDimsBean;
    @EJB
    private InventoryDimensionTableLocal dimTableBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    
    /** Creates a new instance of InventoryAdditionalDimensionsDSBean. */
    public InventoryAdditionalDimensionsDSBean() {
        this.setDataSourceClassName(InventoryAdditionalDimensionsDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return additionalDimsBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }
    
    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return additionalDimsBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }
    
    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return additionalDimsBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
    
    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return additionalDimsBean.validateField(fieldNameToValidate, theRecord, userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryAdditionalDimensionsDS ds = (InventoryAdditionalDimensionsDS)dataSourceInstance;
        
        //Easier than using reference bean.  Item and reference table should be in sync.
        InventoryItemMaster item = itemMasterBean.findItem(ds.getItemId(), userData);
        
        if (item != null) {
            ds.setItemRef(item.getItemReference());
        }
        
        InventoryDimensionTable dimTable = dimTableBean.getDimensionRecord(ds.getDimensionId(), userData);
        
        if (dimTable != null) {
            ds.setBatch(dimTable.getBatchId());
            ds.setSerial(dimTable.getItemSerialId());
            ds.setDimension1(dimTable.getDimension1Id());
            ds.setDimension2(dimTable.getDimension2Id());
            ds.setDimension3(dimTable.getDimension3Id());
        }
        
        return ds;
    }

}
