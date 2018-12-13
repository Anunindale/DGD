/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.datasource.transactionsettlementhistory;

import emc.bus.debtors.transactions.DebtorsTransactionsLocal;
import emc.bus.debtors.transactionsettlement.DebtorsTransactionSettlementHistoryLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.datasource.DebtorsTransactionSettlementHistoryDS;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.entity.sop.SOPCustomers;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Data source bean for DebtorsTransactionSettlementHistoryDS.
 *
 * @date        : 27 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsTransactionSettlementHistoryDSBean extends EMCDataSourceBean implements DebtorsTransactionSettlementHistoryDSLocal {

    @EJB
    private DebtorsTransactionSettlementHistoryLocal historyBean;
    @EJB
    private DebtorsTransactionsLocal debtorsTransBean;
    @EJB
    private SOPCustomersLocal customerBean;

    /** Creates a new instance of DebtorsTransactionSettlementHistoryDSBean */
    public DebtorsTransactionSettlementHistoryDSBean() {
        this.setDataSourceClassName(DebtorsTransactionSettlementHistoryDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return historyBean.delete(super.convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return historyBean.insert(super.convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return historyBean.update(super.convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsTransactionSettlementHistoryDS ds = (DebtorsTransactionSettlementHistoryDS)dataSourceInstance;

        DebtorsTransactions creditTrans = debtorsTransBean.getTransaction(ds.getCreditTransRef(), userData);
        DebtorsTransactions debitTrans = debtorsTransBean.getTransaction(ds.getDebitTransRef(), userData);

        if (creditTrans != null) {
            ds.setCreditTransactionRef(creditTrans.getReferenceNumber());
            ds.setCreditTransactionRefType(creditTrans.getReferenceType());
        }

        if (debitTrans != null) {
            ds.setDebitTransactionRef(debitTrans.getReferenceNumber());
            ds.setDebitTransactionRefType(debitTrans.getReferenceType());
        }

        SOPCustomers customer = customerBean.findCustomer(ds.getCustomerId(), userData);

        if (customer != null) {
            ds.setCustomerName(customer.getCustomerName());
        }

        return ds;
    }

    /**
     * Deallocates the specified settlement from the given DebtorsTransactionSettlementHistoryDS instance.
     * This call is forwarded to the Debtors Transaction Settlement History bean.
     * @param settlementHistory Settlement to deallocate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateSettlement(DebtorsTransactionSettlementHistoryDS settlementHistoryDS, EMCUserData userData) throws EMCEntityBeanException {
        return historyBean.deallocateSettlement((DebtorsTransactionSettlementHistory)convertDataSourceToSuper(settlementHistoryDS, userData), userData);
    }
}
