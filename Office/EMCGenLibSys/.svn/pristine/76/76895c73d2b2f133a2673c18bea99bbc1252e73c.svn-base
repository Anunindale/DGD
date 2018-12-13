/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.mtoworkbench.Dimension1;
import emc.datatypes.sop.mtoworkbench.Dimension2;
import emc.datatypes.sop.mtoworkbench.Dimension3;
import emc.datatypes.sop.mtoworkbench.ItemDescription;
import emc.datatypes.sop.mtoworkbench.ItemReference;
import emc.datatypes.sop.mtoworkbench.SerialNo;
import emc.datatypes.sop.mtoworkbench.TotalRequired;
import emc.datatypes.sop.mtoworkbench.Warehouse;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPMTOWorkBenchMaster")
public class SOPMTOWorkBenchMaster extends EMCEntityClass {

    private String userId;
    //
    private String salesOrderId;
    private double lineNo;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String serialNo;
    private BigDecimal totalRequired;
    private BigDecimal fromStock;
    private BigDecimal fromProduction;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getTotalRequired() {
        return totalRequired;
    }

    public void setTotalRequired(BigDecimal totalRequired) {
        this.totalRequired = totalRequired;
    }

    public BigDecimal getFromStock() {
        return fromStock;
    }

    public void setFromStock(BigDecimal fromStock) {
        this.fromStock = fromStock;
    }

    public BigDecimal getFromProduction() {
        return fromProduction;
    }

    public void setFromProduction(BigDecimal fromProduction) {
        this.fromProduction = fromProduction;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("serialNo", new SerialNo());
        toBuild.put("totalRequired", new TotalRequired());
        return toBuild;
    }
}
