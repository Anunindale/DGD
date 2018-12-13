/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.developertools.poimport;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.unitsofmeasureconversion.ItemId;
import emc.datatypes.pop.purchaseordermaster.PurchaseOrderId;
import emc.datatypes.systemwide.VATCode;
import emc.entity.pop.POPPurchaseOrderLines;
import java.util.HashMap;

/**
 * @Name         : DevNLPOImportLines
 *
 * @Date         : 13 Jul 2009
 * 
 * @Description  : This entity is used to import old N & L Purchase Orders into EMC.
 *
 * @author       : riaan
 */
public class DevNLPOImportLines extends POPPurchaseOrderLines {

    private String ninianItem;
    
    /** Creates a new instance of DevNLPOImportLines. */
    public DevNLPOImportLines() {
        setDataSource(true);
        setEmcLabel("N & L Purchase Order Lines");
    }

    public String getNinianItem() {
        return ninianItem;
    }

    public void setNinianItem(String ninianItem) {
        this.ninianItem = ninianItem;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap<String, EMCDataType> toBuild = new HashMap<String, EMCDataType>();
        
        toBuild.put("purchaseOrderId", new PurchaseOrderId());
        toBuild.put("ninianItem", new ItemId());
        toBuild.put("vatCode", new VATCode());
        
        return toBuild;
    }
}
