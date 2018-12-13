/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.pricegroups.foreignkeys;

import emc.datatypes.pop.discountgroups.foreignkeys.*;

/**
 * @description : Not mandatory data type.
 *
 * @date        : 28 Apr 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class PriceGroupFKNotMandatory extends PriceGroupFK {

    /** Creates a new instance of PriceGroupFK. */
    public PriceGroupFKNotMandatory() {
    	this.setColumnWidth(76);
        this.setMandatory(false);
    }
}
