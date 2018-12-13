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
public class PostedBy extends EmployeeIdFKNotMandatory {

    /** Creates a new instance of PostedBy */
    public PostedBy() {
        this.setEmcLabel("Posted By");
    }
}
