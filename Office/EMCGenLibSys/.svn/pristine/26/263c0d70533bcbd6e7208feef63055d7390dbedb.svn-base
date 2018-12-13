/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.pegging;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import javax.persistence.Entity;

/**
 *
 * @author riaan
 */
@Entity
public class InventoryPeggingTable extends EMCEntityClass {

    private long supplyRef;
    private long demandRef;
    private int peggingType;
    private BigDecimal peggingQuantity = BigDecimal.ZERO;
    private BigDecimal ratioToParent = BigDecimal.ZERO;

    /** Creates a new instance of InventoryPeggingTable. */
    public InventoryPeggingTable() {
    }

    public long getSupplyRef() {
        return supplyRef;
    }

    public void setSupplyRef(long supplyRef) {
        this.supplyRef = supplyRef;
    }

    public long getDemandRef() {
        return demandRef;
    }

    public void setDemandRef(long demandRef) {
        this.demandRef = demandRef;
    }

    public int getPeggingType() {
        return peggingType;
    }

    public void setPeggingType(int peggingType) {
        this.peggingType = peggingType;
    }

    public BigDecimal getPeggingQuantity() {
        return peggingQuantity;
    }

    public void setPeggingQuantity(BigDecimal peggingQuantity) {
        this.peggingQuantity = peggingQuantity;
    }

    public BigDecimal getRatioToParent() {
        return ratioToParent;
    }

    public void setRatioToParent(BigDecimal ratioToParent) {
        this.ratioToParent = ratioToParent;
    }
}
