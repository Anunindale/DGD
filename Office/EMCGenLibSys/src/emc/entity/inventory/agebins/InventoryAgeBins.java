/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.agebins;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.ageing.AgeBin;
import emc.datatypes.inventory.ageing.BinOrder;
import emc.datatypes.inventory.ageing.LastBin;
import emc.datatypes.inventory.ageing.NumberDaysInBin;
import emc.datatypes.inventory.ageing.PrintDescription;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 *
 * @author rico
 */
@Entity
@Table(name = "InventoryAgeBins", uniqueConstraints = {@UniqueConstraint(columnNames = {"ageBin", "companyId"})})
public class InventoryAgeBins extends EMCEntityClass {
    private String ageBin;
    private String ageBinPrintDesc;
    private int numberOfDaysInBin;
    private int binOrder;
    private boolean lastBin;
    public InventoryAgeBins(){

    }

    /**
     * @return the ageBin
     */
    public String getAgeBin() {
        return ageBin;
    }

    /**
     * @param ageBin the ageBin to set
     */
    public void setAgeBin(String ageBin) {
        this.ageBin = ageBin;
    }

    /**
     * @return the ageBinPrintDesc
     */
    public String getAgeBinPrintDesc() {
        return ageBinPrintDesc;
    }

    /**
     * @param ageBinPrintDesc the ageBinPrintDesc to set
     */
    public void setAgeBinPrintDesc(String ageBinPrintDesc) {
        this.ageBinPrintDesc = ageBinPrintDesc;
    }

    /**
     * @return the numberOfDaysInBin
     */
    public int getNumberOfDaysInBin() {
        return numberOfDaysInBin;
    }

    /**
     * @param numberOfDaysInBin the numberOfDaysInBin to set
     */
    public void setNumberOfDaysInBin(int numberOfDaysInBin) {
        this.numberOfDaysInBin = numberOfDaysInBin;
    }

    /**
     * @return the binOrder
     */
    public int getBinOrder() {
        return binOrder;
    }

    /**
     * @param binOrder the binOrder to set
     */
    public void setBinOrder(int binOrder) {
        this.binOrder = binOrder;
    }

    /**
     * @return the lastBin
     */
    public boolean isLastBin() {
        return lastBin;
    }

    /**
     * @param lastBin the lastBin to set
     */
    public void setLastBin(boolean lastBin) {
        this.lastBin = lastBin;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery ret = super.buildQuery();
        ret.addOrderBy("binOrder");
        return ret;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String,EMCDataType> map = super.buildFieldList();
        map.put("ageBin", new AgeBin());
        map.put("ageBinPrintDesc",new PrintDescription());
        map.put("binOrder",new BinOrder());
        map.put("numberOfDaysInBin", new NumberDaysInBin());
        map.put("lastBin",new LastBin());
        return map;
    }




}
