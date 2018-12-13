/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.license;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class LicenseKey extends EMCString {

    /** Creates a new instance of LicenseKey */
    public LicenseKey() {
        this.setEmcLabel("License Key");
        this.setStringSize(1000);
    }
    
}
