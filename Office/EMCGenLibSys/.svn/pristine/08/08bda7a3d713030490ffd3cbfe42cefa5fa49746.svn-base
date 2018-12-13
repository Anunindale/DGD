/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimension3groups.setup.Dimension3GroupFK;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FK;
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
@Table(name = "InventoryDimension3GroupSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimensionGroupId", "dimensionId", "companyId"})})
public class InventoryDimension3GroupSetup extends InventoryDimensionGroupSetup {
    
    /** Creates a new instance of InventoryDimension3GroupSetup */
    public InventoryDimension3GroupSetup() {
        this.setEmcLabel("Colour Group Setup");
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild =  super.buildFieldList();
        toBuild.put("dimensionGroupId", new Dimension3GroupFK());
        toBuild.put("dimensionId", new Dimension3FK());
        
        return toBuild;
    }

}
