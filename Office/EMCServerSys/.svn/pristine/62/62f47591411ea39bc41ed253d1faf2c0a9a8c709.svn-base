/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.creditnotereasons;

import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsCreditNoteReasonsBean.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCreditNoteReasonsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the specified reason.
     * @param reasonCode Reason code of reason to return.
     * @param userData User data.
     * @return The specified reason, or null, if none is found.
     */
    public DebtorsCreditNoteReasons getReason(String reasonCode, EMCUserData userData);
}
