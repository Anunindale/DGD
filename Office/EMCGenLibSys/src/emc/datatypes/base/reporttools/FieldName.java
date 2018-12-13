/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.reporttools;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class FieldName extends EMCString {

    /** Creates a new instance of FieldName */
    public FieldName() {
        this.setEmcLabel("Field");
        this.setMandatory(true);
        this.setColumnWidth(40);
    }
}