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
public class LogToEmailAddress extends EMCString {

    public LogToEmailAddress() {
        this.setEmcLabel("Log To Mail Address");
        this.setLowerCaseAllowed(true);
        this.setColumnWidth(125);
        this.setStringSize(100);
    }
}
