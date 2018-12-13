/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.journals.superclass.Description;
import emc.datatypes.inventory.itemreference.ItemReferenceIdNotMandatory;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.datatypes.sop.salesordermaster.Assemble;
import emc.datatypes.sop.salesordermaster.Pick;
import emc.datatypes.sop.salesordermaster.Rekimble;
import emc.datatypes.sop.salesordermaster.UsedQuantity;
import emc.datatypes.sop.salesordermaster.foreignkeys.SalesOrderNoFKNM;
import emc.datatypes.sop.stockenquiry.AvailableQuantity;
import emc.datatypes.sop.stockenquiry.BackOrderQty;
import emc.datatypes.sop.stockenquiry.MasterDimension1;
import emc.datatypes.sop.stockenquiry.MasterDimension2;
import emc.datatypes.sop.stockenquiry.MasterDimension3;
import emc.datatypes.sop.stockenquiry.RekimbleQuantity;
import emc.datatypes.sop.stockenquiry.SerialNo;
import emc.datatypes.sop.stockenquiry.WorkBenchDescription;
import emc.datatypes.systemwide.BigDecimalQuantity;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPSalesOrderStockEnquiryMaster")
public class SOPSalesOrderStockEnquiryMaster extends EMCEntityClass {

    private String userId;
    private String salesOrderId;
    private double lineNo;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension1Decription;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String serialNo; //Kimbling
    private BigDecimal requiredQuantity = BigDecimal.ZERO;
    private BigDecimal availableQuantity = BigDecimal.ZERO;
    private BigDecimal rekimbleQuantity = BigDecimal.ZERO;
    private boolean cantPick = true;
    private boolean cantAssemble = true;
    private boolean cantRekimble = true;
    private String orderNo;
    private String orderType;
    private String transId;
    private String uom;
    //Variables for Work Bench
    private String wbItemId;
    private String wbItemReference;
    private String wbItemDescription;
    private String wbDimension1;
    private String wbDimension2;
    private String wbDimension3;
    private BigDecimal wbRequiredQuantity = BigDecimal.ZERO;
    private BigDecimal wbBackOrderQuantity = BigDecimal.ZERO;
    private String wbComments;
    private BigDecimal wbUsedQuantity = BigDecimal.ZERO;

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public boolean isCantAssemble() {
        return cantAssemble;
    }

    public void setCantAssemble(boolean cantAssemble) {
        this.cantAssemble = cantAssemble;
    }

    public boolean isCantPick() {
        return cantPick;
    }

    public void setCantPick(boolean cantPick) {
        this.cantPick = cantPick;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getDimension1Decription() {
        return dimension1Decription;
    }

    public void setDimension1Decription(String dimension1Decription) {
        this.dimension1Decription = dimension1Decription;
    }

    public BigDecimal getWbBackOrderQuantity() {
        return wbBackOrderQuantity;
    }

    public void setWbBackOrderQuantity(BigDecimal wbBackOrderQuantity) {
        this.wbBackOrderQuantity = wbBackOrderQuantity;
    }

    public String getWbDimension1() {
        return wbDimension1;
    }

    public void setWbDimension1(String wbDimension1) {
        this.wbDimension1 = wbDimension1;
    }

    public String getWbItemId() {
        return wbItemId;
    }

    public void setWbItemId(String wbItemId) {
        this.wbItemId = wbItemId;
    }

    public String getWbItemReference() {
        return wbItemReference;
    }

    public void setWbItemReference(String wbItemReference) {
        this.wbItemReference = wbItemReference;
    }

    public BigDecimal getWbRequiredQuantity() {
        return wbRequiredQuantity;
    }

    public void setWbRequiredQuantity(BigDecimal wbRequiredQuantity) {
        this.wbRequiredQuantity = wbRequiredQuantity;
    }

    public String getWbItemDescription() {
        return wbItemDescription;
    }

    public void setWbItemDescription(String wbItemDescription) {
        this.wbItemDescription = wbItemDescription;
    }

    public String getWbDimension2() {
        return wbDimension2;
    }

    public void setWbDimension2(String wbDimension2) {
        this.wbDimension2 = wbDimension2;
    }

    public String getWbDimension3() {
        return wbDimension3;
    }

    public void setWbDimension3(String wbDimension3) {
        this.wbDimension3 = wbDimension3;
    }

    public String getWbComments() {
        return wbComments;
    }

    public void setWbComments(String wbComments) {
        this.wbComments = wbComments;
    }

    public BigDecimal getWbUsedQuantity() {
        return wbUsedQuantity;
    }

    public void setWbUsedQuantity(BigDecimal wbUsedQuantity) {
        this.wbUsedQuantity = wbUsedQuantity;
    }

    public boolean isCantRekimble() {
        return cantRekimble;
    }

    public void setCantRekimble(boolean cantRekimble) {
        this.cantRekimble = cantRekimble;
    }

    public BigDecimal getRekimbleQuantity() {
        return rekimbleQuantity;
    }

    public void setRekimbleQuantity(BigDecimal rekimbleQuantity) {
        this.rekimbleQuantity = rekimbleQuantity;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("orderNo", new SalesOrderNoFKNM());
        toBuild.put("itemReference", new ItemReferenceIdNotMandatory());
        toBuild.put("warehouse", new WarehouseFKNotMandatory());
        toBuild.put("dimension1", new MasterDimension1());
        toBuild.put("dimension1Decription", new Description());
        toBuild.put("dimension2", new MasterDimension2());
        toBuild.put("dimension3", new MasterDimension3());
        toBuild.put("cantPick", new Pick());
        toBuild.put("cantAssemble", new Assemble());
        toBuild.put("cantRekimble", new Rekimble());

        toBuild.put("wbItemReference", new ItemReferenceIdNotMandatory());
        toBuild.put("wbItemDescription", new WorkBenchDescription());
        toBuild.put("wbDimension1", new MasterDimension1());
        toBuild.put("wbDimension2", new MasterDimension2());
        toBuild.put("wbDimension3", new MasterDimension3());
        toBuild.put("wbRequiredQuantity", new BigDecimalQuantity());
        toBuild.put("wbBackOrderQuantity", new BackOrderQty());
        toBuild.put("wbUsedQuantity", new UsedQuantity());
        toBuild.put("serialNo", new SerialNo());
        toBuild.put("rekimbleQuantity", new RekimbleQuantity());
        toBuild.put("availableQuantity", new AvailableQuantity());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("userId", null);
        query.addAnd("salesOrderId", null);
        return query;
    }
}
