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
public class ReportUser extends EMCString{

    public ReportUser() {
        this.setEmcLabel("User");
        this.setMandatory(true);
    }
    
}
