/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.leavelog;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class ReturnDate extends EMCDate {

    public ReturnDate() {
        this.setEmcLabel("Return Date");
        this.setMandatory(true);
    }



}
