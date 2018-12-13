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
public class ReportId extends EMCString{

    public ReportId() {
        this.setEmcLabel("Report");
        this.setMandatory(true);
    }
    
}
