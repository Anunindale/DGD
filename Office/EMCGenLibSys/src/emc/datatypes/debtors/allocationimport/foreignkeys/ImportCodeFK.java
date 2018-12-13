/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.allocationimport.foreignkeys;

import emc.datatypes.debtors.allocationimport.ImportCode;
import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ImportCodeFK extends ImportCode {

    /** Creates a new instance of ImportCodeFK. */
    public ImportCodeFK() {
        this.setRelatedTable(DebtorsAllocationImport.class.getName());
        this.setRelatedField("importCode");
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
