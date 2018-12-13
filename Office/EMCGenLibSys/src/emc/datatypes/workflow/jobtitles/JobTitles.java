/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.jobtitles;

/**
 *
 * @author wikus
 */
import emc.datatypes.EMCString;

public class JobTitles extends EMCString {

    /** Creates a new instance of JobTitles */
    public JobTitles() {
        this.setEmcLabel("Job Titles");
        this.setColumnWidth(40);
        this.setMandatory(true);
    }
}
