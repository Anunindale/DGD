/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.serialbatch;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventorySerialBatch", uniqueConstraints={@UniqueConstraint(columnNames={})})
public class InventorySerialBatch extends SerialBatchSuper implements Serializable{
    
    public InventorySerialBatch() {
        this.setEmcLabel("Inventory Serial Batch");
        this.setDataSource(false);
    }
    
}
