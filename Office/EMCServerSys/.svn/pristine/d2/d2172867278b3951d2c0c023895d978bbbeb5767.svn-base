/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */ 
@Stateless
public class POPPurchaseOrderApprovalGroupEmployeesBean extends EMCEntityBean 
        implements POPPurchaseOrderApprovalGroupEmployeesLocal {
   
    /** Creates a new instance of CreditorsPurchaseOrederApprovalGroupEmployeesBean */
    public POPPurchaseOrderApprovalGroupEmployeesBean() {
        
    }
    
    /** This method finds the approval groups that an employee belongs to.  Returns null if no groups are found.  */
    public List<String> findEmployeeApprovalGroups(String employeeId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderApprovalGroupEmployees.class);
        query.addAnd("employeeId", employeeId);
        query.addField("purchaseOrderApprovalGroupId");
        
        List<String> approvalGroups = (List<String>)util.executeGeneralSelectQuery(query, userData);
        
        if (approvalGroups == null || approvalGroups.isEmpty()) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "No approval group found for employee - " + employeeId, userData);
            }
        }
        
        return approvalGroups;
    }
    
    /** This method finds the approval groups that an employee belongs to. */
    public List<POPPurchaseOrderApprovalGroups> findEmployeeApprovalGroupRecords(String employeeId, EMCUserData userData) {
        List<String> approvalGroups = findEmployeeApprovalGroups(employeeId, userData);

        if (approvalGroups == null || approvalGroups.isEmpty()) {
            return new ArrayList<POPPurchaseOrderApprovalGroups>();
        }
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderApprovalGroups.class);
        query.addAndInList("purchaseOrderApprovalGroupId", approvalGroups, true, false);

        return (List<POPPurchaseOrderApprovalGroups>)util.executeGeneralSelectQuery(query, userData);
    }
}
