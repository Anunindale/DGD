/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.action.settlement;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class SettlementDRM extends emcDataRelationManagerUpdate {

    public SettlementDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (rowIndex == -1) rowIndex = super.getLastRowAccessed();
        if (rowIndex != 0 && columnIndex.equals("endDate")) {
            Date prevDate = (Date) super.getFieldValueAt(rowIndex - 1, "endDate");
            Date currentDate = (Date) aValue;
            if (prevDate != null && currentDate != null && (prevDate.after(currentDate) || prevDate.equals(currentDate))) {
                Logger.getLogger("emc").log(Level.WARNING, "The end date has to be after the previous line end date.", super.getUserData());
                return;
            }
        }
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
    }
}
