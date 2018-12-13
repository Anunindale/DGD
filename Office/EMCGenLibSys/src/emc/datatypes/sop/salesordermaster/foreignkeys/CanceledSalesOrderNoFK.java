/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.salesordermaster.foreignkeys;

import emc.datatypes.sop.salesordermaster.SalesOrderNo;
import emc.entity.sop.SOPSalesOrderCancelMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class CanceledSalesOrderNoFK extends SalesOrderNo {

    public CanceledSalesOrderNoFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(SOPSalesOrderCancelMaster.class.getName());
        this.setRelatedField("salesOrderNo");
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
