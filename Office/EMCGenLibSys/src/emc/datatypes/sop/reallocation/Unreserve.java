/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.reallocation;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author riaan
 */
public class Unreserve extends EMCBoolean {

    /** Creates a new instance of Unreserve. */
    public Unreserve() {
    	this.setColumnWidth(18);
        this.setEmcLabel("U");
    }
}
