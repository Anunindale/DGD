/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.citizenstatus.foreignkeys;

import emc.datatypes.hr.citizenstatus.CitizenStatus;
import emc.entity.hr.HRCitizenStatus;

/**
 *
 * @author claudette
 */
public class CitizenStatusFK extends CitizenStatus {

    public CitizenStatusFK() {
        this.setRelatedTable(HRCitizenStatus.class.getName());
        this.setRelatedField("citizenStatus");
    }
}
