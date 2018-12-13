/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.approvalgroupssetup.ds.EmployeeName;
import emc.entity.creditors.CreditorsApprovalGroupSetup;
import java.util.HashMap;

/**
 *
 * @author claudette
 */
public class CreditorsApprovalGroupSetupDS extends CreditorsApprovalGroupSetup {

    private String employeeName;

    public CreditorsApprovalGroupSetupDS() {
        this.setDataSource(true);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeName", new EmployeeName());
        return toBuild;
    }
}
