/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.creditheld;

import emc.datatypes.sop.salesordermaster.foreignkeys.SalesOrderNoFK;

/**
 *
 * @author riaan
 */
public class Reference extends SalesOrderNoFK {

    /** Creates a new instance of Reference. */
    public Reference() {
        this.setColumnWidth(62);
        this.setEmcLabel("Reference");
    }
}
