/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.race.foreignkeys;

import emc.datatypes.hr.nationality.Nationality;
import emc.datatypes.hr.race.Race;
import emc.entity.hr.HRNationality;
import emc.entity.hr.HRRace;

/**
 * @name        : RaceFK
 *
 * @description : Foreign key datatype for the race field on the HRNationality table.
 *
 * @date        : 20 Oct 2009
 *
 * @author      : riaan
 *
 */
public class RaceFK extends Race {

    /** Creates a new instance of RaceFK. */
    public RaceFK() {
        this.setRelatedTable(HRRace.class.getName());
        this.setRelatedField("race");
        this.setMandatory(false);
    }
}
