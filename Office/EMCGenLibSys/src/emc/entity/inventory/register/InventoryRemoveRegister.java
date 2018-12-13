/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.register;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.register.Warehouse;
import emc.datatypes.inventory.register.remove.RegisterQtyRemove;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryRemoveRegister")
public class InventoryRemoveRegister extends InventoryRegisterSuper {

    public InventoryRemoveRegister() {
        this.setEmcLabel("Inventory Remove Register");
        this.setDataSource(false);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        RegisterQtyRemove qty = new RegisterQtyRemove();
        toBuild.put("quantity", qty);
        toBuild.put("totalQty", qty);
        toBuild.put("registeredQty", qty);
        toBuild.put("warehouse", new Warehouse());
        return toBuild;
    }
}
