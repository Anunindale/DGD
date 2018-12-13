package emc.datatypes.workflow.lines;

import emc.datatypes.EMCString;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 * 
 * @author wikus
 */
public class WorkFlowId extends EMCString {

    /** Creates a new instance of WorkFlowId */
    public WorkFlowId() {
        this.setEmcLabel("Work Flow Id");
        this.setMandatory(true);
        this.setStringSize(50);
        this.setRelatedTable(WorkFlowMaster.class.getName());
        this.setRelatedField("workFlowId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
