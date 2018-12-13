/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.stocksettlement;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.stocksettlement.LineNumber;
import emc.datatypes.inventory.stocksettlement.foreignkeys.SettlementIdFK;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "InventoryStockSettlementHistory", uniqueConstraints = {@UniqueConstraint(columnNames = {"lineNumber","settlementId", "companyId"})})
public class InventoryStockSettlementHistory extends EMCEntityClass {
    private long lineNumber;
    private String settlementId;
    @Column(name = "inXmlSummary", length = 10000)
    private String inXmlSummary;
    private long inTxRecordId;
    private long outTxUsedInSettlement;
    private boolean wasInClosed;
    private boolean wasOutClosed;
    @Column(name = "outXmlSummary", length = 10000)
    private String outXmlSummary;
    //for convenience
    private double qtyClosed;

    public InventoryStockSettlementHistory(){

    }

    /**
     * @return the lineNumber
     */
    public long getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the settlementId
     */
    public String getSettlementId() {
        return settlementId;
    }

    /**
     * @param settlementId the settlementId to set
     */
    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    /**
     * @return the xmlSummary
     */
    public String getInXmlSummary() {
        return inXmlSummary;
    }

    /**
     * @param xmlSummary the xmlSummary to set
     */
    public void setInXmlSummary(String xmlSummary) {
        this.inXmlSummary = xmlSummary;
    }

    /**
     * @return the inTxRecordId
     */
    public long getInTxRecordId() {
        return inTxRecordId;
    }

    /**
     * @param inTxRecordId the inTxRecordId to set
     */
    public void setInTxRecordId(long inTxRecordId) {
        this.inTxRecordId = inTxRecordId;
    }

    /**
     * @return the outTxUsedInSettlement
     */
    public long getOutTxUsedInSettlement() {
        return outTxUsedInSettlement;
    }

    /**
     * @param outTxUsedInSettlement the outTxUsedInSettlement to set
     */
    public void setOutTxUsedInSettlement(long outTxUsedInSettlement) {
        this.outTxUsedInSettlement = outTxUsedInSettlement;
    }


    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> map = super.buildFieldList();
        map.put("settlementId", new SettlementIdFK());
        map.put("lineNumber", new LineNumber());
        return map;
    }
    @Override
    public EMCQuery buildQuery() {
        EMCQuery qu = super.buildQuery();
        qu.addOrderBy("settlementId");
        qu.addOrderBy("lineNumber");
        return qu;
    }

    /**
     * @return the wasInClosed
     */
    public boolean isWasInClosed() {
        return wasInClosed;
    }

    /**
     * @param wasInClosed the wasInClosed to set
     */
    public void setWasInClosed(boolean wasInClosed) {
        this.wasInClosed = wasInClosed;
    }

    /**
     * @return the wasOutClosed
     */
    public boolean isWasOutClosed() {
        return wasOutClosed;
    }

    /**
     * @param wasOutClosed the wasOutClosed to set
     */
    public void setWasOutClosed(boolean wasOutClosed) {
        this.wasOutClosed = wasOutClosed;
    }

    /**
     * @return the outXmlSummary
     */
    public String getOutXmlSummary() {
        return outXmlSummary;
    }

    /**
     * @param outXmlSummary the outXmlSummary to set
     */
    public void setOutXmlSummary(String outXmlSummary) {
        this.outXmlSummary = outXmlSummary;
    }

    /**
     * @return the qtyClosed
     */
    public double getQtyClosed() {
        return qtyClosed;
    }

    /**
     * @param qtyClosed the qtyClosed to set
     */
    public void setQtyClosed(double qtyClosed) {
        this.qtyClosed = qtyClosed;
    }

}
