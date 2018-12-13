/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.installation;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class InstallationName extends EMCString{

    public InstallationName() {
        this.setEmcLabel("Installation");
        this.setMandatory(true);
    }

}
