/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.framework.emcentityclass;

import emc.datatypes.base.users.foreignkeys.UserIdFK;

/**
 *
 * @author riaan
 */
public class CreatedBy extends UserIdFK {

    /** Creates a new instance of CreatedBy. */
    public CreatedBy() {
        this.setEmcLabel("Created By");
    }
}
