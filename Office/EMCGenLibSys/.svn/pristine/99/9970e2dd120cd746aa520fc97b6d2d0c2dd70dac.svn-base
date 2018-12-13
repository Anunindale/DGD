/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base;

import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.base.unitsofmeasureconversion.Conversion;
import emc.datatypes.base.unitsofmeasureconversion.DimId;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseUOMConversionTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"unit", "baseUnit", "companyId"})})
public class BaseUOMConversionTable extends EMCEntityClass {

    private String itemId;
    private String dimId;
    private String unit;
    private String baseUnit;
    private double conversion;
    
    /** Creates a new instance of BaseUOMConversionTable */
    public BaseUOMConversionTable() {
        
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDimId() {
        return dimId;
    }

    public void setDimId(String dimId) {
        this.dimId = dimId;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public double getConversion() {
        return conversion;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
       
        UnitOfMeasureFK uom = new UnitOfMeasureFK();
        
        toBuild.put("unit", uom);
        toBuild.put("baseUnit", uom);
        //toBuild.put("itemId", new ItemIdFK());
        //toBuild.put("dimId", new DimId());
        toBuild.put("conversion", new Conversion());

        return toBuild;
    }
}
