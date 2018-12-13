/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.pallet.PalletPK;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryPallet", uniqueConstraints = {@UniqueConstraint(columnNames = {"palletId", "companyId"})})
public class InventoryPallet extends EMCEntityClass {

    private String palletId;
    private String description;

    /** Creates a new instance of InventoryPalletTable */
    public InventoryPallet() {

    }

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();
        ret.add("palletId");
        ret.add("description");
        return ret;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("palletId", new PalletPK());
        toBuild.put("description", new Description());
        return toBuild;
    }
}
