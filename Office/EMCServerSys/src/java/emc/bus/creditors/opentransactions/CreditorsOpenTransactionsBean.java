package emc.bus.creditors.opentransactions;

import emc.entity.creditors.CreditorsOpenTransactions;
import emc.entity.creditors.CreditorsTransactions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/** 
 *
 * @author Owner
 */
@Stateless
public class CreditorsOpenTransactionsBean extends EMCEntityBean implements CreditorsOpenTransactionsLocal {

    /** Creates a new instance of CreditorsTransactionsBean. */
    public CreditorsOpenTransactionsBean() {
    }

    @Override
    public void insertFromTransactions(CreditorsTransactions transaction, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsOpenTransactions openTransaction = new CreditorsOpenTransactions();

        util.copyFields(transaction, openTransaction, userData);

        openTransaction.setRefTransRecId(transaction.getRecordID());

        this.insert(openTransaction, userData);
    }


    @Override
    public void deleteFromTransactions(CreditorsTransactions transaction, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsOpenTransactions.class);
        query.addAnd("refTransRecId", transaction.getRecordID());
        CreditorsOpenTransactions openTransaction = (CreditorsOpenTransactions) util.executeSingleResultQuery(query, userData);

        if (openTransaction != null) {
            this.delete(openTransaction, userData);
        }
    }
}
