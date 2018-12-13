/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

/**
 *
 * @author wikus
 */
public class WorkFlowSkillsEnquiry extends WorkFlowEmployeeSkills {

    private String employeeName;

    public WorkFlowSkillsEnquiry() {
        this.setDataSource(true);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
