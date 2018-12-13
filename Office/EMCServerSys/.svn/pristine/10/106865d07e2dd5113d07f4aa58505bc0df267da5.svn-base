/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.transactiontotals;

import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DebtorsTransactionTotalsReportBean extends EMCReportBean implements DebtorsTransactionTotalsReportLocal {

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        EMCQuery query = (EMCQuery) parameters.get(1);
        query.addGroupBy("customerId");
        query.addGroupBy("referenceType");
        query.addGroupBy("transactionDate");
        query.addGroupBy("referenceNumber");
        query.addField("customerId");
        query.addField("referenceType");
        query.addField("transactionDate");
        query.addField("referenceNumber");
        query.addFieldAggregateFunction("debit", "SUM");
        query.addFieldAggregateFunction("credit", "SUM");
        return manipulateQueryResult(util.executeGeneralSelectQuery(query, userData), null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        Object[] transaction;
        DebtorsTransactionTotalsReportDS ds;
        Map<String, String> customerMap = new HashMap<String, String>();
        String customerName;
        EMCQuery custQuery;
        List reportdata = new ArrayList();
        for (Object o : queryResult) {
            transaction = (Object[]) o;
            ds = new DebtorsTransactionTotalsReportDS();
            ds.setCustomrtId((String) transaction[0]);
            customerName = customerMap.get((String) transaction[0]);
            if (customerName == null) {
                custQuery = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                custQuery.addAnd("customerId", transaction[0]);
                custQuery.addField("customerName");
                customerName = (String) util.executeSingleResultQuery(custQuery, userData);
                customerMap.put((String) transaction[0], customerName);
            }
            ds.setCustomerName(customerName);
            ds.setTransactionType((String) transaction[1]);
            ds.setTransDate(Functions.date2String((Date) transaction[2]));
            ds.setReference((String) transaction[3]);
            ds.setTransactionType((String) transaction[1]);
            if (((BigDecimal) transaction[4]).compareTo(new BigDecimal(0)) > 0) {
                ds.setAmount((BigDecimal) transaction[4]);
            } else if (((BigDecimal) transaction[5]).compareTo(new BigDecimal(0)) > 0) {
                ds.setAmount(((BigDecimal) transaction[5]).multiply(new BigDecimal(-1)));
            }
            reportdata.add(ds);
        }
        return reportdata;
    }
}
