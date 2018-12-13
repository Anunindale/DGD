package emc.bus.creditors.opentransactions;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

@Local
/** 
 *
 * @author Owner
 */
public interface CreditorsOpenTransactionsLocal extends EMCEntityBeanLocalInterface {

    public void insertFromTransactions(emc.entity.creditors.CreditorsTransactions transaction, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void deleteFromTransactions(emc.entity.creditors.CreditorsTransactions transaction, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
