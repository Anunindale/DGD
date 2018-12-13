/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.companies;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class CompanyName extends EMCString {

    /** Creates a new instance of CompanyName */
    public CompanyName() {
        this.setEmcLabel("Company Name");
        this.setColumnWidth(50);
        this.setStringSize(253);
        this.setEditable(true);
        this.setMandatory(true);
        this.setLowerCaseAllowed(true);
    }
}
