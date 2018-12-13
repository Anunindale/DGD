/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ReportFooterCaption extends EMCString{

    public ReportFooterCaption() {
        this.setEmcLabel("Report Footer Caption");
        this.setLowerCaseAllowed(true);
        this.setStringSize(10000);
    }

}
