/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.dbshowlog;

import emc.datatypes.EMCString;

/**
 *
 * @author asd_admin
 */
public class NewValue extends EMCString {

    public NewValue() {
        this.setEmcLabel("New Value");
        this.setLowerCaseAllowed(true);
        this.setColumnWidth(106);
        this.setStringSize(10000);
    }
}