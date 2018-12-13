/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCString;

/**
 *
 * @author claudette
 */
public class PossibleAffected extends EMCString {

    public PossibleAffected() {
        this.setEmcLabel("Possible Affected");
        this.setStringSize(1000);
        this.setLowerCaseAllowed(true);
    }
}
