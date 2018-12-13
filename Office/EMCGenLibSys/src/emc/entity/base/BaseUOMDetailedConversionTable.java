/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.base.unitsofmeasureconversion.Conversion;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseUOMDetailedConversionTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"masterRecordID", "itemId", "companyId"})})
public class BaseUOMDetailedConversionTable extends EMCEntityClass {

    private long masterRecordID;
    private String itemId;
    private double conversion;

    public BaseUOMDetailedConversionTable() {
    }

    public double getConversion() {
        return conversion;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public long getMasterRecordID() {
        return masterRecordID;
    }

    public void setMasterRecordID(long masterRecordID) {
        this.masterRecordID = masterRecordID;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("conversion", new Conversion());
        return toBuild;
    }
}
