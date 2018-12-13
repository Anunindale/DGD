/*
 * employeeDataRelation.java
 *
 * Created on 12 December 2007, 08:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.base.display.employees;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.hr.HREmployeeAccessManager;
import emc.entity.base.BaseEmployeeCategoryHistory;
import emc.entity.hr.HRAbsenteeismLog;
import emc.entity.hr.HRDiciplaneryActions;
import emc.entity.hr.HREmployeeDependants;
import emc.entity.hr.HREmployeeEducation;
import emc.entity.hr.HREmployeeTraining;
import emc.entity.hr.HRGrievences;
import emc.entity.hr.HRInternalEmploymentHistory;
import emc.entity.hr.HRLeaveLog;
import emc.entity.hr.HRMedicalLog;
import emc.entity.hr.HRPreviousEmploymentHistory;
import emc.entity.workflow.WorkFlowEmployeeSkills;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class employeeDataRelation extends emcDataRelationManagerUpdate {

    /** Creates a new instance of employeeDataRelation */
    public employeeDataRelation(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
        checkAccessGroup(userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        List x = new ArrayList();
        EMCQuery query;
        String employee = (String) getLastFieldValueAt("employeeNumber");
        switch (Index) {
            case 0:
                query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowEmployeeSkills.class.getName());
                query.addAnd("employeeNumber", employee);
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 3:
                query = new EMCQuery(enumQueryTypes.SELECT, HRLeaveLog.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 5:
                query = new EMCQuery(enumQueryTypes.SELECT, HRDiciplaneryActions.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 6:
                query = new EMCQuery(enumQueryTypes.SELECT, HRGrievences.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 7:
                query = new EMCQuery(enumQueryTypes.SELECT, HREmployeeEducation.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 9:
                query = new EMCQuery(enumQueryTypes.SELECT, HREmployeeTraining.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 10:
                query = new EMCQuery(enumQueryTypes.SELECT, HRInternalEmploymentHistory.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 11:
                query = new EMCQuery(enumQueryTypes.SELECT, HRPreviousEmploymentHistory.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 12:
                query = new EMCQuery(enumQueryTypes.SELECT, HREmployeeDependants.class.getName());
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 13:
                query = new EMCQuery(enumQueryTypes.SELECT, HRMedicalLog.class.getName());
                query.addAnd("employeeId", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 14:
                query = new EMCQuery(enumQueryTypes.SELECT, HRAbsenteeismLog.class);
                query.addAnd("employeeNumber", employee);
                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            case 16:
                query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeCategoryHistory.class);
                query.addAnd("employeeNumber", employee);

                x = new ArrayList();
                x.add(query);
                x.add("");
                x.add(this.getLastFieldValueAt("forenames") + " " + this.getLastFieldValueAt("surname"));
                x.add(employee);
                formUserData.setUserData(x);
                break;
            default:
                break;
        }
        return formUserData;
    }

    private void checkAccessGroup(EMCUserData userData) {
        EMCQuery query = HREmployeeAccessManager.getEmployeeAccessQuery(userData);
        userData.setUserData(0, query);
        this.setUserData(userData);
    }
}
