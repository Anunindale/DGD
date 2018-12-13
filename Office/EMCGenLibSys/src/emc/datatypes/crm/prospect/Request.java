/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.crm.prospect;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Request extends EMCString{

    public Request() {
        this.setEmcLabel("Request");
        this.setStringSize(10000);
        this.setLowerCaseAllowed(true);
    }

}
