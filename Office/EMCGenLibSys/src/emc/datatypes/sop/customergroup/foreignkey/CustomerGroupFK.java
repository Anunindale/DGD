/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.customergroup.foreignkey;

import emc.datatypes.sop.customergroup.CustomerGroup;
import emc.entity.sop.SOPCustomerGroup;

/**
 *
 * @author wikus
 */
public class CustomerGroupFK extends CustomerGroup {

    public CustomerGroupFK() {
        this.setRelatedTable(SOPCustomerGroup.class.getName());
        this.setRelatedField("customerGroup");
    }
}
