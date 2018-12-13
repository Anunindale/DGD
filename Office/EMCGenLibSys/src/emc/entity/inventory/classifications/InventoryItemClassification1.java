/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.classifications;

import emc.entity.inventory.classifications.superclasses.InventoryItemClassification;
import emc.helpers.debtors.royalty.RoyaltyInterface;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryItemClassification1", uniqueConstraints = {@UniqueConstraint(columnNames = {"classification", "companyId"})})
public class InventoryItemClassification1 extends InventoryItemClassification implements RoyaltyInterface {

    /** Creates a new instance of InventoryItemClassification */
    public InventoryItemClassification1() {
        
    }

    public String getFieldToDisplay() {
        return this.getDescription();
    }

    
    
}
