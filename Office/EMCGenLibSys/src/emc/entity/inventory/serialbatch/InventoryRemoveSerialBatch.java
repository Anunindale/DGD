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
@Table(name = "InventoryRemoveSerialBatch", uniqueConstraints = {@UniqueConstraint(columnNames = {})})
public class InventoryRemoveSerialBatch extends SerialBatchSuper implements Serializable {

    private String location;

    public InventoryRemoveSerialBatch() {
        this.setEmcLabel("Inventory Serial Batch");
        this.setDataSource(false);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
