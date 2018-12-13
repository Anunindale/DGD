package emc.datatypes.workflow.jobmaster;


import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class StartedDate extends EMCDate {

    /** Creates a new instance of StartDate */
    public StartedDate() {
        this.setEmcLabel("Start Date");
        this.setEditable(true);
    }
}
