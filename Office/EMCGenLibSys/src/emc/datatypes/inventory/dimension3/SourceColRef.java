/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension3;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class SourceColRef extends EMCString {

    /** Creates a new instance of SourceColRef. */
    public SourceColRef() {
        this.setEmcLabel("Source Col Ref");
        this.setMandatory(true);
        this.setColumnWidth(75);
    }
}
