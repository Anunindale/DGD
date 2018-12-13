/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditnoteapprovalgroupemployees.datasource.EmployeeName;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroupEmployees;
import java.util.HashMap;

/**
 * @description : Data source for DebtorsCreditNoteApprovalGroupEmployees.
 *
 * @date        : 27 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsCreditNoteApprovalGroupEmployeesDS extends DebtorsCreditNoteApprovalGroupEmployees {

    private String employeeName;

    /** Creates a new instance of DebtorsCreditNoteApprovalGroupEmployeesDS */
    public DebtorsCreditNoteApprovalGroupEmployeesDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("employeeName", new EmployeeName());

        return toBuild;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
