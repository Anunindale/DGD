/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.movestock;

import emc.datatypes.EMCDataType;
import emc.datatypes.emcentityclass.RecordID;
import emc.datatypes.inventory.location.foreignkeys.LocationFK;
import emc.datatypes.systemwide.Quantity;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryMoveStockLines")
public class InventoryMoveStockLines extends EMCEntityClass {

    private String toLocation;
    private double quantity;
    private long masterId;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("toLocation", new LocationFK());
        toBuild.put("quantity", new Quantity());
        toBuild.put("masterId", new RecordID());
        return toBuild;
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }
}
