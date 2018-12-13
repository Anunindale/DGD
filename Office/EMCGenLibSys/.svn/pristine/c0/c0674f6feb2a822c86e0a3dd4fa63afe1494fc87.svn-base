/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.pop.cancelledpurchaseorders;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.cancelledpurchaseordermaster.CancelledPurchaseOrderId;
import emc.entity.pop.purchaseordersuperclass.POPPurchaseOrderMasterSuper;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPCancelledPurchaseOrderMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"purchaseOrderId", "companyId"})})
public class POPCancelledPurchaseOrderMaster extends POPPurchaseOrderMasterSuper {

    /** Creates a new instance of POPPCancelledPurchaseOrderMaster */
    public POPCancelledPurchaseOrderMaster() {
        this.setEmcLabel("Cancelled Purchase Order Master");
        this.setDataSource(false);
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        
        toBuild.put("purchaseOrderId", new CancelledPurchaseOrderId());
        
        return toBuild;
    }

    @Override
    public EMCQuery buildLookupQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPCancelledPurchaseOrderMaster.class.getName());

        query.addOrderBy("purchaseOrderId");

        return query;
    }
 
}
