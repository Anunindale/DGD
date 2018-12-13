/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFKNotMandatory;

/**
 *
 * @author riaan
 */
public class ApprovedBy extends EmployeeIdFKNotMandatory {

    /** Creates a new instance of ApprovedBy */
    public ApprovedBy() {
        this.setEmcLabel("Approved By");
        this.setEditable(false);
    }
}
