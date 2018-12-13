/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCBoolean;

/**
 * @Name         : BugId
 *
 * @Date         : 14 Aug 2009
 *
 * @Description  : Datatype for the processed field on the DevBugs entity.
 *
 * @author       : riaan
 */
public class Processed extends EMCBoolean {

    /** Creates a new instance of Processed. */
    public Processed() {
        this.setEmcLabel("Processed");
        this.setEditable(false);
    }
}
