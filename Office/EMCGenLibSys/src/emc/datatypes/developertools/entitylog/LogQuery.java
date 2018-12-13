/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.entitylog;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class LogQuery extends EMCString{

    public LogQuery() {
        this.setEmcLabel("Query");
        this.setStringSize(10000);
        this.setMandatory(true);
        this.setLowerCaseAllowed(true);
    }

}
