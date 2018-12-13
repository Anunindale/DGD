/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.webfilepaths;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RelativePath extends EMCString {

    public RelativePath() {
        this.setEmcLabel("Relative Path");
        this.setMandatory(true);
        this.setLowerCaseAllowed(true);
        this.setStringSize(1000);
    }
}
