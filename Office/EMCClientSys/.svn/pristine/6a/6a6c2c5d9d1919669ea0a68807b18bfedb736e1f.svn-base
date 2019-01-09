/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.allocationimport.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class AllocationImportDRM extends emcDataRelationManagerUpdate {

    /** Creates a new instance of AllocationImportDRM. */
    public AllocationImportDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 0:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsAllocationImportFailLog.class);
                query.addAnd("importCode", this.getLastFieldValueAt("importCode"));

                formUserData.setUserData(0, query);
            default:
                break;
        }

        return formUserData;
    }
}
