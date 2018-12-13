/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.colourdisignmaster;

import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.inventory.colourdesignmaster.Claimed;
import emc.datatypes.inventory.colourdesignmaster.DesignNo;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryColourDisignMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"designNo", "companyId"})})
public class InventoryColourDesignMaster extends EMCEntityClass {

    private String designNo;
    private String description;
    private Double thresholdQty;
    private String uom;
    private Double receivedQty;
    private Boolean claimed;
    private double designPerc;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }

    public Double getThresholdQty() {
        return thresholdQty == null ? 0.0 : thresholdQty;
    }

    public void setThresholdQty(Double thresholdQty) {
        this.thresholdQty = thresholdQty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getReceivedQty() {
        return receivedQty == null ? 0.0 : receivedQty;
    }

    public void setReceivedQty(Double receivedQty) {
        this.receivedQty = receivedQty;
    }

    public boolean getClaimed() {
        return claimed == null ? false : claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }

    public double getDesignPerc() {
        return designPerc;
    }

    public void setDesignPerc(double designPerc) {
        this.designPerc = designPerc;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("designNo");
        return query;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("designNo", new DesignNo());
        toBuild.put("description", new Description());
        toBuild.put("uom", new UnitOfMeasureFK());
        toBuild.put("claimed", new Claimed());

        return toBuild;
    }
}
