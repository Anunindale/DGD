/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.bugtracking;

import emc.datatypes.base.users.foreignkeys.UserIdFK;

/**
 *
 * @author rico
 */
public class Responsible extends UserIdFK {

    public Responsible() {
        this.setColumnWidth(57);
        this.setEmcLabel("Assigned");
    }
}
