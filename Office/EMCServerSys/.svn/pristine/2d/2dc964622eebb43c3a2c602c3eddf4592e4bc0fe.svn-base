/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.datasource;

import emc.entity.inventory.datasource.InventoryItemMasterLookupDS;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemMasterLookupDSBean extends EMCEntityBean implements InventoryItemMasterLookupDSLocal {

    /** Creates a new instance of InventoryItemMasterLookupDSBean. */
    public InventoryItemMasterLookupDSBean() {
        
    }

    /** This is special.  The data selected by the query will be seperate fields, not an entire entity class. */
    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        Collection data = super.getDataInRange(theTable, userData, start, end);
        
        List<Object[]> rows = (List<Object[]>)data;
        
        
        List<EMCEntityClass> ret = new ArrayList<EMCEntityClass>();
        
        InventoryItemMasterLookupDS ds = null;
        
        /* Fields in query:
         * itemId
         * description
         * dimensionGroup
         * itemGroup
         * classificationClassGroup4
         * reference
         * refType
         */
        
        for (Object[] row : rows) {
            ds = new InventoryItemMasterLookupDS();
            
            ds.setItemId((String)row[0]);
            ds.setDescription((String)row[1]);
            ds.setDimensionGroup((String)row[2]);
            ds.setItemGroup((String)row[3]);
            ds.setClassificationClassGroup4((String)row[4]);
            ds.setReference((String)row[5]);
            ds.setRefType((String)row[6]);

            ret.add(ds);
        }
        return ret;
    }
    

}
