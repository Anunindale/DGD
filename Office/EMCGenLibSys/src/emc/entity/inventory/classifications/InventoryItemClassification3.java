package emc.entity.inventory.classifications;

import emc.entity.inventory.classifications.superclasses.InventoryItemClassification;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryItemClassification3", uniqueConstraints={@UniqueConstraint(columnNames={"classification", "companyId"})})
public class InventoryItemClassification3 extends InventoryItemClassification {

    /** Creates a new instance of InventoryItemClassification2 */
    public InventoryItemClassification3() {
        
    }
    
}
