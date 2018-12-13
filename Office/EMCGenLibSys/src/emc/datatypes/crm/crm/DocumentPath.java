/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.crm.crm;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DocumentPath extends EMCString {

    public DocumentPath() {
        this.setEmcLabel("Path");
        this.setMandatory(true);
        this.setLowerCaseAllowed(true);
    }

}
