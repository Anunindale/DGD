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
public class ReportSelection extends EMCString{

    public ReportSelection() {
        this.setEmcLabel("Report Selection");
        this.setMandatory(true);
    }
    
}
