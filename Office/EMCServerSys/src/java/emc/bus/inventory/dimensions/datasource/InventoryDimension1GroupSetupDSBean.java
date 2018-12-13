/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.dimensions.InventoryDimension1GroupSetupLocal;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.datasource.InventoryDimension1GroupSetupDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.utility.EMCServerUtilityLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless 
public class InventoryDimension1GroupSetupDSBean extends EMCDataSourceBean implements InventoryDimension1GroupSetupDSLocal {

    @EJB
    private InventoryDimension1GroupSetupLocal dimensionGroupBean;
    
    /** Creates a new instance of InventoryDimension1GroupSetupDSBean */
    public InventoryDimension1GroupSetupDSBean() {
        this.setDataSourceClassName(InventoryDimension1GroupSetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryDimension1GroupSetupDS ds = (InventoryDimension1GroupSetupDS)dataSourceInstance;
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
        query.addField("description");
        query.addAnd("dimensionId", ds.getDimensionId());
        
        ds.setDescription((String)(util.executeSingleResultQuery(query, userData)));
        
        return ds;
    }
    
    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return dimensionGroupBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }
}
