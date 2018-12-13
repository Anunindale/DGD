/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.emctable.stock;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.mergetable.*;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class EMCMergeTableStockDRMViewOnly extends ViewOnlyStockDRM {

    /**
     * Creates a new instance of EMCMergeTableDRMViewOnly.
     */
    public EMCMergeTableStockDRMViewOnly(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
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
     *
     * @param mainJTable Main table
     */
    public void setMainTableComponent(EMCMergeJTable mainJTable) {
        super.setMainTableComponent(mainJTable);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        EMCUserData generated = super.generateRelatedFormUserData(formUserData, Index);

        String itemId;
        String dim1;
        String dim2;
        String dim3;
        EMCQuery query;
        List udList;

        switch (Index) {
           
            case 2: //Planned Ord.
                itemId = (String) getLastFieldValueAt("itemId");
                dim1 = (String) getLastFieldValueAt("dimension1");
                dim2 = (String) getLastFieldValueAt("dimension2");
                dim3 = (String) getLastFieldValueAt("dimension3");

                query = new EMCQuery(enumQueryTypes.SELECT, POPPlannedPurchaseOrders.class);
                query.addAnd("itemId", itemId);
                if (!Functions.checkBlank(dim1)) {
                    query.addAnd("dimension1", dim1);
                }
                if (!Functions.checkBlank(dim2)) {
                    query.addAnd("dimension2", dim2);
                }
                if (!Functions.checkBlank(dim3)) {
                    query.addAnd("dimension3", dim3);
                }

                udList = new ArrayList();
                udList.add(query);

                generated.setUserData(udList);
                break;
        }

        return generated;
    }
}
