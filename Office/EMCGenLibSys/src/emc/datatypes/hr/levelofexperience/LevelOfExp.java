package emc.datatypes.hr.levelofexperience;

import emc.datatypes.EMCString;

/** 
 *
 * @author claudette
 */
public class LevelOfExp extends EMCString {

    /** Creates a new instance of LevelOfExp. */
    public LevelOfExp() {
        this.setEmcLabel("Level Of Exp");
        this.setMandatory(true);
    }
}