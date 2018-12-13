package emc.bus.gl.budgetperiod;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
/** 
 *
 * @author claudette
 */
public interface GLBudgetPeriodLocal extends EMCEntityBeanLocalInterface {

    public boolean splitAmounts(long lineRecordID, BigDecimal amount, EMCUserData userData) throws EMCEntityBeanException;
}