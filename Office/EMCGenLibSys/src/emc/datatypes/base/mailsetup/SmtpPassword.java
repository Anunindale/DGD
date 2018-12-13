/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.mailsetup;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class SmtpPassword extends EMCString {

    public SmtpPassword() {
        this.setEmcLabel("SMTP Password");
    	this.setColumnWidth(82);
        this.setLowerCaseAllowed(true);
    }
}
