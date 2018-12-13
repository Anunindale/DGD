/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.emctable.stock;

import emc.app.components.emctable.mergetable.*;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class EMCMergeTableStockDRM extends StockDRM {

    /** Creates a new instance of EMCMergeTableDRM. */
    public EMCMergeTableStockDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    
    @Override
    public void refreshRecord(int rowIndex) {
        emcDataRelationManagerUpdate linesDRM = getLinesTable();
        if ((!this.checkFocus()) && (linesDRM != null)) {
            linesDRM.refreshRecord(rowIndex);
        } else {
            //Single line refresh not allowed for merge tables.
            return;
        }
    }

    //Enforce use of merge table
    @Deprecated
    @Override
    public void setMainTableComponent(emcJTableUpdate mainJTable) {
        super.setMainTableComponent(mainJTable);
    }

    /**
     * Sets a merge table as the main table component on this DRM.
     * @param mainJTable Main table
     */
    public void setMainTableComponent(EMCMergeJTable mainJTable) {
        super.setMainTableComponent(mainJTable);
    }
}
