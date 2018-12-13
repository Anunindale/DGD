/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.salesordermaster.foreignkeys;

import emc.datatypes.sop.salesordermaster.SalesOrderNo;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class SalesOrderNoFK extends SalesOrderNo {

    public SalesOrderNoFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(SOPSalesOrderMaster.class.getName());
        this.setRelatedField("salesOrderNo");
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
