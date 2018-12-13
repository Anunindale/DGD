/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.sites;

import emc.entity.developertools.sites.DevSiteMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class MasterIdFK extends MasterId {

    public MasterIdFK() {
        this.setRelatedTable(DevSiteMaster.class.getName());
        this.setRelatedField("masterId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
