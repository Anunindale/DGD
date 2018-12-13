/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.dailyfigures.AtDate;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPDailyFigures", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"asAtDate", "companyId"})})
public class SOPDailyFigures extends EMCEntityClass {

    @Temporal(TemporalType.DATE)
    private Date asAtDate;
    private BigDecimal grossTotalInv;
    private BigDecimal sundriInv;
    private BigDecimal salesValueInv;
    private BigDecimal discountTotalInv;
    private BigDecimal qtyTotalCrates;
    private BigDecimal bin1QtyCgImp;
    private BigDecimal bin2QtyCgImp;
    private BigDecimal bin3QtyCgImp;
    private BigDecimal bin4QtyCgImp;
    private BigDecimal bin1QtyCgLoc;
    private BigDecimal bin2QtyCgLoc;
    private BigDecimal bin3QtyCgLoc;
    private BigDecimal bin4QtyCgLoc;
    private BigDecimal bin1QtyFg;
    private BigDecimal bin2QtyFg;
    private BigDecimal bin3QtyFg;
    private BigDecimal bin4QtyFg;
    private BigDecimal workCenterIdSw;
    private BigDecimal finishedSw;
    private BigDecimal rejectedSw;
    private BigDecimal unitsPk;
    private BigDecimal qtyReceivedFgCMT;
    private BigDecimal qtyReceivedCgImp;
    private BigDecimal qtyReceivedCgCMT;
    private BigDecimal qtyReceivedFgImp;
    private BigDecimal unitsAllDesp;
    private BigDecimal costAllDesp;
    private BigDecimal unitsImpDesp;
    private BigDecimal costImpDesp;
    private BigDecimal unitsDI;
    private BigDecimal costDI;
    private BigDecimal bin1QtyFabKg;
    private BigDecimal bin2QtyFabKg;
    private BigDecimal bin3QtyFabKg;
    private BigDecimal bin4QtyFabKg;
    private BigDecimal bin1QtyFabMtr;
    private BigDecimal bin2QtyFabMtr;
    private BigDecimal bin3QtyFabMtr;
    private BigDecimal bin4QtyFabMtr;
    private BigDecimal inCuttingUnits;
    private BigDecimal inSewingUnits;
    private BigDecimal toPickUnits;
    private BigDecimal inPackingUnits;
    private BigDecimal finishedGoodsUnits;

    public Date getAsAtDate() {
        return asAtDate;
    }

    public void setAsAtDate(Date asAtDate) {
        this.asAtDate = asAtDate;
    }

    public BigDecimal getGrossTotalInv() {
        return grossTotalInv;
    }

    public void setGrossTotalInv(BigDecimal grossTotalInv) {
        this.grossTotalInv = grossTotalInv;
    }

    public BigDecimal getSundriInv() {
        return sundriInv;
    }

    public void setSundriInv(BigDecimal sundriInv) {
        this.sundriInv = sundriInv;
    }

    public BigDecimal getSalesValueInv() {
        return salesValueInv;
    }

    public void setSalesValueInv(BigDecimal salesValueInv) {
        this.salesValueInv = salesValueInv;
    }

    public BigDecimal getDiscountTotalInv() {
        return discountTotalInv;
    }

    public void setDiscountTotalInv(BigDecimal discountTotalInv) {
        this.discountTotalInv = discountTotalInv;
    }

    public BigDecimal getQtyTotalCrates() {
        return qtyTotalCrates;
    }

    public void setQtyTotalCrates(BigDecimal qtyTotalCrates) {
        this.qtyTotalCrates = qtyTotalCrates;
    }

    public BigDecimal getBin1QtyCgImp() {
        return bin1QtyCgImp;
    }

    public void setBin1QtyCgImp(BigDecimal bin1QtyCgImp) {
        this.bin1QtyCgImp = bin1QtyCgImp;
    }

    public BigDecimal getBin2QtyCgImp() {
        return bin2QtyCgImp;
    }

    public void setBin2QtyCgImp(BigDecimal bin2QtyCgImp) {
        this.bin2QtyCgImp = bin2QtyCgImp;
    }

    public BigDecimal getBin3QtyCgImp() {
        return bin3QtyCgImp;
    }

    public void setBin3QtyCgImp(BigDecimal bin3QtyCgImp) {
        this.bin3QtyCgImp = bin3QtyCgImp;
    }

    public BigDecimal getBin4QtyCgImp() {
        return bin4QtyCgImp;
    }

    public void setBin4QtyCgImp(BigDecimal bin4QtyCgImp) {
        this.bin4QtyCgImp = bin4QtyCgImp;
    }

    public BigDecimal getBin1QtyCgLoc() {
        return bin1QtyCgLoc;
    }

    public void setBin1QtyCgLoc(BigDecimal bin1QtyCgLoc) {
        this.bin1QtyCgLoc = bin1QtyCgLoc;
    }

    public BigDecimal getBin2QtyCgLoc() {
        return bin2QtyCgLoc;
    }

    public void setBin2QtyCgLoc(BigDecimal bin2QtyCgLoc) {
        this.bin2QtyCgLoc = bin2QtyCgLoc;
    }

    public BigDecimal getBin3QtyCgLoc() {
        return bin3QtyCgLoc;
    }

    public void setBin3QtyCgLoc(BigDecimal bin3QtyCgLoc) {
        this.bin3QtyCgLoc = bin3QtyCgLoc;
    }

    public BigDecimal getBin4QtyCgLoc() {
        return bin4QtyCgLoc;
    }

    public void setBin4QtyCgLoc(BigDecimal bin4QtyCgLoc) {
        this.bin4QtyCgLoc = bin4QtyCgLoc;
    }

    public BigDecimal getBin1QtyFg() {
        return bin1QtyFg;
    }

    public void setBin1QtyFg(BigDecimal bin1QtyFg) {
        this.bin1QtyFg = bin1QtyFg;
    }

    public BigDecimal getBin2QtyFg() {
        return bin2QtyFg;
    }

    public void setBin2QtyFg(BigDecimal bin2QtyFg) {
        this.bin2QtyFg = bin2QtyFg;
    }

    public BigDecimal getBin3QtyFg() {
        return bin3QtyFg;
    }

    public void setBin3QtyFg(BigDecimal bin3QtyFg) {
        this.bin3QtyFg = bin3QtyFg;
    }

    public BigDecimal getBin4QtyFg() {
        return bin4QtyFg;
    }

    public void setBin4QtyFg(BigDecimal bin4QtyFg) {
        this.bin4QtyFg = bin4QtyFg;
    }

    public BigDecimal getWorkCenterIdSw() {
        return workCenterIdSw;
    }

    public void setWorkCenterIdSw(BigDecimal workCenterIdSw) {
        this.workCenterIdSw = workCenterIdSw;
    }

    public BigDecimal getFinishedSw() {
        return finishedSw;
    }

    public void setFinishedSw(BigDecimal finishedSw) {
        this.finishedSw = finishedSw;
    }

    public BigDecimal getRejectedSw() {
        return rejectedSw;
    }

    public void setRejectedSw(BigDecimal rejectedSw) {
        this.rejectedSw = rejectedSw;
    }

    public BigDecimal getUnitsPk() {
        return unitsPk;
    }

    public void setUnitsPk(BigDecimal unitsPk) {
        this.unitsPk = unitsPk;
    }

    public BigDecimal getQtyReceivedFgCMT() {
        return qtyReceivedFgCMT;
    }

    public void setQtyReceivedFgCMT(BigDecimal qtyReceivedFgCMT) {
        this.qtyReceivedFgCMT = qtyReceivedFgCMT;
    }

    public BigDecimal getQtyReceivedCgImp() {
        return qtyReceivedCgImp;
    }

    public void setQtyReceivedCgImp(BigDecimal qtyReceivedCgImp) {
        this.qtyReceivedCgImp = qtyReceivedCgImp;
    }

    public BigDecimal getQtyReceivedCgCMT() {
        return qtyReceivedCgCMT;
    }

    public void setQtyReceivedCgCMT(BigDecimal qtyReceivedCgCMT) {
        this.qtyReceivedCgCMT = qtyReceivedCgCMT;
    }

    public BigDecimal getQtyReceivedFgImp() {
        return qtyReceivedFgImp;
    }

    public void setQtyReceivedFgImp(BigDecimal qtyReceivedFgImp) {
        this.qtyReceivedFgImp = qtyReceivedFgImp;
    }

    public BigDecimal getUnitsAllDesp() {
        return unitsAllDesp;
    }

    public void setUnitsAllDesp(BigDecimal unitsAllDesp) {
        this.unitsAllDesp = unitsAllDesp;
    }

    public BigDecimal getCostAllDesp() {
        return costAllDesp;
    }

    public void setCostAllDesp(BigDecimal costAllDesp) {
        this.costAllDesp = costAllDesp;
    }

    public BigDecimal getUnitsImpDesp() {
        return unitsImpDesp;
    }

    public void setUnitsImpDesp(BigDecimal unitsImpDesp) {
        this.unitsImpDesp = unitsImpDesp;
    }

    public BigDecimal getCostImpDesp() {
        return costImpDesp;
    }

    public void setCostImpDesp(BigDecimal costImpDesp) {
        this.costImpDesp = costImpDesp;
    }

    public BigDecimal getUnitsDI() {
        return unitsDI;
    }

    public void setUnitsDI(BigDecimal unitsDI) {
        this.unitsDI = unitsDI;
    }

    public BigDecimal getCostDI() {
        return costDI;
    }

    public void setCostDI(BigDecimal costDI) {
        this.costDI = costDI;
    }

    public BigDecimal getBin1QtyFabKg() {
        return bin1QtyFabKg;
    }

    public void setBin1QtyFabKg(BigDecimal bin1QtyFabKg) {
        this.bin1QtyFabKg = bin1QtyFabKg;
    }

    public BigDecimal getBin2QtyFabKg() {
        return bin2QtyFabKg;
    }

    public void setBin2QtyFabKg(BigDecimal bin2QtyFabKg) {
        this.bin2QtyFabKg = bin2QtyFabKg;
    }

    public BigDecimal getBin3QtyFabKg() {
        return bin3QtyFabKg;
    }

    public void setBin3QtyFabKg(BigDecimal bin3QtyFabKg) {
        this.bin3QtyFabKg = bin3QtyFabKg;
    }

    public BigDecimal getBin4QtyFabKg() {
        return bin4QtyFabKg;
    }

    public void setBin4QtyFabKg(BigDecimal bin4QtyFabKg) {
        this.bin4QtyFabKg = bin4QtyFabKg;
    }

    public BigDecimal getBin1QtyFabMtr() {
        return bin1QtyFabMtr;
    }

    public void setBin1QtyFabMtr(BigDecimal bin1QtyFabMtr) {
        this.bin1QtyFabMtr = bin1QtyFabMtr;
    }

    public BigDecimal getBin2QtyFabMtr() {
        return bin2QtyFabMtr;
    }

    public void setBin2QtyFabMtr(BigDecimal bin2QtyFabMtr) {
        this.bin2QtyFabMtr = bin2QtyFabMtr;
    }

    public BigDecimal getBin3QtyFabMtr() {
        return bin3QtyFabMtr;
    }

    public void setBin3QtyFabMtr(BigDecimal bin3QtyFabMtr) {
        this.bin3QtyFabMtr = bin3QtyFabMtr;
    }

    public BigDecimal getBin4QtyFabMtr() {
        return bin4QtyFabMtr;
    }

    public void setBin4QtyFabMtr(BigDecimal bin4QtyFabMtr) {
        this.bin4QtyFabMtr = bin4QtyFabMtr;
    }

    public BigDecimal getInCuttingUnits() {
        return inCuttingUnits;
    }

    public void setInCuttingUnits(BigDecimal inCuttingUnits) {
        this.inCuttingUnits = inCuttingUnits;
    }

    public BigDecimal getInSewingUnits() {
        return inSewingUnits;
    }

    public void setInSewingUnits(BigDecimal inSewingUnits) {
        this.inSewingUnits = inSewingUnits;
    }

    public BigDecimal getToPickUnits() {
        return toPickUnits;
    }

    public void setToPickUnits(BigDecimal toPickUnits) {
        this.toPickUnits = toPickUnits;
    }

    public BigDecimal getInPackingUnits() {
        return inPackingUnits;
    }

    public void setInPackingUnits(BigDecimal inPackingUnits) {
        this.inPackingUnits = inPackingUnits;
    }

    public BigDecimal getFinishedGoodsUnits() {
        return finishedGoodsUnits;
    }

    public void setFinishedGoodsUnits(BigDecimal finishedGoodsUnits) {
        this.finishedGoodsUnits = finishedGoodsUnits;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("asAtDate", new AtDate());

        return toBuild;
    }
}
