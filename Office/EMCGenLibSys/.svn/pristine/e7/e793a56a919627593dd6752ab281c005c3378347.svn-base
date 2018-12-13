/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.register;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.register.Warehouse;
import emc.datatypes.inventory.register.stocktake.OnHandQty;
import emc.datatypes.inventory.register.stocktake.RegisterQtyStocktake;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryStocktakeRegister")
public class InventoryStocktakeRegister extends InventoryRegisterSuper {

    private double onHandQty;
    private double originalCountedQty;
    private int pageNumber;

    public InventoryStocktakeRegister() {
        this.setEmcLabel("Inventory Stock Take Register");
        this.setDataSource(false);
    }

    public double getOnHandQty() {
        return onHandQty;
    }

    public void setOnHandQty(double onHandQty) {
        this.onHandQty = onHandQty;
    }

    public double getOriginalCountedQty() {
        return originalCountedQty;
    }

    public void setOriginalCountedQty(double originalCountedQty) {
        this.originalCountedQty = originalCountedQty;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        RegisterQtyStocktake qty = new RegisterQtyStocktake();
        toBuild.put("quantity", qty);
        toBuild.put("totalQty", qty);
        toBuild.put("registeredQty", qty);
        toBuild.put("onHandQty", new OnHandQty());
        toBuild.put("warehouse", new Warehouse());
        return toBuild;
    }
}
