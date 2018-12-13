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
public class DataField extends EMCString {

    public DataField() {
        this.setEmcLabel("Data Field");
        this.setLowerCaseAllowed(true);
        this.setStringSize(10000);
        this.setColumnWidth(106);
    }
}
