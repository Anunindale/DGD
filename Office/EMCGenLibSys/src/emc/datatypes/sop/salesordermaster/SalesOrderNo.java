/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.salesordermaster;

import emc.datatypes.EMCString;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class SalesOrderNo extends EMCString{

    public SalesOrderNo() {
        this.setEmcLabel("Sales Order No");
        this.setMandatory(true);
        this.setNumberSeqAllowed(true);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    	this.setColumnWidth(76);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }

}
