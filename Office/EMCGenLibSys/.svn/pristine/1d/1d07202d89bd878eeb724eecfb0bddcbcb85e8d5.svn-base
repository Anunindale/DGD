/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource.nlcutplan;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;
import emc.datatypes.sop.cutplan.RequiredDate;
import emc.datatypes.sop.salesorderlines.Dimension1;
import emc.datatypes.sop.salesorderlines.Dimension2;
import emc.datatypes.sop.salesorderlines.Dimension3;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "SOPNLCutPlan")
public class SOPNLCutPlan extends EMCEntityClass {

    private String itemId;
    private String itemReference;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String type;
    private BigDecimal qty;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;
    private String class6;

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the itemReference
     */
    public String getItemReference() {
        return itemReference;
    }

    /**
     * @param itemReference the itemReference to set
     */
    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    /**
     * @return the dimension1
     */
    public String getDimension1() {
        return dimension1;
    }

    /**
     * @param dimension1 the dimension1 to set
     */
    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    /**
     * @return the dimension2
     */
    public String getDimension2() {
        return dimension2;
    }

    /**
     * @param dimension2 the dimension2 to set
     */
    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    /**
     * @return the dimension3
     */
    public String getDimension3() {
        return dimension3;
    }

    /**
     * @param dimension3 the dimension3 to set
     */
    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the qty
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    /**
     * @return the requiredDate
     */
    public Date getRequiredDate() {
        return requiredDate;
    }

    /**
     * @param requiredDate the requiredDate to set
     */
    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap map = super.buildFieldList();
        map.put("requiredDate", new RequiredDate());
        map.put("itemReference", new ItemReferenceFK());
        map.put("dimension1", new Dimension1());
        map.put("dimension2", new Dimension2());
        map.put("dimension3", new Dimension3());
        return map;
    }

    /**
     * @return the class6
     */
    public String getClass6() {
        return class6;
    }

    /**
     * @param class6 the class6 to set
     */
    public void setClass6(String class6) {
        this.class6 = class6;
    }
}
