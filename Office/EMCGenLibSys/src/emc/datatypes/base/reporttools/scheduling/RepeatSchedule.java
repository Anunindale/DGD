/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.reporttools.scheduling;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RepeatSchedule extends EMCString{

    public RepeatSchedule() {
        this.setEmcLabel("Repeat");
        this.setMandatory(true);
    }
    
}
