/*
 * To change this template, choose Tools | Templates 
 * and open the template in the editor.
 */
package emc.bus.inventory.movestock;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.movestock.InventoryMoveStockSummaryDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryMoveStockSummaryDSBean extends EMCDataSourceBean implements InventoryMoveStockSummaryDSLocal {
    
    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryDimensionTableLocal dimensionBean;

    public InventoryMoveStockSummaryDSBean() {
        this.setDataSourceClassName(InventoryMoveStockSummaryDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryMoveStockSummaryDS ds = (InventoryMoveStockSummaryDS)dataSourceInstance;
        referenceBean.populateDSFields(ds, userData);
        dimensionBean.populateDimensions(ds, userData);
        return ds;
    }
    
    
    
}
