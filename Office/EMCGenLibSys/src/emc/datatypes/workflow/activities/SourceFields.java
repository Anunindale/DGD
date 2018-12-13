/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class SourceFields extends EMCString{

    public SourceFields() {
        this.setStringSize(1024);
        this.setLowerCaseAllowed(true);
    }

}
