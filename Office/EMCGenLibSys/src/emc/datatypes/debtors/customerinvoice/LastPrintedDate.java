/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.EMCDate;

/**
 *
 * @author riaan
 */
public class LastPrintedDate extends EMCDate {

    /** Creates a new instance of PrintedDate */
    public LastPrintedDate() {
        this.setEmcLabel("Printed Date");
        this.setEditable(false);
    }
}
