/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.accessgroupemployee;

import emc.datatypes.base.journals.accessgroup.foreignkeys.AccessGroupIdFK;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author rico
 */
public class AccessGroupId extends AccessGroupIdFK{

    public AccessGroupId() {
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }


}
