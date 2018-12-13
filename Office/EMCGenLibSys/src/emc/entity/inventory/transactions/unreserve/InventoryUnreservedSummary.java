/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.transactions.unreserve;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This temporary table is used to ensure that unreserved stock can only be
 * used by the container transaction that unreserved the stock while the
 * transaction it still running.  It prevents stock from being 'lost' on
 * Sales Order Reallocation, BOM Rereservation, etc.
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryUnreservedSummary")
public class InventoryUnreservedSummary extends EMCEntityClass {

    private String transactionRef;
    private String itemId;
    private long itemDimId;
    private double availUnreserved;
    private double qcUnreserved;
    private double recUnreserved;

    /** Creates a new instance of InventoryUnreservedSummary. */
    public InventoryUnreservedSummary() {
        
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public long getItemDimId() {
        return itemDimId;
    }

    public void setItemDimId(long itemDimId) {
        this.itemDimId = itemDimId;
    }

    public double getAvailUnreserved() {
        return availUnreserved;
    }

    public void setAvailUnreserved(double availUnreserved) {
        this.availUnreserved = availUnreserved;
    }

    public double getQcUnreserved() {
        return qcUnreserved;
    }

    public void setQcUnreserved(double qcUnreserved) {
        this.qcUnreserved = qcUnreserved;
    }

    public double getRecUnreserved() {
        return recUnreserved;
    }

    public void setRecUnreserved(double recUnreserved) {
        this.recUnreserved = recUnreserved;
    }

}
