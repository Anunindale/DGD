/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.disiplinarylevel;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class LevelId extends EMCString{

    public LevelId() {
        this.setEmcLabel("Level");
        this.setMandatory(true);
    }

}
