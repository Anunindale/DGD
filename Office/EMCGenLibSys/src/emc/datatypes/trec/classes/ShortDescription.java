/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.classes;

import emc.datatypes.systemwide.Description;

/**
 *
 * @author wikus
 */
public class ShortDescription extends Description {

    public ShortDescription() {
        this.setEmcLabel("Short Description");
        this.setStringSize(500);
        this.setLowerCaseAllowed(true);
    }
}
