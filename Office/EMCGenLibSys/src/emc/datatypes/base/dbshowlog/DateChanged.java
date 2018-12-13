/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.dbshowlog;

import emc.datatypes.EMCDate;

/**
 *
 * @author asd_admin
 */
public class DateChanged extends EMCDate {

    /**
     * Creates a new instance of LogDate.
     */
    public DateChanged() {
        this.setColumnWidth(80);
        this.setEmcLabel("Date");
    }
}