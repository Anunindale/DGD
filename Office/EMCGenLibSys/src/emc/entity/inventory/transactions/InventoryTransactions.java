/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.transactions;

import emc.datatypes.sop.customers.foreignkeys.CustomerIdFKNotMandatory;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKDeleteRestrict;
import emc.datatypes.inventory.transactions.AverageCost;
import emc.datatypes.inventory.transactions.Cost;
import emc.datatypes.inventory.transactions.Direction;
import emc.datatypes.inventory.transactions.DocumentDate;
import emc.datatypes.inventory.transactions.DocumentNo;
import emc.datatypes.inventory.transactions.ExpectedDate;
import emc.datatypes.inventory.transactions.FifoCost;
import emc.datatypes.inventory.transactions.FinancialDate;
import emc.datatypes.inventory.transactions.ItemDimId;
import emc.datatypes.inventory.transactions.MovementDate;
import emc.datatypes.inventory.transactions.PhysicalDate;
import emc.datatypes.inventory.transactions.Quantity;
import emc.datatypes.inventory.transactions.RefNumber;
import emc.datatypes.inventory.transactions.RefTransId;
import emc.datatypes.inventory.transactions.RefType;
import emc.datatypes.inventory.transactions.Status;
import emc.datatypes.inventory.transactions.TransId;
import emc.datatypes.inventory.transactions.Type;
import emc.datatypes.pop.supplier.foreignkeys.SupplierIdFKNotMandatory;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryTransactions")
public class InventoryTransactions extends EMCEntityClass {

    private String type;
    private String refType;
    private String refNumber;
    private String documentNo;
    @Temporal(TemporalType.DATE)
    private Date documentDate;
    private String transId; //link to lines that caused the transaction
    private String status;
    @Temporal(TemporalType.DATE)
    private Date movementDate;
    private double quantity;
    private double cost; //standard Cost - should be calculated on IN
    private double averageCost; //average Cost - should be calculated on IN
    private double fifoCost; //fifoCost - populated by stock closing
    private long refTransId;
    @Temporal(TemporalType.DATE)
    private Date physicalDate;
    @Temporal(TemporalType.DATE)
    private Date financialDate;
    private String direction;
    private long itemDimId = 0;
    private String itemId;
    private String customerId;
    private String supplierId;
    private boolean closedFlag;
    private double closedQty;
    @Temporal(TemporalType.DATE)
    private Date closedDate;
    @Temporal(TemporalType.DATE)
    private Date expectedDate;

    /** Creates a new instance of InventoryTransactions */
    public InventoryTransactions() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getRefTransId() {
        return refTransId;
    }

    public void setRefTransId(long refTransId) {
        this.refTransId = refTransId;
    }

    public Date getPhysicalDate() {
        return physicalDate;
    }

    public void setPhysicalDate(Date physicalDate) {
        this.physicalDate = physicalDate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Date getFinancialDate() {
        return financialDate;
    }

    public void setFinancialDate(Date financialDate) {
        this.financialDate = financialDate;
    }

    public long getItemDimId() {
        return itemDimId;
    }

    public void setItemDimId(long inventDimId) {
        this.itemDimId = inventDimId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("type", new Type());
        toBuild.put("refType", new RefType());
        toBuild.put("refNumber", new RefNumber());
        toBuild.put("documentNo", new DocumentNo());
        toBuild.put("documentDate", new DocumentDate());
        toBuild.put("transId", new TransId());
        toBuild.put("status", new Status());
        toBuild.put("movementDate", new MovementDate());
        toBuild.put("quantity", new Quantity());
        toBuild.put("cost", new Cost());
        toBuild.put("refTransId", new RefTransId());
        toBuild.put("physicalDate", new PhysicalDate());
        toBuild.put("expectedDate", new ExpectedDate());
        toBuild.put("financialDate", new FinancialDate());
        toBuild.put("direction", new Direction());
        toBuild.put("itemDimId", new ItemDimId());
        toBuild.put("itemId", new ItemIdFKDeleteRestrict());
        toBuild.put("supplierId", new SupplierIdFKNotMandatory());
        toBuild.put("customerId", new CustomerIdFKNotMandatory());
        toBuild.put("averageCost", new AverageCost());
        toBuild.put("fifoCost", new FifoCost());
        return toBuild;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public List<String> buildDefaultLookupFieldList() {
        List<String> ret = new ArrayList<String>();

        ret.add("refNumber");
        ret.add("supplierId");

        return ret;
    }

    public boolean getClosedFlag() {
        return closedFlag;
    }

    public void setClosedFlag(boolean closedFlag) {
        this.closedFlag = closedFlag;
    }

    public double getClosedQty() {
        return closedQty;
    }

    public void setClosedQty(double closedQty) {
        this.closedQty = closedQty;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    /**
     * @return the averageCost
     */
    public double getAverageCost() {
        return averageCost;
    }

    /**
     * @param averageCost the averageCost to set
     */
    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }

    /**
     * @return the fifoCost
     */
    public double getFifoCost() {
        return fifoCost;
    }

    /**
     * @param fifoCost the fifoCost to set
     */
    public void setFifoCost(double fifoCost) {
        this.fifoCost = fifoCost;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
        query.addTableAsField(InventoryTransactions.class.getName());
        query.addTableAsField(InventoryDimensionTable.class.getName());
        return query;
    }
}
