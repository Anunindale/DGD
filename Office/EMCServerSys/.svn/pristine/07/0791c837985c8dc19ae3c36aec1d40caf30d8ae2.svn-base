/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.creditnoteapprovalgroupemployeess;

import emc.entity.debtors.DebtorsCreditNoteApprovalGroupEmployees;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCreditNoteApprovalGroupEmployees.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCreditNoteApprovalGroupEmployeesBean extends EMCEntityBean implements DebtorsCreditNoteApprovalGroupEmployeesLocal {

    /** Creates a new instance of DebtorsCreditNoteApprovalGroupEmployeesBean */
    public DebtorsCreditNoteApprovalGroupEmployeesBean() {

    }

    /**
     * Returns a boolean indicating whether the specified employee is in the specified approval group.
     * @param approvalGroupId Approval group id.
     * @param employeeId Employee id.
     * @param userData User data.
     * @return A boolean indicating whether the specified employee is in the specified approval group.
     */
    public boolean isEmployeeInGroup(String approvalGroupId, String employeeId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteApprovalGroupEmployees.class);
        query.addAnd("approvalGroupId", approvalGroupId);
        query.addAnd("employeeId", employeeId);

        return util.exists(query, userData);
    }
}
