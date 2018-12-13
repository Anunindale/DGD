/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.formlookup.controllookup;

import emc.app.components.emctable.stock.*;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;

/**
 *
 * @author riaan
 */
public class StockControlLookupDRM extends StockDRM {

    private EMCControlLookupComponent lookup;

    /** Creates a new instance of StockControlLookupDRM */
    public StockControlLookupDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    public void setLookup(EMCControlLookupComponent lookup) {
        this.lookup = lookup;

        EMCUserData userData = getUserData();
        List<String> userDataList = userData.getUserData();

        if (userDataList.size() < 3) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, this.getRowCache(0).getClass().getName());
            query.addAnd(lookup.getFieldKey(), "");
            query.addAnd("companyId", userData.getCompanyId());
            userData.setUserData(0, query);
            //userData.setUserData(1, query.getCountQuery());
            userData.setUserData(2, "");
            userData.setUserData(3, "");
        }

        this.setUserData(this.getUserData());
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        super.deleteRowCache(rowIndex);
        lookup.setValueOnNew(lookup.getValue());
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        super.insertRowCache(rowIndex, addRowAfter);
        lookup.setValueOnNew(lookup.getValue());
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {

        if (columnIndex.equals(lookup.getFieldKey())) {
            if (!lookup.lastSelected.equals(aValue)) {
                lookup.refresh(false);
                lookup.lastSelected = (String) aValue;
            }
            super.setFieldValueAt(rowIndex, columnIndex, aValue);
            this.setEditFlag(false);
        } else {
            if (super.getFieldValueAt(rowIndex, lookup.getFieldKey()) == null) {
                super.setFieldValueAt(rowIndex, lookup.getFieldKey(), lookup.getValue());
            }
            super.setFieldValueAt(rowIndex, columnIndex, aValue);
        }

    }

    /**
     * The value to be displayed in the lookup should always be in position 3 of the user data list
     * The value to be displayed in the text component shoulds always be in position 2
     * 
     */
    @Override
    public void setUserData(EMCUserData userData) {
        super.setUserData(userData);
        try {
            lookup.valueSetFromFormDRM(userData.getUserData().get(3), userData.getUserData().get(2));
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
            //java.util.logging.Logger.getLogger("emc").log(Level.WARNING, "UserData List does not contain info in positions 2 and 3", userData);   
            }
        }
    }

    public EMCControlLookupComponent getLookup() {
        return lookup;
    }
}
