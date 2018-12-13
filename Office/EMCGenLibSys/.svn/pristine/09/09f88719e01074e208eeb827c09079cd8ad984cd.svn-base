/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.systemwide;

import emc.datatypes.EMCString;
import emc.entity.base.BaseCompanyTable;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class CompanyId extends EMCString {

    /** Creates a new instance of CompanyId */
    public CompanyId() {
        this.setEmcLabel("Company Id");
        this.setMandatory(true);
        this.setRelatedField("companyId");
        this.setRelatedTable(BaseCompanyTable.class.getName());
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}

