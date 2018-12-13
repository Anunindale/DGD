/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.creditnoteapprovalgroups;

import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsCreditNoteApprovalGroupsBean.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCreditNoteApprovalGroupsLocal extends EMCEntityBeanLocalInterface {


    /**
     * Returns the specified Credit Note Approval Group.
     * @param approvalGroupId Id of approval group to retrieve.
     * @param userData User data.
     * @return The specified Credit Note Approval Group, or null, if the group does not exist.
     */
    public DebtorsCreditNoteApprovalGroups getApprovalGroup(String approvalGroupId, EMCUserData userData);
}
