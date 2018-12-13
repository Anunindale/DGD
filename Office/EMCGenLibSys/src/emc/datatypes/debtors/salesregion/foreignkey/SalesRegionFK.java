/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.salesregion.foreignkey;

import emc.datatypes.debtors.salesregion.SalesRegion;
import emc.entity.debtors.DebtorsSalesRegion;

/**
 *
 * @author wikus
 */
public class SalesRegionFK extends SalesRegion {

    public SalesRegionFK() {
        this.setRelatedTable(DebtorsSalesRegion.class.getName());
        this.setRelatedField("salesRegion");
        this.setMandatory(false);
    }
}
