/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.jobcatagory.foreignkey;

import emc.datatypes.hr.jobcatagory.JobCatagory;
import emc.entity.hr.HRJobCatagory;

/**
 *
 * @author wikus
 */
public class JobCatagoryFK extends JobCatagory {

    public JobCatagoryFK() {
        this.setRelatedTable(HRJobCatagory.class.getName());
        this.setRelatedField("catagory");
        this.setMandatory(false);
    }
}
