/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

import emc.entity.inventory.stocksettlement.InventoryStockSettlement;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.settlement.SettlementStatus;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerInventoryMessageEnum;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.tables.EMCTable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryTransactionsBean extends EMCEntityBean implements InventoryTransactionsLocal {
    //used to disallow insertion of transactions in period that has been stock closed
    //done like this for speed of execution

    private static Map<String, Date> companyStockCloseEndDates = Collections.synchronizedMap(new HashMap<String, Date>());
    @EJB
    private EMCDateHandlerLocal dateBean;

    /** Creates a new instance of InventoryTransactionsBean */
    public InventoryTransactionsBean() {
        if (companyStockCloseEndDates.isEmpty()) {
        }
    }

    @Override
    public long findTransRecordId(String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("transId", transId);
        InventoryTransactions trans = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
        if (trans == null) {
            return 0;
        } else {
            return trans.getRecordID();
        }
    }

    @Override
    public String findTransId(long transRecordId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("recordID", transRecordId);
        InventoryTransactions trans = (InventoryTransactions) util.executeSingleResultQuery(query, userData);
        if (trans == null) {
            return null;
        } else {
            return trans.getTransId();
        }
    }

    /** Returns the transaction with the specified record id. */
    @Override
    public InventoryTransactions findTransaction(long transactionRecordId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("recordID", transactionRecordId);

        return (InventoryTransactions) util.executeSingleResultQuery(query, userData);
    }

    /** This method returns all transactions matching the given parameters.  All parameters are optional.  Those with null values will be excluded from the query generated to select transactions.
     *
     * @param refNumber Ref number on transactions to find.
     * @param refType Ref type of transactions to find.
     * @param type Type of transactions to find.
     * @param status Status of transactions to find.
     * @param direction Direction of transactions to find.
     * @param transId Trans id of transactions to find.
     * @param userData User data.
     * @return A list of transactions matching the specified criteria.
     */
    @Override
    public List<InventoryTransactions> findTransactions(String refNumber, InventoryTransactionsRefType refType, InventoryTransactionTypes type, InventoryTransactionStatus status, InventoryTransactionDirection direction, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);

        if (refType != null) {
            query.addAnd("refType", refType.toString());
        }

        if (type != null) {
            query.addAnd("type", type.toString());
        }

        if (status != null) {
            query.addAnd("status", status.toString());
        }

        if (direction != null) {
            query.addAnd("direction", direction.toString());
        }

        if (refNumber != null) {
            query.addAnd("refNumber", refNumber);
        }

        if (transId != null) {
            query.addAnd("transId", transId);
        }

        return (List<InventoryTransactions>) util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        return super.doInsertValidation(vobject, userData) && checkClosedPeriod((InventoryTransactions) vobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doUpdateValidation(vobject, userData) && checkClosedPeriod((InventoryTransactions) vobject, userData);
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean res = super.doDeleteValidation(vobject, userData);
        InventoryTransactions trans = (InventoryTransactions) vobject;
        if (trans.getClosedFlag()) {
            res = false;
            this.logMessage(Level.SEVERE, ServerInventoryMessageEnum.TXDELCLOSEFAIL, userData);
        }
        return res;
    }

    private boolean checkClosedPeriod(InventoryTransactions tx, EMCUserData userData) {
        if (isBlank(tx.getPhysicalDate())) {
            return true;
        }
        if (companyStockCloseEndDates.get(userData.getCompanyId()) == null) {
            updateStockCloseDate(userData);
        }
        if (dateBean.compareDatesIgnoreTime(tx.getPhysicalDate(), companyStockCloseEndDates.get(userData.getCompanyId()), userData) <= 0) {
            if (userData.getUserData(7) != null && userData.getUserData(7).equals("STOCKCLOSE_IN_PROGRESS")) {
                //do nothing stock close in progress allow this update
            } else {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.TXPERSISTFAILSC, userData);
                return false;
            }
        }
        return true;
    }

    private void updateStockCloseDate(EMCUserData userData) {
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlement.class);
        qu.addAnd("runStatus", SettlementStatus.COMPLETED.toString());
        qu.addFieldAggregateFunction("endDate", "MAX");
        List result = util.executeGeneralSelectQuery(qu, userData);
        if (result.isEmpty() || result.get(0) == null) {
            companyStockCloseEndDates.put(userData.getCompanyId(), dateBean.buildDate(1900, 0, 1, 0, 0, 0));
        } else {
            try {
                companyStockCloseEndDates.put(userData.getCompanyId(), Functions.string2Date(result.get(0).toString(), "yyyy-MM-dd"));
            } catch (Exception e) {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.TXSCPERIOD, userData);
            }
        }

    }

    @Override
    public void setStockCloseDate(Date toSet, EMCUserData userData) {
        companyStockCloseEndDates.put(userData.getCompanyId(), toSet);
    }

    /**
     * Returns a list of transactions matching the specified criteria.  Null parameters may be passed to this method - they will be ignored.  (0 for dimension id)
     * @param refNumber Reference number.
     * @param itemId Item id.
     * @param dimensionId Dimension id.
     * @param transId Transaction id.
     * @param refType Reference type.
     * @param type Transaction type.
     * @param direction Transaction direction.
     * @param status Transaction status.
     * @param userData User data.
     * @return A list of transactions matching the specified criteria.
     */
    @Override
    public List<InventoryTransactions> findTransactions(String refNumber, String itemId, long dimensionId, String transId, InventoryTransactionsRefType refType, InventoryTransactionTypes type, InventoryTransactionDirection direction, InventoryTransactionStatus status, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);

        if (itemId != null) {
            query.addAnd("itemId", itemId);
        }

        if (dimensionId != 0) {
            query.addAnd("itemDimId", dimensionId);
        }

        if (refType != null) {
            query.addAnd("refType", refType.toString());
        }

        if (type != null) {
            query.addAnd("type", type.toString());
        }

        if (status != null) {
            query.addAnd("status", status.toString());
        }

        if (direction != null) {
            query.addAnd("direction", direction.toString());
        }

        if (refNumber != null) {
            query.addAnd("refNumber", refNumber);
        }

        if (transId != null) {
            query.addAnd("transId", transId);
        }

        return (List<InventoryTransactions>) util.executeGeneralSelectQuery(query, userData);
    }

    /**
     * Returns the last stock close date for the company specified in userData.
     * @param userData User data.
     * @return The last stock close date for the company specified in userData.
     *          If no stock close has been done, this method will return null.
     */
    @Override
    public Date getLastStockCloseDate(EMCUserData userData) {
        if (companyStockCloseEndDates.isEmpty()) {
            updateStockCloseDate(userData);
        }
        return companyStockCloseEndDates.get(userData.getCompanyId());
    }
}
