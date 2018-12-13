/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditheld;

import emc.datatypes.base.users.foreignkeys.UserIdFK;

/**
 *
 * @author riaan
 */
public class ApprovedBy extends UserIdFK {

    /** Creates a new instance of ApprovedBy. */
    public ApprovedBy() {
        this.setEmcLabel("Approved By");
        this.setEditable(false);
    }
}
