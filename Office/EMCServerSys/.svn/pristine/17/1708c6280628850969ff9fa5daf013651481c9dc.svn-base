/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.edconstatement;

import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.bus.debtors.transactions.DebtorsTransactionsLocal;
import emc.entity.debtors.DebtorsTransactions;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsEdconStatementBean extends EMCBusinessBean implements DebtorsEdconStatementLocal {

    @EJB
    private DebtorsAgingLocal agingBean;
    @EJB
    private DebtorsTransactionsLocal transactionsBean;

    /** Creates a new instance of DebtorsEdconStatementBean. */
    public DebtorsEdconStatementBean() {
    }

    /**
     * Generates and returns an Edcon statement as a String.
     * @param customerId Customer Id.  This should always be the Edcon customer id.  
     *        It's passed in as a method parameter, instead of being hard-coded as it may, for some reason, change in the system.
     * @param from From date.
     * @param to To date.
     * @param edconSupplierRef Edcon supplier reference number.
     * @param userData User data.
     * @return A String that can be written to a file in order to produce an Edcon statement.
     */
    public String getEdconStatement(String customerId, Date from, Date to, String edconSupplierRef, EMCUserData userData) {
        //It looks like the Edcon statement expects two decimals.  Format numbers.
        DecimalFormat format = new DecimalFormat("##.##");

        //Create header line
        StringBuilder edconStatementString = new StringBuilder();

        BigDecimal openingBalance = agingBean.getBalanceAtDate(from, customerId, true, null, userData);
        BigDecimal closingBalance = agingBean.getBalanceAtDate(to, customerId, false, null, userData);

        StringBuilder line = new StringBuilder();

        //Record indicator
        line.append("01|");
        //Append supplier number
        line.append(edconSupplierRef);
        line.append("|");
        //Append date in CCYYMMDD format
        line.append(Functions.date2String(to, "yyyyMMdd"));
        line.append("|");
        //Append opening balance
        line.append(String.valueOf(format.format(openingBalance.doubleValue())));
        line.append("|");
        //Append closing balance
        line.append(String.valueOf(format.format(closingBalance.doubleValue())));
        //Carriage return
        line.append("\n");

        edconStatementString.append(line.toString());

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        query.addAnd("customerId", customerId);
        query.addAndCommaSeperated("transactionDate", Functions.date2SQLString(from) + "~" + Functions.date2SQLString(to), DebtorsTransactions.class.getName(), EMCQueryConditions.EQUALS);
        //Only select open transactions
        query.addAnd("balance", 0, EMCQueryConditions.NOT);
        query.addOrderBy("transactionDate", DebtorsTransactions.class.getName(), EMCQueryOrderByDirections.DESC);

        List<DebtorsTransactions> transactions = (List<DebtorsTransactions>) util.executeGeneralSelectQuery(query, userData);

        //Create lines
        for (DebtorsTransactions transaction : transactions) {
            DebtorsTransactionRefTypes type = DebtorsTransactionRefTypes.fromString(transaction.getReferenceType());

            line = new StringBuilder();

            //Record indicator
            line.append("02|");
            //Supplier (our) transaction reference number
            if (type == DebtorsTransactionRefTypes.INVOICE) {
                //Strip year
                line.append(transaction.getReferenceNumber().substring(4));
            } else {
                line.append(transaction.getReferenceNumber());
            }
            
            line.append("|");
            //Edcon reference number. (Customer order number in EMC)
            line.append(transaction.getCustomerOrderNumber() == null ? "" : transaction.getCustomerOrderNumber());
            line.append("|");
            //Transaction date
            line.append(Functions.date2String(transaction.getTransactionDate(), "yyyyMMdd"));
            line.append("|");
            //Due date
            line.append(transaction.getPaymentDueDate() == null ? "" : Functions.date2String(transaction.getPaymentDueDate(), "yyyyMMdd"));
            line.append("|");
            //Transaction type.  Map our transaction type to an Edcon type
            String edconTransType = null;

            switch (type) {
                case CREDIT_NOTE:
                    edconTransType = "K";
                    break;
                case INVOICE:
                    edconTransType = "I";
                    break;
                case RETURNED_PAYMENT:
                    edconTransType = "R";
                    break;
                case CREDIT_JOURNAL:
                //Fall through
                case DEBIT_JOURNAL:
                    edconTransType = "J";
                    break;
                case PAYMENT:
                    edconTransType = "P";
                    break;

                default:
                    //TODO:  What happens with unmappable type?  For now, ignore line.
                    continue;
            }

            line.append(edconTransType);
            line.append("|");

            //Transaction amount.  Use balance
            line.append(String.valueOf(format.format(transaction.getBalance())));

            //Carriage return
            line.append("\r\n");

            edconStatementString.append(line.toString());
        }

        return edconStatementString.toString();
    }
}
