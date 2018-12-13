/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.dbshowlog;

import emc.datatypes.EMCTime;

/**
 *
 * @author asd_admin
 */
public class TimeChanged extends EMCTime {

    /**
     * Creates a new instance of Time.
     */
    public TimeChanged() {
        this.setColumnWidth(69);
        this.setEmcLabel("Time");
    }
}