/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.repgroups;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RepGroupId extends EMCString{

    public RepGroupId() {
        this.setEmcLabel("Rep Group");
        this.setMandatory(true);
    }

}
