/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.employeeskills;

/**
 *
 * @author wikus
 */
import emc.datatypes.EMCDate;

public class EmployeeSkillDate extends EMCDate {

    /** Creates a new instance of Date */
    public EmployeeSkillDate() {
        this.setEmcLabel("Rating Date");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setEditable(true);
    }
}
