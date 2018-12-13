/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.systemwide;

import emc.datatypes.EMCDouble;

/**
 *
 * @author riaan
 */
public class LineNo extends EMCDouble {

    /** Creates a new instance of LineNo */
    public LineNo() {
        this.setMandatory(true);
        this.setEmcLabel("Line No");
        this.setNumberDecimalsDisplay(0);
        this.setColumnWidth(5);
    }
}
