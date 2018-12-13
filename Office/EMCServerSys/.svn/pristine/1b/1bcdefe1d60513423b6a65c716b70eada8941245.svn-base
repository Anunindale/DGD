/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.allocationimport;

import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsAllocationImportLocal extends EMCEntityBeanLocalInterface {

    /**
     * Creates a settlement session in a new transaction.  Without this, update method on settlement bean falls over.
     * @param customerId Customer for which a session should be generated.
     * @param userData User data.
     * @return A session id.
     * @throws EMCEntityBeanException
     */
    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public long createSession(java.lang.String customerId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Validates the specified import.  All unmappable lines will be written to the fail log.
     * @param allocationImport Import to validate.
     * @param List<String> importData Data to validate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean validateImport(emc.entity.debtors.allocationimport.DebtorsAllocationImport allocationImport, java.util.List<java.lang.String> importData, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Returns the specified import allocation line.
     * @param importCode Import code of line to fetch.
     * @param userData User data.
     * @return The specified allocation import line, or null, if it does not exists.
     */
    public DebtorsAllocationImport getAllocationImport(String importCode, EMCUserData userData);

    /**
     * Imports data in the specified list to the specified allocation session.
     * @param allocationImport Allocation import record.
     * @param importData Data to import.  The first line should contain column headings.
     * @param userData User data.
     * @param fromFailLog Indicates whether this import is being called from the fail log bean.  If so, records should not be written to the fail log.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean importSettlement(DebtorsAllocationImport allocationImport, List<String> importData, boolean fromFailLog, EMCUserData userData) throws EMCEntityBeanException;
}
