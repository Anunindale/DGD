/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.debtors;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.entity.sop.SOPCustomers;

/**
 *
 * @author wikus
 */
public class CustomerDescriptionDS extends EMCString {

    public CustomerDescriptionDS() {
        setEmcLabel("Name");
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(SOPCustomers.class.getName());
        dsr.setRelatedField("customerName");
        dsr.setFKField("customerId");
        dsr.setPKField("customerId");
        this.setColumnWidth(131);
        this.setDsRelation(dsr);
    }
}
