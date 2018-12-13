package emc.datatypes.hr.learningprogram;

import emc.datatypes.EMCString;

/** 
 *
 * @author claudette
 */
public class LearningProgramId extends EMCString {

    /** Creates a new instance of LearningProgramId. */
    public LearningProgramId() {
        this.setEmcLabel("Learning Program Id");
        this.setMandatory(true);
    }
}