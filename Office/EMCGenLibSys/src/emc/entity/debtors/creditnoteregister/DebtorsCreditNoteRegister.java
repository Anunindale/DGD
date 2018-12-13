/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.creditnoteregister;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditnoteregister.Quantity;
import emc.datatypes.debtors.creditnoteregister.RegisteredQuantity;
import emc.datatypes.debtors.creditnoteregister.TotalQuantity;
import emc.datatypes.inventory.register.Warehouse;
import emc.entity.inventory.register.InventoryRegisterSuper;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsCreditNoteRegister")
public class DebtorsCreditNoteRegister extends InventoryRegisterSuper {

    //Original quantity.  This may not be exceeded
    private double originalQuantity;

    //N & L Specific.  Only applies to renumbering on Credit Notes returned to stock.
    private String newBatch;
    
    /** Creates a new instance of DebtorsCreditNoteRegister. */
    public DebtorsCreditNoteRegister() {
        
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
