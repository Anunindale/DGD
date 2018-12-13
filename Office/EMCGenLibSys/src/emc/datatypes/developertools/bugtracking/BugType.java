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
public class BugType extends EMCString {

    public BugType() {
        this.setEmcLabel("Type");
        this.setMandatory(true);
        this.setColumnWidth(69);
    }
}
