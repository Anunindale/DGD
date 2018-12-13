/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.branch;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Branch extends EMCString{

    public Branch() {
        this.setEmcLabel("Branch");
        this.setMandatory(true);
    }

}
