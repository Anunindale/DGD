/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.salesarea.foreignkey;

import emc.datatypes.debtors.salesarea.SalesArea;
import emc.entity.debtors.DebtorsSalesArea;

/**
 *
 * @author wikus
 */
public class SalesAreaFK extends SalesArea {

    public SalesAreaFK() {
        this.setRelatedTable(DebtorsSalesArea.class.getName());
        this.setRelatedField("salesArea");
        this.setMandatory(false);
    }
}
