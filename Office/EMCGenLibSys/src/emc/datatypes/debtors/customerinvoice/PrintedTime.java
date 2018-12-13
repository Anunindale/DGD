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
public class PrintedTime extends EMCTime {

    /** Creates a new instance of PrintedTime */
    public PrintedTime() {
        this.setEmcLabel("Printed Time");
        this.setEditable(false);
    }
}
