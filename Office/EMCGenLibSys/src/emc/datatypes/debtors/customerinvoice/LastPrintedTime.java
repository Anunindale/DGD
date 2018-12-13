/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.EMCTime;

/**
 *
 * @author riaan
 */
public class LastPrintedTime extends EMCTime {

    /** Creates a new instance of LastPrintedTime */
    public LastPrintedTime() {
        this.setEmcLabel("Last Printed Time");
        this.setEditable(false);
    }
}
