/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.pricearangements;

import emc.datatypes.EMCDataType;
import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class ToDate extends EMCDate{

    public ToDate() {
        this.setEmcLabel("To");
    	this.setColumnWidth(67);
        this.setMandatory(true);
    }

}
