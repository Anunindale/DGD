/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.journallines;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class LineDate extends EMCDate{

    public LineDate() {
        this.setEmcLabel("Line Date");
    	this.setColumnWidth(74);
        this.setMandatory(true);
    }

}
