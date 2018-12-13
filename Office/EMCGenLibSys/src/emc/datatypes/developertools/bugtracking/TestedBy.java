/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.bugtracking;

import emc.datatypes.base.users.foreignkeys.UserIdFK;

/**
 *
 * @author claudette
 */
public class TestedBy extends UserIdFK {

    public TestedBy() {
        this.setEmcLabel("Tested By");
    }
}
