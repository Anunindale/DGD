/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.planning;

import emc.datatypes.EMCDate;

/**
 *
 * @author riaan
 */
public class ReleaseDate extends EMCDate{

    public ReleaseDate() {
        this.setEmcLabel("Release Date");
    	this.setColumnWidth(90);
        this.setMandatory(true);
    }

}
