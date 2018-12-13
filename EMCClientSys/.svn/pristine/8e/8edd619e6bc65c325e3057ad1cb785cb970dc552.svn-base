/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.movestock.reserved;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class MoveReservedStockMasterDRM extends StockDRM {

    private boolean busy = false;
    private EMCControlLookupComponent lkpLocation;
    private EMCControlLookupComponent lkpWarehouse;

    public MoveReservedStockMasterDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        Logger.getLogger("emc").log(Level.SEVERE, "You can't add rows to this form.", super.getUserData());
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        Logger.getLogger("emc").log(Level.SEVERE, "You can't remove rows from this form.", super.getUserData());
    }

    @Override
    public void setUserData(EMCUserData userData) {
        if (lkpLocation != null) {
            if (!Functions.checkBlank(this.lkpLocation.getValue())) {
                userData.setUserData(2, this.lkpWarehouse.getValue());
                userData.setUserData(3, this.lkpLocation.getValue());
            } else {
                userData.setUserData(2, null);
                userData.setUserData(3, null);
            }
        }
        super.setUserData(userData);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (!busy) {
            if ((Boolean) super.getFieldValueAt(super.getLastRowAccessed(), "groupLine") == true && (columnIndex.equals("toLocation"))) {
                busy = true;
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
                refreshData();
                busy = false;
            } else if ((Boolean) super.getFieldValueAt(super.getLastRowAccessed(), "split") && !columnIndex.equals("split")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please set the values on the lines.", this.getUserData());
                return;
            } else {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
            }
        }
    }

    public void setFieldFromLines(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAtRegardRelation(rowIndex, columnIndex, aValue, false);
    }

    public void setLookups(EMCControlLookupComponent lkpLocation, EMCControlLookupComponent lkpWarehouse) {
        this.lkpLocation = lkpLocation;
        this.lkpWarehouse = lkpWarehouse;
    }
}
