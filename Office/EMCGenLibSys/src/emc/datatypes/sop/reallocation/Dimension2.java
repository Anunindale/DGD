/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.reallocation;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;

/**
 *
 * @author riaan
 */
public class Dimension2 extends Dimension2FKNotMandatory {

    /** Creates a new instance of Dimension2. */
    public Dimension2() {
    	this.setColumnWidth(33);
        this.setEditable(false);
    }
}
