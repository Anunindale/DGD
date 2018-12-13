/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.allocationimport;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author riaan
 */
public class DebtorsAllocationImportHelper {

    private String line;
    private Map<String, Object> fields = new HashMap<String, Object>();

    /** Creates a new instance of DebtorsAllocationImportHelper. */
    public DebtorsAllocationImportHelper(String line) {
        this.line = line;

        //Populate numeric fields
        this.setCreditAmountAllocated(BigDecimal.ZERO);
        this.setDebitAmountAllocated(BigDecimal.ZERO);
        this.setRebate(BigDecimal.ZERO);
        this.setSettlementDiscount(BigDecimal.ZERO);
    }

    public void mapField(String field, Object value) {
        this.fields.put(field, value);
    }

    public Object getField(String field) {
        return this.fields.get(field);
    }

    public String getLine() {
        return line;
    }

    public BigDecimal getDebitAmountAllocated() {
        return (BigDecimal)this.fields.get("debitAmountSettled");
    }

    public BigDecimal getCreditAmountAllocated() {
        return (BigDecimal)this.fields.get("creditAmountSettled");
    }

    public BigDecimal getRebate() {
        return (BigDecimal)this.fields.get("rebate");
    }

    public BigDecimal getSettlementDiscount() {
        return (BigDecimal)this.fields.get("discTaken");
    }

    public void setDebitAmountAllocated(BigDecimal debitAmountAllocated) {
        this.fields.put("debitAmountSettled", debitAmountAllocated);
    }

    public void setCreditAmountAllocated(BigDecimal creditAmountAllocated) {
        this.fields.put("creditAmountSettled", creditAmountAllocated);
    }

    public void setRebate(BigDecimal rebate) {
        this.fields.put("rebate", rebate);
    }
    
    public void setSettlementDiscount(BigDecimal settlementDiscount) {
        this.fields.put("discTaken", settlementDiscount);
    }

    public String getCustomerOrderNumber() {
        return (String)this.fields.get("customerOrderNumber");
    }

    public String getTransactionReference() {
        return (String)this.fields.get("referenceNumber");
    }
}
