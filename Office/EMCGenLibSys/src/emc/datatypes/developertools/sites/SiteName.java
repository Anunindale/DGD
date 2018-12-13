/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.sites;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class SiteName extends EMCString{

    public SiteName() {
        this.setEmcLabel("Site Name");
        this.setStringSize(500);
        this.setLowerCaseAllowed(true);
    }

}
