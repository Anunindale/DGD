/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface POPPurchaseOrderApprovalGroupEmployeesLocal extends EMCEntityBeanLocalInterface {

    /** This method finds the approval groups that an employee belongs to. */
    public List<String> findEmployeeApprovalGroups(String employeeId, EMCUserData userData);
    
    /** This method finds the approval groups that an employee belongs to. */
    public List<POPPurchaseOrderApprovalGroups> findEmployeeApprovalGroupRecords(String employeeId, EMCUserData userData);
}
