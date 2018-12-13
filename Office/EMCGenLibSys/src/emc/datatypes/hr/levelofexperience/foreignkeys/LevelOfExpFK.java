/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.levelofexperience.foreignkeys;

import emc.datatypes.hr.levelofexperience.LevelOfExp;
import emc.entity.hr.HRLevelOfExperience;

/**
 *
 * @author claudette
 */
public class LevelOfExpFK extends LevelOfExp {

    public LevelOfExpFK() {
        this.setRelatedTable(HRLevelOfExperience.class.getName());
        this.setRelatedField("levelOfExp");
    }
}
