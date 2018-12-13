/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.dimensions.InventoryDimension3GroupSetupLocal;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.datasource.InventoryDimension3GroupSetupDS;
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
public class InventoryDimension3GroupSetupDSBean extends EMCDataSourceBean implements InventoryDimension3GroupSetupDSLocal {

    @EJB
    private InventoryDimension3GroupSetupLocal dimensionGroupBean;

    /** Creates a new instance of InventoryDimension3GroupSetupDSBean */
    public InventoryDimension3GroupSetupDSBean() {
        this.setDataSourceClassName(InventoryDimension3GroupSetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryDimension3GroupSetupDS ds = (InventoryDimension3GroupSetupDS) dataSourceInstance;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
        query.addField("description");
        query.addAnd("dimensionId", ds.getDimensionId());

        ds.setDescription((String) (util.executeSingleResultQuery(query, userData)));

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return dimensionGroupBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }
}
