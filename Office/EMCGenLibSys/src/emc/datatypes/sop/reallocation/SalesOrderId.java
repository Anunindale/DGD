/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.reallocation;

import emc.datatypes.sop.salesordermaster.foreignkeys.SalesOrderNoFK;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class SalesOrderId extends SalesOrderNoFK {

    /** Creates a new instance of SalesOrderId. */
    public SalesOrderId() {
        this.setEmcLabel("From SO");
        this.setColumnWidth(63);
        this.setEditable(false);
        this.setDeleteAction(enumDeleteUpdateOptions.IGNORE);
        this.setUpdateAction(enumDeleteUpdateOptions.IGNORE);
    }
}
