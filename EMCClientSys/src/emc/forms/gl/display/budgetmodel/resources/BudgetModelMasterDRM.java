/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.budgetmodel.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;

/**
 *
 * @author claudette
 */
public class BudgetModelMasterDRM extends emcDataRelationManagerUpdate {

    public BudgetModelMasterDRM(emcGenericDataSourceUpdate update, EMCUserData userData) {
        super(update, userData);
    }
}
