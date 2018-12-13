/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.batchconsolidation.JournalLineRecordId;
import emc.datatypes.inventory.batchconsolidation.JournalNumberFK;
import emc.datatypes.inventory.batchconsolidation.LineNo;
import emc.datatypes.inventory.batchconsolidation.Quantity;
import emc.datatypes.inventory.batchconsolidation.ShortPicked;
import emc.datatypes.inventory.batchconsolidation.foreignkeys.ConsolidationNumberFK;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryBatchConsolidationLines", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"consolidationNumber", "transferJournalLineRecordId", "movementJournalLineRecordId", "movementJournalLineRegisterRecordId", "companyId"})})
public class InventoryBatchConsolidationLines extends EMCEntityClass {

    private String consolidationNumber;
    private BigDecimal lineNo;
    private String journalMasterId;
    private long movementJournalLineRecordId;
    private long movementJournalLineRegisterRecordId;
    private long transferJournalLineRecordId;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal shortPicked = BigDecimal.ZERO;

    public InventoryBatchConsolidationLines() {
    }

    public String getConsolidationNumber() {
        return consolidationNumber;
    }

    public void setConsolidationNumber(String consolidationNumber) {
        this.consolidationNumber = consolidationNumber;
    }

    public String getJournalMasterId() {
        return journalMasterId;
    }

    public void setJournalMasterId(String journalMasterId) {
        this.journalMasterId = journalMasterId;
    }

    public long getMovementJournalLineRecordId() {
        return movementJournalLineRecordId;
    }

    public void setMovementJournalLineRecordId(long movementJournalLineRecordId) {
        this.movementJournalLineRecordId = movementJournalLineRecordId;
    }

    public long getMovementJournalLineRegisterRecordId() {
        return movementJournalLineRegisterRecordId;
    }

    public void setMovementJournalLineRegisterRecordId(long movementJournalLineRegisterRecordId) {
        this.movementJournalLineRegisterRecordId = movementJournalLineRegisterRecordId;
    }

    public long getTransferJournalLineRecordId() {
        return transferJournalLineRecordId;
    }

    public void setTransferJournalLineRecordId(long transferJournalLineRecordId) {
        this.transferJournalLineRecordId = transferJournalLineRecordId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getShortPicked() {
        return shortPicked;
    }

    public void setShortPicked(BigDecimal shortPicked) {
        this.shortPicked = shortPicked;
    }

    public BigDecimal getLineNo() {
        return lineNo;
    }

    public void setLineNo(BigDecimal lineNo) {
        this.lineNo = lineNo;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("consolidationNumber", new ConsolidationNumberFK());
        toBuild.put("lineNo", new LineNo());
        toBuild.put("journalMasterId", new JournalNumberFK());
        toBuild.put("movementJournalLineRecordId", new JournalLineRecordId());
        toBuild.put("transferJournalLineRecordId", new JournalLineRecordId());
        toBuild.put("quantity", new Quantity());
        toBuild.put("shortPicked", new ShortPicked());

        return toBuild;
    }
}
