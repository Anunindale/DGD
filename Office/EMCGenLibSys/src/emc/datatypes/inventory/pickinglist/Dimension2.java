/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.pickinglist;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;

/**
 * @Name         : Dimension1
 *
 * @Date         : 04 Aug 2009
 * 
 * @Description  : Datatype for Picking List dimension2 field 
 *
 * @author       : riaan
 */
public class Dimension2 extends Dimension2FKNotMandatory {

    /** Creates a new instance of Dimension2. */
    public Dimension2() {
    	this.setColumnWidth(15);

    }
}
