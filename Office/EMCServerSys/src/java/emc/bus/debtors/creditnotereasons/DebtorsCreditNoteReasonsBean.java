/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.creditnotereasons;

import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCreditNoteReasons.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCreditNoteReasonsBean extends EMCEntityBean implements DebtorsCreditNoteReasonsLocal {

    /** Creates a new instance of DebtorsCreditNoteReasonsBean */
    public DebtorsCreditNoteReasonsBean() {

    }

    /**
     * Returns the specified reason.
     * @param reasonCode Reason code of reason to return.
     * @param userData User data.
     * @return The specified reason, or null, if none is found.
     */
    public DebtorsCreditNoteReasons getReason(String reasonCode, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteReasons.class);
        query.addAnd("reasonCode", reasonCode);
        
        return (DebtorsCreditNoteReasons)util.executeSingleResultQuery(query, userData);
    }
}
