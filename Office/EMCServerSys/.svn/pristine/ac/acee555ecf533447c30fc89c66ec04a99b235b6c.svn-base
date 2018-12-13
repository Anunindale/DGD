/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.journals;

import emc.entity.base.journals.BaseJournalApprovalGroupEmployees;
import emc.entity.base.journals.BaseJournalApprovalGroups;
import emc.enums.base.journals.Modules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseJournalApprovalGroupEmployeesBean extends EMCEntityBean implements BaseJournalApprovalGroupEmployeesLocal {

    /** Creates a new instance of BaseJournalApprovalGroupEmployeesBean */
    public BaseJournalApprovalGroupEmployeesBean() {
    }

    /** This method is used to find the approval group(s) in the specified module that the specified employee belongs to. */
    public List<String> findEmployeeApprovalGroup(String employeeId, Modules module, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalApprovalGroupEmployees.class);
        query.addTableAnd(BaseJournalApprovalGroups.class.getName(), "journalApprovalGroupId", BaseJournalApprovalGroupEmployees.class.getName(), "journalApprovalGroupId");
        query.addAnd("employeeId", employeeId);
        query.addAnd("groupModule", module.toString(), BaseJournalApprovalGroups.class.getName());
        query.addField("journalApprovalGroupId");

        List<String> approvalGroup = util.executeGeneralSelectQuery(query, userData);

        if (approvalGroup == null || approvalGroup.isEmpty()) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "No approval group found for employee - " + employeeId, userData);
            }
        }

        return approvalGroup;
    }
}
