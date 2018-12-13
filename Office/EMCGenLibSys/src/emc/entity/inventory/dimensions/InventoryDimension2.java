/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.entity.inventory.dimensions.superclasses.InventoryDimension;
import emc.datatypes.inventory.dimension2.Dimension2;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name= "InventoryDimension2", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "dimensionId"})})
public class InventoryDimension2 extends InventoryDimension {

    /** Creates a new instance of InventoryDimension2 */
    public InventoryDimension2() {
        this.setEmcLabel("Sizes");
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("dimensionId", new Dimension2());
        
        return toBuild;
    }
    
}
