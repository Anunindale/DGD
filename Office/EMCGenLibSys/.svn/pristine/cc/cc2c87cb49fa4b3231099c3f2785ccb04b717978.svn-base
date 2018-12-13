/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.batchconsolidation.ConsolidationNumber;
import emc.datatypes.inventory.batchconsolidation.ConsolidationStatus;
import emc.datatypes.inventory.batchconsolidation.Description;
import emc.datatypes.inventory.batchconsolidation.PostedDate;
import emc.enums.inventory.batchconsolidation.BatchConsolidationStatus;
import emc.framework.EMCEntityClass;
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
@Table(name = "InventoryBatchConsolidationMaster", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"consolidationNumber", "companyId"})})
public class InventoryBatchConsolidationMaster extends EMCEntityClass {

    private String consolidationNumber;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date postedDate;
    private String postedBy;
    private String consolidationStatus = BatchConsolidationStatus.CREATED.toString();
    private String warehouse;
    private String location;
    private String productGroup;
    private long movementJournalRecordID;

    public InventoryBatchConsolidationMaster() {
    }

    public String getConsolidationNumber() {
        return consolidationNumber;
    }

    public void setConsolidationNumber(String consolidationNumber) {
        this.consolidationNumber = consolidationNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getConsolidationStatus() {
        return consolidationStatus;
    }

    public void setConsolidationStatus(String consolidationStatus) {
        this.consolidationStatus = consolidationStatus;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public long getMovementJournalRecordID() {
        return movementJournalRecordID;
    }

    public void setMovementJournalRecordID(long movementJournalRecordID) {
        this.movementJournalRecordID = movementJournalRecordID;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("consolidationNumber", new ConsolidationNumber());
        toBuild.put("description", new Description());
        toBuild.put("postedDate", new PostedDate());
        toBuild.put("consolidationStatus", new ConsolidationStatus());

        return toBuild;
    }
}
