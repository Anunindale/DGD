/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.planning;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PlanningOrderId extends EMCString {

    public PlanningOrderId() {
        this.setEmcLabel("Order Id");
        this.setMandatory(true);
    	this.setColumnWidth(90);
        this.setNumberSeqAllowed(true);
    }
}
