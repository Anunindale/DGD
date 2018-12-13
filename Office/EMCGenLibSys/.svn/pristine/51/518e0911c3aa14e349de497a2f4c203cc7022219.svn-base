/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.register.Warehouse;
import emc.entity.inventory.register.InventoryRegisterSuper;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "SOPSalesOrderPostRegister")
public class SOPSalesOrderPostRegister extends InventoryRegisterSuper {

    public SOPSalesOrderPostRegister() {
        this.setEmcLabel("Sales Order Post Register");
        this.setDataSource(false);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("warehouse", new Warehouse());

        return toBuild;
    }
}
