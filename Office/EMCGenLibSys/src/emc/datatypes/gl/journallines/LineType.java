/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.journallines;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class LineType extends EMCString {

    /** Creates a new instance of LineType. */
    public LineType() {
        this.setEmcLabel("Type");
        this.setColumnWidth(52);
    }
}
