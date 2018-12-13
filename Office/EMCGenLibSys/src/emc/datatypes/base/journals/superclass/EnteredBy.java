/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.superclass;

import emc.datatypes.base.users.foreignkeys.UserIdFK;

/**
 * @description : Data type for enteredBy on DebtorsJournalMaster.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class EnteredBy extends UserIdFK {

    /** Creates a new instance of EnteredBy */
    public EnteredBy() {
        this.setEmcLabel("Entered By");
    }
}
