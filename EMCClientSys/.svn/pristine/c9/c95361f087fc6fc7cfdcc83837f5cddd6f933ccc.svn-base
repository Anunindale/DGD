/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.journalapprovalgroups;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.base.journals.BaseJournalApprovalGroupEmployees;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class GLJournalApprovalGroupsFormDRM extends emcDataRelationManagerUpdate {

    /** Creates a new instance of GLJournalApprovalGroupsFormDRM */
    public GLJournalApprovalGroupsFormDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object groupId;
        Object description;
        List x;
        switch (Index) {
            case 0:
                groupId = super.getFieldValueAt(this.getLastRowAccessed(), "journalApprovalGroupId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                if (groupId != null) {
                    x = new ArrayList();
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalApprovalGroupEmployees.class);
                    query.addAnd("journalApprovalGroupId", groupId);
                    x.add(0, query);
                    x.add(1, "");
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
}
