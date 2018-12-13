/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.timedoperations;

import emc.datatypes.EMCTime;

/**
 *
 * @author wikus
 */
public class LastExecutedEndTime extends EMCTime {

    public LastExecutedEndTime() {
        this.setEmcLabel("Last Completed Time");
    }
}
