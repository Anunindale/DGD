/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.allocationimport;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsAllocationImportFailLogLocal extends EMCEntityBeanLocalInterface {

    /**
     * Clears fail log for the specified import.
     *
     * @param importCode Import code.
     * @param userData User data
     * @return A boolean indicating whether the operation was successful.
     */
    public boolean clearFailLog(String importCode, EMCUserData userData);

    /**
     * Imports and allocates the specified fail log line.
     * @param failLog Line to import.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean importFailLogLine(emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog failLog, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Imports and allocates the specified fail log line.
     * @param allocationImport Allocation import for which line is being imported.
     * @param failLog Line to import.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean importFailLogLine(emc.entity.debtors.allocationimport.DebtorsAllocationImport allocationImport, emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog failLog, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Attempts to import all fail log lines belonging to the specified import.
     * @param importcode Import code.
     * @param userData User data.
     * @return A boolean indicating whether all lines were imported succesfully.
     * @throws EMCEntityBeanException
     */
    public boolean importFailLogLines(java.lang.String importCode, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
