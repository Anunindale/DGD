/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimension3.BaseColour;
import emc.datatypes.inventory.dimension3.Category;
import emc.datatypes.inventory.dimension3.ColourWay;
import emc.datatypes.inventory.dimension3.DesignNo;
import emc.entity.inventory.dimensions.superclasses.InventoryDimension;
import emc.datatypes.inventory.dimension3.Dimension3;
import emc.datatypes.inventory.dimension3.SourceColRef;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryDimension3", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "dimensionId"})})
public class InventoryDimension3 extends InventoryDimension {

    private String designNo;
    private String baseColour;
    private String colourWay;
    private String sourceColRef;
    private String catagory;
    private int color;

    /** Creates a new instance of InventoryDimension3 */
    public InventoryDimension3() {
        this.setEmcLabel("Colours");
    }

    public String getBaseColour() {
        return baseColour;
    }

    public void setBaseColour(String baseColour) {
        this.baseColour = baseColour;
    }

    public String getColourWay() {
        return colourWay;
    }

    public void setColourWay(String colourWay) {
        this.colourWay = colourWay;
    }

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }

    public String getSourceColRef() {
        return sourceColRef;
    }

    public void setSourceColRef(String sourceColRef) {
        this.sourceColRef = sourceColRef;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("dimensionId", new Dimension3());
        toBuild.put("designNo", new DesignNo());
        toBuild.put("baseColour", new BaseColour());
        toBuild.put("colourWay", new ColourWay());
        toBuild.put("sourceColRef", new SourceColRef());
        toBuild.put("catagory", new Category());

        return toBuild;
    }
}
