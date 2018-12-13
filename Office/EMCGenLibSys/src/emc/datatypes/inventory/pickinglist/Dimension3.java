/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.pickinglist;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;

/**
 * @Name         : Dimension1
 *
 * @Date         : 04 Aug 2009
 * 
 * @Description  : Datatype for Picking List dimension3 field 
 *
 * @author       : riaan
 */
public class Dimension3 extends Dimension3FKNotMandatory {

    /** Creates a new instance of Dimension3. */
    public Dimension3() {
    	this.setColumnWidth(45);

    }
}
