/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customerinvoice.datasource.CustomerName;
import emc.datatypes.debtors.transactionsettlementhistory.CreditTransactionRef;
import emc.datatypes.debtors.transactionsettlementhistory.CreditTransactionRefType;
import emc.datatypes.debtors.transactionsettlementhistory.DebitTransactionRef;
import emc.datatypes.debtors.transactionsettlementhistory.DebitTransactionRefType;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import java.util.HashMap;

/**
 * @description : Data source for Debtors Transaction Settlement History.
 *
 * @date        : 27 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsTransactionSettlementHistoryDS extends DebtorsTransactionSettlementHistory {

    private String debitTransactionRef;
    private String debitTransactionRefType;
    private String creditTransactionRef;
    private String creditTransactionRefType;
    private String customerName;

    /** Creates a new instance of DebtorsTransactionSettlementHistoryDS */
    public DebtorsTransactionSettlementHistoryDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("debitTransactionRef", new DebitTransactionRef());
        toBuild.put("debitTransactionRefType", new DebitTransactionRefType());
        toBuild.put("creditTransactionRef", new CreditTransactionRef());
        toBuild.put("creditTransactionRefType", new CreditTransactionRefType());
        toBuild.put("customerName", new CustomerName());
        
        return toBuild;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDebitTransactionRef() {
        return debitTransactionRef;
    }

    public void setDebitTransactionRef(String debitTransactionRef) {
        this.debitTransactionRef = debitTransactionRef;
    }

    public String getDebitTransactionRefType() {
        return debitTransactionRefType;
    }

    public void setDebitTransactionRefType(String debitTransactionRefType) {
        this.debitTransactionRefType = debitTransactionRefType;
    }

    public String getCreditTransactionRef() {
        return creditTransactionRef;
    }

    public void setCreditTransactionRef(String creditTransactionRef) {
        this.creditTransactionRef = creditTransactionRef;
    }

    public String getCreditTransactionRefType() {
        return creditTransactionRefType;
    }

    public void setCreditTransactionRefType(String creditTransactionRefType) {
        this.creditTransactionRefType = creditTransactionRefType;
    }
}
