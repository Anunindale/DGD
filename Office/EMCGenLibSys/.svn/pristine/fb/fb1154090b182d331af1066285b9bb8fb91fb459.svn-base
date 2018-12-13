/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.pop.cancelledpurchaseorders;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.cancelledpurchaseordermaster.foreignkeys.CancelledPurchaseOrderIdFK;
import emc.entity.pop.purchaseordersuperclass.POPPurchaseOrderLinesSuper;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPCancelledPurchaseOrderLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"lineNo", "purchaseOrderId", "companyId"})})
public class POPCancelledPurchaseOrderLines extends POPPurchaseOrderLinesSuper {

    /** Creates a new instance of POPPCancelledPurchaseOrderLines */
    public POPCancelledPurchaseOrderLines() {
        this.setEmcLabel("Cancelled Purchase Order Lines");
        this.setDataSource(false);
    }
    
    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        
        toBuild.put("purchaseOrderId", new CancelledPurchaseOrderIdFK());
        
        return toBuild;
    }
}
