package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;
import emc.entity.workflow.WorkFlowSkill;

/**
 *
 * @author wikus
 */
public class SkillRequired extends EMCString {

    /** Creates a new instance of SkillRequired */
    public SkillRequired() {
        this.setEmcLabel("Skills Required");
        this.setEditable(true);
        this.setStringSize(100);
        this.setRelatedTable(WorkFlowSkill.class.getName());
        this.setRelatedField("skill");
    }
}
