/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.creditnoteapprovalgroupemployeess;

import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsCreditNoteApprovalGroupEmployeesBean.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCreditNoteApprovalGroupEmployeesLocal extends EMCEntityBeanLocalInterface{

    /**
     * Returns a boolean indicating whether the specified employee is in the specified approval group.
     * @param approvalGroupId Approval group id.
     * @param employeeId Employee id.
     * @param userData User data.
     * @return A boolean indicating whether the specified employee is in the specified approval group.
     */
    public boolean isEmployeeInGroup(java.lang.String approvalGroupId, java.lang.String employeeId, emc.framework.EMCUserData userData);
}
