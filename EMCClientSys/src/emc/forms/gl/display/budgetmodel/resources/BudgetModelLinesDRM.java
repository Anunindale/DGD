/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.budgetmodel.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.gl.GLBudgetPeriod;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author claudette
 */
public class BudgetModelLinesDRM extends emcDataRelationManagerUpdate {

    public BudgetModelLinesDRM(emcGenericDataSourceUpdate sourceUpdate, EMCUserData userData) {
        super(sourceUpdate, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 0:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLBudgetPeriod.class);
                query.addAnd("lineRecordId", getLastFieldValueAt("recordID"));
                formUserData.setUserData(0, query);

                formUserData.setUserData(2, getLastFieldValueAt("account"));
                formUserData.setUserData(3, getLastFieldValueAt("description"));
                formUserData.setUserData(4, getLastFieldValueAt("recordID"));

                break;
        }
        return formUserData;
    }
}
