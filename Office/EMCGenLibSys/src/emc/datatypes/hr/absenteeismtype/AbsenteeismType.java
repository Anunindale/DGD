/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.absenteeismtype;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class AbsenteeismType extends EMCString {

    public AbsenteeismType() {
        this.setEmcLabel("Type");
        this.setMandatory(true);
    }

}
