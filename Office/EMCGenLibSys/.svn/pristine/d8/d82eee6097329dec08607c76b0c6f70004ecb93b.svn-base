/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.developertools.poimport;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.pop.purchaseordermaster.PurchaseOrderId;
import emc.datatypes.pop.supplier.foreignkeys.SupplierFK;
import emc.entity.pop.POPPurchaseOrderMaster;
import java.util.HashMap;

/**
 * @Name         : DevNLPOImportMaster
 *
 * @Date         : 13 Jul 2009
 * 
 * @Description  : This entity is used to import old N & L Purchase Orders into EMC.
 *
 * @author       : riaan
 */
public class DevNLPOImportMaster extends POPPurchaseOrderMaster {

    /** Creates a new instance of DevNLPOImportMaster. */
    public DevNLPOImportMaster() {
        setDataSource(true);
        setEmcLabel("N & L Purchase Order Master");
    }

    @Override
    public HashMap buildFieldList() {
        HashMap<String, EMCDataType> toBuild = new HashMap<String, EMCDataType>();
        
        toBuild.put("purchaseOrderId", new PurchaseOrderId());
        toBuild.put("warehouse", new WarehouseFK());
        
        return toBuild;
    }

}
