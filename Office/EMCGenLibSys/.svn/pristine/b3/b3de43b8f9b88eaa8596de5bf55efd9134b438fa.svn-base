/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.allocationimport;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.allocationimportfaillog.Balance;
import emc.datatypes.debtors.allocationimportfaillog.Credit;
import emc.datatypes.debtors.allocationimportfaillog.CustomerOrderNumber;
import emc.datatypes.debtors.allocationimportfaillog.Debit;
import emc.datatypes.debtors.allocationimportfaillog.ImportCode;
import emc.datatypes.debtors.allocationimportfaillog.Line;
import emc.datatypes.debtors.allocationimportfaillog.TransReference;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsAllocationImportFailLog")
public class DebtorsAllocationImportFailLog extends EMCEntityClass {

    private String importCode;
    private String transReference;
    private String line;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;
    private String customerOrderNumber;

    /** Creates a new instance of DebtorsAllocationImportFailLog. */
    public DebtorsAllocationImportFailLog() {
        
    }


    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("importCode", new ImportCode());
        toBuild.put("transReference", new TransReference());
        toBuild.put("line", new Line());
        toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());
        toBuild.put("balance", new Balance());
        toBuild.put("customerOrderNumber", new CustomerOrderNumber());

        return toBuild;
    }

    public String getTransReference() {
        return transReference;
    }

    public void setTransReference(String transReference) {
        this.transReference = transReference;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getImportCode() {
        return importCode;
    }

    public void setImportCode(String importCode) {
        this.importCode = importCode;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }
}
