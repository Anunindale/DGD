package emc.datatypes.workflow.joblines;

import emc.datatypes.EMCString;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.enums.datatypes.CallBeanLogicOptions;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class DesignNo extends EMCString {

    /** Creates a new instance of DesignNo */
    public DesignNo() {
        this.setEmcLabel("Design No");
        this.setMandatory(true);
        this.setStringSize(50);
        this.setRelatedTable(WorkFlowJobMaster.class.getName());
        this.setRelatedField("designNo");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setCallBeanOptions(CallBeanLogicOptions.DELETE_ONLY);
    }
}
