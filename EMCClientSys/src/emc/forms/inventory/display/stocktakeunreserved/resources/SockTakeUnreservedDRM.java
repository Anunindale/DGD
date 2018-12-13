/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.stocktakeunreserved.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class SockTakeUnreservedDRM extends emcDataRelationManagerUpdate {

    public SockTakeUnreservedDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add rows to this table", getUserData());
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to remove rows to this table", getUserData());
    }
}
