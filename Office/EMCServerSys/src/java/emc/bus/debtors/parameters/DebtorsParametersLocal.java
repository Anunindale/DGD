/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.parameters;

import emc.entity.debtors.DebtorsParameters;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsParametersBean.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsParametersLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a DebtorsParameters instance for the active company, or null, if none is found.
     * @param userData User data.
     * @return A DebtorsParameters instance for the active company, or null, if none is found.
     */
    public DebtorsParameters getDebtorsParameters(EMCUserData userData);
}
