/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.users;

import emc.datatypes.systemwide.CompanyId;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author rico
 */
public class userCompany extends CompanyId{
    public userCompany(){
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
    }

}
