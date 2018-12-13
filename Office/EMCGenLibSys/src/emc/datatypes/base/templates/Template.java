/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.templates;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Template extends EMCString {

    public Template() {
        this.setEmcLabel("Template");
        this.setStringSize(10000);
        this.setLowerCaseAllowed(true);
    }

}
