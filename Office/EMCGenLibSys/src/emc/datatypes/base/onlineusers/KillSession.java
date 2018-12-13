package emc.datatypes.base.onlineusers;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class KillSession extends EMCBoolean {

    /** Creates a new instance of KillSession */
    public KillSession() {
        this.setEmcLabel("Kill Session");
        this.setEditable(true);
    }
}
