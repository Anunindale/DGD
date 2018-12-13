/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.salesgroup.foreignkey;

import emc.datatypes.debtors.salesgroup.SalesGroup;
import emc.entity.debtors.DebtorsSalesGroup;

/**
 *
 * @author wikus
 */
public class SalesGroupFK extends SalesGroup {

    public SalesGroupFK() {
        this.setRelatedTable(DebtorsSalesGroup.class.getName());
        this.setRelatedField("salesGroup");
        this.setMandatory(false);
    }
}
