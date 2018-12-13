/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.allocationimport;

import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsAllocationImportFailLogBean extends EMCEntityBean implements DebtorsAllocationImportFailLogLocal {

    @EJB
    private DebtorsAllocationImportLocal allocationImportBean;
    
    /** Creates a new instance of DebtorsAllocationImportFailLogBean. */
    public DebtorsAllocationImportFailLogBean() {
        
    }

    /**
     * Attempts to import all fail log lines belonging to the specified import.
     * @param importcode Import code.
     * @param userData User data.
     * @return A boolean indicating whether all lines were imported succesfully.
     * @throws EMCEntityBeanException
     */
    public boolean importFailLogLines(String importCode, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsAllocationImport allocationImport = allocationImportBean.getAllocationImport(importCode, userData);

        if (allocationImport != null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsAllocationImportFailLog.class);
            query.addField("line");
            query.addAnd("importCode", importCode);

            List<String> importLines = (List<String>)util.executeGeneralSelectQuery(query, userData);

            return allocationImportBean.importSettlement(allocationImport, importLines, true, userData);
        } else {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALLOCATION_IMPORT_NOT_FOUND, userData, importCode);
            return false;
        }
    }
    
    /**
     * Imports and allocates the specified fail log line.
     * @param failLog Line to import.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException 
     */
    public boolean importFailLogLine(DebtorsAllocationImportFailLog failLog, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsAllocationImport allocationImport = allocationImportBean.getAllocationImport(failLog.getImportCode(), userData);

        if (allocationImport != null) {
            return importFailLogLine(allocationImport, failLog, userData);
        } else {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALLOCATION_IMPORT_NOT_FOUND, userData, failLog.getImportCode());
            return false;
        }
    }

    /**
     * Imports and allocates the specified fail log line.
     * @param allocationImport Allocation import for which line is being imported.
     * @param failLog Line to import.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean importFailLogLine(DebtorsAllocationImport allocationImport, DebtorsAllocationImportFailLog failLog, EMCUserData userData) throws EMCEntityBeanException {
        List<String> data = new ArrayList<String>();

        return allocationImportBean.importSettlement(allocationImport, data, true, userData);
    }

    /**
     * Clears fail log for the specified import.
     *
     * @param importCode Import code.
     * @param userData User data
     * @return A boolean indicating whether the operation was successful.
     */
    public boolean clearFailLog(String importCode, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, DebtorsAllocationImportFailLog.class);
        query.addAnd("importCode", importCode);

        return util.executeUpdate(query, userData);
    }
}
