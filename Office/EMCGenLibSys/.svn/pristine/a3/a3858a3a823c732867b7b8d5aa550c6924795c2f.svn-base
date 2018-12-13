/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base;

import emc.datatypes.base.unitsofmeasure.Decimals;
import emc.datatypes.base.unitsofmeasure.UOMType;
import emc.datatypes.base.unitsofmeasure.UnitOfMeasureNoFK;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseUnitsOfMeasure", uniqueConstraints = {@UniqueConstraint(columnNames = {"unit", "companyId"})})
public class BaseUnitsOfMeasure extends EMCEntityClass implements Serializable {

    private String unit;
    private String description;
    @Column(name = "decimals")
    private int decimals;
    private String type;
    
    /** Creates a new instance of BaseUnitsOfMeasure */
    public BaseUnitsOfMeasure() {
        
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("unit", new UnitOfMeasureNoFK());
        toBuild.put("description", new Description());
        toBuild.put("decimals", new Decimals());
        toBuild.put("type", new UOMType());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("unit");
        toBuild.add("description");
        return toBuild;
    }
    
}
