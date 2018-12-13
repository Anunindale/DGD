/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.previousemploymenthistory;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Company extends EMCString{

    public Company() {
        this.setEmcLabel("Company");
        this.setMandatory(true);
    }

}
