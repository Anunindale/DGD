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
public class PrintedBy extends EmployeeIdFKNotMandatory {

    /** Creates a new instance of PrintedBy */
    public PrintedBy() {
        this.setEmcLabel("Printed By");
        this.setEditable(false);
    }
}
