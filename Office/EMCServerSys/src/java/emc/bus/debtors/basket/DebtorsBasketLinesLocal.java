package emc.bus.debtors.basket;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @Author stuart
 */
@Local
public interface DebtorsBasketLinesLocal extends EMCEntityBeanLocalInterface {

    public boolean ReReleaseTrec(java.lang.Long val, emc.framework.EMCUserData userData);
}
