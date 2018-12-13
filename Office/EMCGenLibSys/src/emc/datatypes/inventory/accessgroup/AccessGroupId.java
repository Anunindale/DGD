package emc.datatypes.inventory.accessgroup;

import emc.datatypes.EMCString;

public class AccessGroupId extends EMCString {

    /** Creates a new instance of AccessGroupId */
    public AccessGroupId() {
        this.setEmcLabel("Group Id");
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(20);
    }
}
