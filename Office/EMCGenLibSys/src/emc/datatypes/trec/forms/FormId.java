/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.forms;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class FormId extends EMCString{

    public FormId() {
        this.setEmcLabel("Form");
        this.setMandatory(true);
        this.setStringSize(1000);
    }

}
