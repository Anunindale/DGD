/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.movestock;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class MoveStockLinesDRM extends emcDataRelationManagerUpdate {

    private boolean busy = false;

    public MoveStockLinesDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (!busy) {
            busy = true;
            if ((Long) super.getLastFieldValueAt("masterId") == 0) {
                super.setFieldValueAt(super.getLastRowAccessed(), "masterId", super.getHeaderTable().getFieldValueAt(super.getHeaderTable().getLastRowAccessed(), "recordID"));
            }
            if (!(Boolean) super.getHeaderTable().getFieldValueAt(super.getHeaderTable().getLastRowAccessed(), "split")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please set the values on the master record", super.getUserData());
            } else if (columnIndex.equals("quantity")) {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
                double value = 0;
                for (int i = 0; i < super.getRowCount(); i++) {
                    value += (Double) super.getFieldValueAt(i, "quantity");
                }
                ((MoveStockMasterDRM) super.getHeaderTable()).setFieldFromLines(super.getHeaderTable().getLastRowAccessed(), "quantity", value);
            } else {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
            }
            busy = false;
        }
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        double masterQty = super.getHeaderTable().getFieldValueAt(super.getHeaderTable().getLastRowAccessed(), "quantity") == null ? 0 : (Double) super.getHeaderTable().getFieldValueAt(super.getHeaderTable().getLastRowAccessed(), "quantity");
        double value = masterQty - (Double) super.getLastFieldValueAt("quantity");
        ((MoveStockMasterDRM) super.getHeaderTable()).setFieldFromLines(super.getHeaderTable().getLastRowAccessed(), "quantity", value);
        super.deleteRowCache(rowIndex);
    }
}
