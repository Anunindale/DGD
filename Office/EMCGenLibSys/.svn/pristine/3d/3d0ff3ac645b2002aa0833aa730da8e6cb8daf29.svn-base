/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimension1groups.setup.Dimension1GroupFK;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FK;
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
@Table(name = "InventoryDimension1GroupSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimensionGroupId", "dimensionId", "companyId"})})
public class InventoryDimension1GroupSetup extends InventoryDimensionGroupSetup {

    /** Creates a new instance of InventoryDimension1GroupSetup */
    public InventoryDimension1GroupSetup() {
        this.setEmcLabel("Config Group Setup");
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild =  super.buildFieldList();
         toBuild.put("dimensionGroupId", new Dimension1GroupFK());
        toBuild.put("dimensionId", new Dimension1FK());
        
        return toBuild;
    }
}
