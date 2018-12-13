/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.register;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFKNotMandatory;
import emc.datatypes.inventory.register.Warehouse;
import emc.datatypes.inventory.register.normal.RegisterQtyNormal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryRegister")
public class InventoryRegister extends InventoryRegisterSuper {

    private double width;
    private String uom;

    public InventoryRegister() {
        this.setEmcLabel("Inventory Register");
        this.setDataSource(false);
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        RegisterQtyNormal qty = new RegisterQtyNormal();
        toBuild.put("quantity", qty);
        toBuild.put("totalQty", qty);
        toBuild.put("registeredQty", qty);
        toBuild.put("uom", new UnitOfMeasureFKNotMandatory());
        toBuild.put("warehouse", new Warehouse());

        return toBuild;
    }
}
