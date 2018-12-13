/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.journals;

import emc.enums.base.journals.Modules;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseJournalApprovalGroupEmployeesLocal extends EMCEntityBeanLocalInterface {

    /** This method is used to find the approval group(s) in the specified module that the specified employee belongs to. */
    public List<String> findEmployeeApprovalGroup(String employeeId, Modules module, EMCUserData userData);
}
