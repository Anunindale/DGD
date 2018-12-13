package emc.datatypes.gl.transactionssummary;

import emc.datatypes.EMCString;

/** 
 *
 * @author claudette
 */
public class GroupGranularity extends EMCString {

    /** Creates a new instance of GroupGranularity. */
    public GroupGranularity() {
        this.setEmcLabel("Group Granularity");
        this.setMandatory(true);
        this.setEditable(false);
    }
}