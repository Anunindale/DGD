/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.employeeaccessgroup;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class AccessGroup extends EMCString{

    public AccessGroup() {
        this.setEmcLabel("Access Group");
        this.setMandatory(true);
    }

}
