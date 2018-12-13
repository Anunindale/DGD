/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.invoiceregister.Quantity;
import emc.datatypes.creditors.invoiceregister.RegisteredQuantity;
import emc.datatypes.creditors.invoiceregister.TotalQuantity;
import emc.datatypes.inventory.register.Warehouse;
import emc.entity.inventory.register.InventoryRegisterSuper;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CreditorsInvoiceRegister")
public class CreditorsInvoiceRegister extends InventoryRegisterSuper {

    private double originalQuantity;
    private String newBatch;

    /** Creates a new instance of DebtorsCreditNoteRegister. */
    public CreditorsInvoiceRegister() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("quantity", new Quantity());
        toBuild.put("registeredQty", new RegisteredQuantity());
        toBuild.put("totalQty", new TotalQuantity());
        toBuild.put("warehouse", new Warehouse());

        return toBuild;
    }

    public double getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(double originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    @Override
    public List<String> buildFieldListToClearOnCopy() {
        List<String> fieldsToClear = super.buildFieldListToClearOnCopy();

        fieldsToClear.add("quantity");
        fieldsToClear.add("originalQuantity");

        return fieldsToClear;
    }

    public String getNewBatch() {
        return newBatch;
    }

    public void setNewBatch(String newBatch) {
        this.newBatch = newBatch;
    }
}
