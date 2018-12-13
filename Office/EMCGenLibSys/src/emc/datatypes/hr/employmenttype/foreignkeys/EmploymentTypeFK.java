/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.employmenttype.foreignkeys;

import emc.datatypes.hr.employmenttype.Type;
import emc.entity.hr.HREmploymentType;

/**
 * @name        : EmploymentTypeFK
 *
 * @description : Foreign key datatype for the nationality field on the HREmploymentType table.
 *
 * @date        : 20 Oct 2009
 *
 * @author      : riaan
 *
 */
public class EmploymentTypeFK extends Type {

    /** Creates a new instance of EmploymentTypeFK. */
    public EmploymentTypeFK() {
        this.setRelatedTable(HREmploymentType.class.getName());
        this.setRelatedField("type");
        this.setMandatory(false);
    }
}
