/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditnotes.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class CreditNoteMasterDRM extends emcDataRelationManagerUpdate {

    /**
     * Creates a new instance of CreditNoteMasterDRM
     */
    public CreditNoteMasterDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        if ((!this.checkFocus()) && (getLinesTable() != null)) {
            getLinesTable().deleteRowCache(rowIndex);
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to delete rows from this table. Rather cancel the Credit Note.", getUserData());
        }
    }

}
