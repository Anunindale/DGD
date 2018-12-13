/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.skillratings;

/**
 *
 * @author wikus
 */
import emc.datatypes.EMCString;

public class Rating extends EMCString {

    /** Creates a new instance of SkillRatings */
    public Rating() {
        this.setEmcLabel("Rating");
        this.setColumnWidth(50);
        this.setMandatory(true);
    }
}
