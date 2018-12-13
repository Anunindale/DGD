/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.joblevel;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class JobLevel extends EMCString {

    public JobLevel() {
        this.setEmcLabel("Job Level");
        this.setMandatory(true);
    }
}
