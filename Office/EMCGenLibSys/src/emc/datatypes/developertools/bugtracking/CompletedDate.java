/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCDate;

/**
 *
 * @author rico
 */
public class CompletedDate extends EMCDate {
    public CompletedDate(){
        this.setEmcLabel("Completed");
        this.setColumnWidth(65);
    }
}
