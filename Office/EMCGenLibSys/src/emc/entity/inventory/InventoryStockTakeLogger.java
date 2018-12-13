/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryStockTakeLogger", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemId", "dimensionId", "companyId"})})
public class InventoryStockTakeLogger extends EMCEntityClass {

    private String journalNumber;
    private String itemId;
    private long dimensionId;
    private int status;

    public long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
