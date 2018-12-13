/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.employeeskills;

/**
 *
 * @author wikus
 */
import emc.datatypes.EMCString;
import emc.entity.workflow.WorkFlowSkill;

public class Skill extends EMCString {

    /** Creates a new instance of Skill */
    public Skill() {
        this.setEmcLabel("Skill");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setRelatedTable(WorkFlowSkill.class.getName());
        this.setRelatedField("skill");
    }
}
