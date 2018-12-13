/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.nationality.foreignkeys;

import emc.datatypes.hr.nationality.Nationality;
import emc.entity.hr.HRNationality;

/**
 * @name        : NationalityFK
 *
 * @description : Foreign key datatype for the nationality field on the HRNationality table.
 *
 * @date        : 20 Oct 2009
 *
 * @author      : riaan
 *
 */
public class NationalityFK extends Nationality {

    /** Creates a new instance of NationalityFK. */
    public NationalityFK() {
        this.setRelatedTable(HRNationality.class.getName());
        this.setRelatedField("nationality");
        this.setMandatory(false);
    }
}
