/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.inventory.helper;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author wikus
 */
public class InventoryItemPeggingOutput {

    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String referenceType;
    private String referenceId;
    private Date referenceDate;
    private BigDecimal demandSupply = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;
    private long referenceRecordID;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getDemandSupply() {
        return demandSupply;
    }

    public void setDemandSupply(BigDecimal demandSupply) {
        this.demandSupply = demandSupply;
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

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
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

    public long getReferenceRecordID() {
        return referenceRecordID;
    }

    public void setReferenceRecordID(long referenceRecordID) {
        this.referenceRecordID = referenceRecordID;
    }
}
