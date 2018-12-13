/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.users.foreignkeys;

import emc.datatypes.base.users.UserId;
import emc.entity.base.Usertable;

/**
 *
 * @author riaan
 */
public class UserIdFK extends UserId {

    /** Creates a new instance of UserIdFK. */
    public UserIdFK() {
        this.setRelatedTable(Usertable.class.getName());
        this.setRelatedField("userId");
    	this.setColumnWidth(56);
        this.setMandatory(false);
    }
}
