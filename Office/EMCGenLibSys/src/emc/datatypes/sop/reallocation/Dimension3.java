/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.reallocation;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;

/**
 *
 * @author riaan
 */
public class Dimension3 extends Dimension3FKNotMandatory {

    /** Creates a new instance of Dimension3. */
    public Dimension3() {
    	this.setColumnWidth(66);
        this.setEditable(false);
    }
}
