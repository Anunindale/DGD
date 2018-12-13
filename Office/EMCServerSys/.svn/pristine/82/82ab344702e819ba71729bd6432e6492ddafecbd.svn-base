/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

import emc.bus.inventory.journals.movement.MovementJournalPostCases;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author rico
 */
public class TransactionHelper {
    //Transaction type

    private TransactionType type;
    private boolean newRecord = false;
    //Inventory
    private InventoryTransactionTypes pickType;
    private boolean stockTakeJournal = false;
    private boolean postJournal = false;
    //POP
    private boolean POPsetQtyToZero = false;
    private boolean POPReturnStockLaterFlag = false;
    private double POPBlancketOrderQty;
    //Production
    private boolean ProductionSetQtyToZero = false;
    private boolean continueWhenNoOrderFound = false;
    private boolean allowPartialReservation = false;
    private EMCEntityClass relatedEntity;
    private MovementJournalPostCases postCase;
    private String mtoWarehouse;
    private BigDecimal qtyToAllocate;
    //SOP
    private String salesOrderNo;
    private String salesORderType;
    private BigDecimal salesBOQuantity;
    private boolean unreserve;
    private BigDecimal quantity;
    private String customer;
    private boolean picklist;
    //Creditors
    private List<CreditorsCreditNoteInvoiceLines> creditorsLinesList;
    //Debtors
    private boolean generateNewSTKNumbers;

    public TransactionHelper(TransactionType type) {
        this.type = type;
    }

    public boolean isPOPsetQtyToZero() {
        return POPsetQtyToZero;
    }

    /**
     * Set this to true if the quantity is to be reduced to zero e.g. stock transaction will be deleted
     * this is to cater for persisted record attached and not known what the real persisted value is once set.
     * Typiclaly delete of line not called from the client  
     * @param POPsetQtyToZero
     */
    public void setPOPsetQtyToZero(boolean POPsetQtyToZero) {
        this.POPsetQtyToZero = POPsetQtyToZero;
    }

    public boolean isProductionSetQtyToZero() {
        return ProductionSetQtyToZero;
    }

    /**
     * Set this to true if the quantity is to be reduced to zero e.g. stock transaction will be deleted
     * this is to cater for persisted record attached and not known what the real persisted value is once set.
     * Typiclaly delete of line not called from the client  
     * @param POPsetQtyToZero
     */
    public void setProductionSetQtyToZero(boolean ProductionSetQtyToZero) {
        this.ProductionSetQtyToZero = ProductionSetQtyToZero;
    }

    public boolean isPOPReturnStockLaterFlag() {
        return POPReturnStockLaterFlag;
    }

    /**
     * Set on Post of PO if stock is to be returned lated
     * @param POPReturnStockLaterFlag
     */
    public void setPOPReturnStockLaterFlag(boolean POPReturnStockLaterFlag) {
        this.POPReturnStockLaterFlag = POPReturnStockLaterFlag;
    }

    /**
     * Get the transaction type
     * @return
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Set to update outstanding blanket order quantity
     * @return
     */
    public double getPOPBlancketOrderQty() {
        return POPBlancketOrderQty;
    }

    /**
     * Set to update outstanding blanket order quantity
     * @return
     */
    public void setPOPBlancketOrderQty(double blancketOrderQty) {
        this.POPBlancketOrderQty = blancketOrderQty;
    }

    public InventoryTransactionTypes getPickType() {
        return pickType;
    }

    /**
     * Use this method to set the type that will be posted on a picking list post
     * @param pickType
     */
    public void setPickType(InventoryTransactionTypes pickType) {
        this.pickType = pickType;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    /**
     * Set this value if the record to insert is a new record - used when looking up 
     * persisted record
     * @param newRecord
     */
    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    public boolean isContinueWhenNoOrderFound() {
        return continueWhenNoOrderFound;
    }

    public void setContinueWhenNoOrderFound(boolean continueWhenNoOrderFound) {
        this.continueWhenNoOrderFound = continueWhenNoOrderFound;
    }

    /**
     * @return the relatedEntity
     */
    public EMCEntityClass getRelatedEntity() {
        return relatedEntity;
    }

    /**
     * @param relatedEntity the relatedEntity to set
     */
    public void setRelatedEntity(EMCEntityClass relatedEntity) {
        this.relatedEntity = relatedEntity;
    }

    /**
     * @return the stockTakeJournal
     */
    public boolean isStockTakeJournal() {
        return stockTakeJournal;
    }

    /**
     * @param stockTakeJournal the stockTakeJournal to set
     */
    public void setStockTakeJournal(boolean stockTakeJournal) {
        this.stockTakeJournal = stockTakeJournal;
    }

    public MovementJournalPostCases getPostCase() {
        return postCase;
    }

    public void setPostCase(MovementJournalPostCases postCase) {
        this.postCase = postCase;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public BigDecimal getSalesBOQuantity() {
        return salesBOQuantity;
    }

    public void setSalesBOQuantity(BigDecimal salesBOQuantity) {
        this.salesBOQuantity = salesBOQuantity;
    }

    public String getSalesORderType() {
        return salesORderType;
    }

    public void setSalesORderType(String salesORderType) {
        this.salesORderType = salesORderType;
    }

    public boolean isUnreserve() {
        return unreserve;
    }

    public void setUnreserve(boolean unreserve) {
        this.unreserve = unreserve;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public boolean isPicklist() {
        return picklist;
    }

    public void setPicklist(boolean picklist) {
        this.picklist = picklist;
    }

    public boolean isPostJournal() {
        return postJournal;
    }

    public void setPostJournal(boolean postJournal) {
        this.postJournal = postJournal;
    }

    public List<CreditorsCreditNoteInvoiceLines> getCreditorsLinesList() {
        return creditorsLinesList;
    }

    public void setCreditorsLinesList(List<CreditorsCreditNoteInvoiceLines> creditorsLinesList) {
        this.creditorsLinesList = creditorsLinesList;
    }

    public boolean isGenerateNewSTKNumbers() {
        return generateNewSTKNumbers;
    }

    public void setGenerateNewSTKNumbers(boolean generateNewSTKNumbers) {
        this.generateNewSTKNumbers = generateNewSTKNumbers;
    }

    public boolean isAllowPartialReservation() {
        return allowPartialReservation;
    }

    public void setAllowPartialReservation(boolean allowPartialReservation) {
        this.allowPartialReservation = allowPartialReservation;
    }

    public String getMtoWarehouse() {
        return mtoWarehouse;
    }

    public void setMtoWarehouse(String mtoWarehouse) {
        this.mtoWarehouse = mtoWarehouse;
    }

    public BigDecimal getQtyToAllocate() {
        return qtyToAllocate;
    }

    public void setQtyToAllocate(BigDecimal qtyToAllocate) {
        this.qtyToAllocate = qtyToAllocate;
    }
}
