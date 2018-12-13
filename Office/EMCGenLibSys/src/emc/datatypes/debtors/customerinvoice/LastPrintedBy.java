/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFKNotMandatory;

/**
 *
 * @author riaan
 */
public class LastPrintedBy extends EmployeeIdFKNotMandatory {

    /** Creates a new instance of LastPrintedBy */
    public LastPrintedBy() {
        this.setEmcLabel("Last Printed By");
        this.setEditable(false);
    }
}
