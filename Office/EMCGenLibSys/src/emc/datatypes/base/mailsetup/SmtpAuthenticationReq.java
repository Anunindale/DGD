/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.mailsetup;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author rico
 */
public class SmtpAuthenticationReq extends EMCBoolean {
    public SmtpAuthenticationReq(){
        this.setEmcLabel("SMTP Authentication Req");
        this.setColumnWidth(38);
    }

}
