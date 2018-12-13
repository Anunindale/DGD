/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.pricearangements;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class FromDate extends EMCDate{

    public FromDate() {
        this.setEmcLabel("From");
    	this.setColumnWidth(67);
        this.setMandatory(true);
    }

}
