/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.basket;

import emc.entity.debtors.DebtorsBasketMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author stuart
 */
@Local
public interface DebtorsBasketMasterLocal extends EMCEntityBeanLocalInterface {
    public boolean invoiceDebtorsBasketMaster(DebtorsBasketMaster basketMaster, EMCUserData userData) throws EMCEntityBeanException;

    public boolean ReleaseTrec(java.lang.String val, emc.framework.EMCUserData userData);
}
