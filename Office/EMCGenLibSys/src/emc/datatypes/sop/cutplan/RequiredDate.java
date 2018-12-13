/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.cutplan;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class RequiredDate extends EMCDate{

    public RequiredDate() {
        this.setEmcLabel("Required Date");
    	this.setColumnWidth(78);
    }

}
