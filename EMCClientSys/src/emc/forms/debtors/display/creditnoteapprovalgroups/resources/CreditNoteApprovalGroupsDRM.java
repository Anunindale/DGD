/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.creditnoteapprovalgroups.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroupEmployees;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 * @description : Data Relation Manager for the Credit Note Approval Groups form.
 *
 * @date        : 27 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditNoteApprovalGroupsDRM extends emcDataRelationManagerUpdate {

    /** Creates a new instance of CreditNoteApprovalGroupsDRM */
    public CreditNoteApprovalGroupsDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 0:
                //Approval groups employees
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteApprovalGroupEmployees.class);
                query.addAnd("approvalGroupId", this.getLastFieldValueAt("approvalGroupId"));

                formUserData.setUserData(0, query);
                formUserData.setUserData(1, "");
                formUserData.setUserData(2, this.getLastFieldValueAt("description"));
                formUserData.setUserData(3, this.getLastFieldValueAt("approvalGroupId"));

                break;

            default:
                break;
        }

        return formUserData;
    }
}
