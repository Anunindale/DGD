/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions.lines;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FK;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FK;
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
@Table(name = "InventoryDimension1Lines", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimension1Id", "dimension3Id", "seq", "companyId"})})
public class InventoryDimension1Lines extends EMCEntityClass {

    private String dimension1Id;
    private String dimension3Id;
    private int seq;

    /** Creates a new instance of InventoryDimension1Lines */
    public InventoryDimension1Lines() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("dimension1Id", new Dimension1FK());
        toBuild.put("dimension3Id", new Dimension3FK());

        return toBuild;
    }

    public String getDimension1Id() {
        return dimension1Id;
    }

    public void setDimension1Id(String dimension1Id) {
        this.dimension1Id = dimension1Id;
    }

    public String getDimension3Id() {
        return dimension3Id;
    }

    public void setDimension3Id(String dimension3Id) {
        this.dimension3Id = dimension3Id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
