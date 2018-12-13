/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.superclass;

import emc.datatypes.base.users.foreignkeys.UserIdFK;

/**
 * @description : Data type for postedBy on DebtorsJournalMaster.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class PostedBy extends UserIdFK {

    /** Creates a new instance of PostedBy */
    public PostedBy() {
        this.setEmcLabel("Posted By");
        this.setEditable(false);
    }
}
