/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.securityrules;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class SecurityRules extends EMCString{

    public SecurityRules() {
        this.setEmcLabel("Security Rule");
        this.setMandatory(true);
    }

}
