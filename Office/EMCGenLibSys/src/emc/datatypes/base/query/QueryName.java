/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.query;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class QueryName extends EMCString {

    public QueryName() {
        this.setEmcLabel("Query");
        this.setMandatory(true);
    }
}
