package emc.entity.inventory.classifications;

import emc.entity.inventory.classifications.superclasses.InventoryItemClassification;
import emc.helpers.debtors.royalty.RoyaltyInterface;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryItemClassification6", uniqueConstraints={@UniqueConstraint(columnNames={"classification", "companyId"})})
public class InventoryItemClassification6 extends InventoryItemClassification implements RoyaltyInterface {
    
    /** Creates a new instance of InventoryItemClassification6 */
    public InventoryItemClassification6() {
        
    }

    public String getFieldToDisplay() {
        return this.getDescription();
    }
    
}
