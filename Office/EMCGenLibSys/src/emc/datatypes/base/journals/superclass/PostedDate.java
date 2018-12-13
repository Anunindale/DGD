/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.superclass;

import emc.datatypes.EMCDate;

/**
 * @description : Data type for postedDate on DebtorsJournalMaster.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class PostedDate extends EMCDate {

    /** Creates a new instance of PostedDate */
    public PostedDate() {
        this.setEmcLabel("Posted Date");
        this.setEditable(false);
    }
}
