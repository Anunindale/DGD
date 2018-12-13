/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.creditnoteapprovalgroups;

import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCreditNoteApprovalGroups.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCreditNoteApprovalGroupsBean extends EMCEntityBean implements DebtorsCreditNoteApprovalGroupsLocal {

    /** Creates a new instance of DebtorsCreditNoteApprovalGroupsBean */
    public DebtorsCreditNoteApprovalGroupsBean() {

    }

    /**
     * Returns the specified Credit Note Approval Group.
     * @param approvalGroupId Id of approval group to retrieve.
     * @param userData User data.
     * @return The specified Credit Note Approval Group, or null, if the group does not exist.
     */
    public DebtorsCreditNoteApprovalGroups getApprovalGroup(String approvalGroupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteApprovalGroups.class);
        query.addAnd("approvalGroupId", approvalGroupId);

        return (DebtorsCreditNoteApprovalGroups)util.executeSingleResultQuery(query, userData);
    }
}
