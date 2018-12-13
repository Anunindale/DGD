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
public class PrintedDate extends EMCDate {

    /** Creates a new instance of PrintedDate */
    public PrintedDate() {
        this.setEmcLabel("Printed Date");
        this.setEditable(false);
    }
}
