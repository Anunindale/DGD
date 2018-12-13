/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.pickinglist;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;

/**
 * @Name         : Dimension1
 *
 * @Date         : 04 Aug 2009
 * 
 * @Description  : Datatype for Picking List dimension1 field 
 *
 * @author       : riaan
 */
public class Dimension1 extends Dimension1FKNotMandatory {

    /** Creates a new instance of Dimension1. */
    public Dimension1() {
    	this.setColumnWidth(45);

    }
}
