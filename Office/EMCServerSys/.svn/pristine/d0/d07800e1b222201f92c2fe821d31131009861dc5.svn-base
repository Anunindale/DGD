/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.dimensions.InventoryDimension2GroupSetupLocal;
import emc.bus.inventory.dimensions.InventoryDimension2Local;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.datasource.InventoryDimension2GroupSetupDS;
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
 * @author riaan
 */
@Stateless
public class InventoryDimension2GroupSetupDSBean extends EMCDataSourceBean implements InventoryDimension2GroupSetupDSLocal {

    @EJB
    private InventoryDimension2GroupSetupLocal dimensionGroupBean;
    @EJB
    private InventoryDimension2Local dimensionBean;

    /** Creates a new instance of InventoryDimension2GroupSetupDSBean */
    public InventoryDimension2GroupSetupDSBean() {
        this.setDataSourceClassName(InventoryDimension2GroupSetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryDimension2GroupSetupDS ds = (InventoryDimension2GroupSetupDS) dataSourceInstance;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        query.addAnd("dimensionId", ds.getDimensionId());
        InventoryDimension2 dim2 = (InventoryDimension2) util.executeSingleResultQuery(query, userData);
        ds.setDescription(dim2.getDescription());
        ds.setSortCode(dim2.getSortCode());

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return dimensionGroupBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryDimension2GroupSetupDS ds = (InventoryDimension2GroupSetupDS) iobject;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        query.addAnd("dimensionId", ds.getDimensionId());
        InventoryDimension2 dim2 = (InventoryDimension2) util.executeSingleResultQuery(query, userData);
        if (dim2 != null && dim2.getSortCode() != ds.getSortCode()) {
            dim2.setSortCode(ds.getSortCode());
            dimensionBean.update(dim2, userData);
        }
        return dimensionGroupBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryDimension2GroupSetupDS ds = (InventoryDimension2GroupSetupDS) uobject;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        query.addAnd("dimensionId", ds.getDimensionId());
        InventoryDimension2 dim2 = (InventoryDimension2) util.executeSingleResultQuery(query, userData);
        if (dim2 != null && dim2.getSortCode() != ds.getSortCode()) {
            dim2.setSortCode(ds.getSortCode());
            dimensionBean.update(dim2, userData);
        }
        return dimensionGroupBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}
