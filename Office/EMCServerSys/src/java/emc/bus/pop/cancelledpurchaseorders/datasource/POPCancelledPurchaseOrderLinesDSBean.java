/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.cancelledpurchaseorders.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.pop.cancelledpurchaseorders.datasource.POPCancelledPurchaseOrderLinesDS;
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
public class POPCancelledPurchaseOrderLinesDSBean extends EMCDataSourceBean implements POPCancelledPurchaseOrderLinesDSLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;

    public POPCancelledPurchaseOrderLinesDSBean() {
        this.setDataSourceClassName(POPCancelledPurchaseOrderLinesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPCancelledPurchaseOrderLinesDS record = (POPCancelledPurchaseOrderLinesDS) dataSourceInstance;
        referenceBean.populateDSFields(record, userData);
        return record;
    }
}
