/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.dblog;

import emc.datatypes.EMCTime;

/**
 *
 * @author riaan
 */
public class LogTime extends EMCTime {

    /** Creates a new instance of LogTime. */
    public LogTime() {
    	this.setColumnWidth(69);
        this.setEmcLabel("Time");
    }
}
