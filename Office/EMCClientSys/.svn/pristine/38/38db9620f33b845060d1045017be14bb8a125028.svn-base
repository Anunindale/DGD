/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.journalaccessgroups.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupEmployees;
import emc.enums.base.journals.Modules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class AccessGroupsDRM extends emcDataRelationManagerUpdate{
    private Modules module;

    public AccessGroupsDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData, Modules module) {
        super(tableDataSource, userData);
        this.module = module;
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object groupId;
        Object description;
        List x;
        switch (Index) {
            case 2:
                groupId = super.getFieldValueAt(this.getLastRowAccessed(), "accessGroupId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "accessGroupDescription");
                if (groupId != null) {
                    x = new ArrayList();
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalAccessGroupEmployees.class);
                    query.addAnd("accessGroupId", groupId);
                    query.addAnd("companyId", formUserData.getCompanyId());
                    x.add(0, query.toString());
                    x.add(1, query.getCountQuery());
                    x.add(2, description == null ? "" : description);
                    x.add(3, groupId.toString());
                    formUserData.setUserData(x);
                }
                break;
            default:
                break;
        }
        return formUserData;
    }
    @Override
    public void updatePersist(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }

        if (Functions.checkBlank(getFieldValueAt(rowIndex, "groupModule"))) {
            //Populate module on save.
            this.setFieldValueAt(rowIndex, "groupModule", module.toString());
        }
        super.updatePersist(rowIndex);
    }
}
