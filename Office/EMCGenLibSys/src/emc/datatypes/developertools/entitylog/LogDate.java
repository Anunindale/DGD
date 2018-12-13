/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.entitylog;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class LogDate extends EMCDate{

    public LogDate() {
        this.setEmcLabel("Log Date");
        this.setMandatory(true);
    }

}
