/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.query;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class MainEntityClass extends EMCString {

    public MainEntityClass() {
        this.setEmcLabel("Main Entity Class");
        this.setMandatory(true);
    }

}
