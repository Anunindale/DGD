/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimension2groups.setup.Dimension2GroupFK;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FK;
import emc.entity.inventory.dimensions.superclasses.InventoryDimensionGroupSetup;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryDimension2GroupSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimensionGroupId", "dimensionId", "companyId"})})
public class InventoryDimension2GroupSetup extends InventoryDimensionGroupSetup {
          
    /** Creates a new instance of InventoryDimension2GroupSetup */
    public InventoryDimension2GroupSetup() {
        this.setEmcLabel("Size Group Setup");
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild =  super.buildFieldList();
        toBuild.put("dimensionGroupId", new Dimension2GroupFK());
        toBuild.put("dimensionId", new Dimension2FK());
        
        return toBuild;
    }

}
