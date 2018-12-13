/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactionsettlement.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description : Super class for data relation managers used on the Debtors transaction settlement form.
 *
 * @date        : 10 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class TransactionSettlementDRM extends emcDataRelationManagerUpdate {

    private TransactionSettlementDRM otherDRM;

    //Indicates that a record has been ticked, but not saved yet.
    private boolean ticked;
    //Indicates that a record has been unticked, but not saved yet.
    private boolean unticked;
    //Keeps track of the number of ticked records on this DRM
    private int numTicked;

    //The number of ticked records on this DRM.
    /** Creates a new instance of TransactionSettlementDRM */
    public TransactionSettlementDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    /** Sets the other DRM. */
    public void setOtherDRM(TransactionSettlementDRM otherDRM) {
        this.otherDRM = otherDRM;
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        Logger.getLogger("emc").log(Level.SEVERE, "Rows may not be added.", getUserData());
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        Logger.getLogger("emc").log(Level.SEVERE, "Rows may not be deleted.", getUserData());
    }

    @Override
    public void setHasTheFocus(boolean hasTheFocus) {
        //Change form data manager
        if (hasTheFocus) {
            if (this.getTheForm() != null) {
                this.getTheForm().setDataManager(this);

                if (otherDRM.isRowUpdated()) {
                    //Save last row accessed on other DRM.
                    this.otherDRM.updatePersist(-1);
                }
            }
            //Explicitly remove focus from other DRM.
            otherDRM.setHasTheFocus(false);
        }

        super.setHasTheFocus(hasTheFocus);
    }

    @Override
    public void updatePersist(int rowIndex) {
        super.updatePersist(rowIndex);

        if (getLastUpdateStatus()) {
            if (ticked) {
                numTicked++;
            }
            if (unticked) {
                numTicked--;
            }
            
            //Refresh other DRM, in case records were updated.  After refresh, select
            //previously selected row again.
            int otherSelectedRow = otherDRM.getLastRowAccessed();

            otherDRM.refreshData();
            otherDRM.setSelectedRow(otherSelectedRow);

            this.ticked = false;
            this.unticked = false;
        }
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAt(rowIndex, columnIndex, aValue);

        if ("tick".equals(columnIndex)) {
            if ((Boolean) aValue) {
                if (!this.unticked) {
                    this.ticked = true;
                }
            } else {
                if (!this.ticked) {
                    this.unticked = true;
                }
            }
        }
    }

    /** Returns a boolean indicating whether settled amounts may be changed on this DRM. */
    public boolean canChangeSettledQuantity(int row) {
        return otherDRM.numTicked <= 1 && (Boolean)getFieldValueAt(row, "tick");
    }

    /** Returns a boolean indicating whether the specified row may be ticked/unticked. */
    public boolean canTickRow(int rowIndex) {
        if (otherDRM.numTicked > 1 && this.numTicked > 0) {
            if (Boolean.TRUE.equals(getFieldValueAt(rowIndex, "tick"))) {
                //May untick
                return true;
            } else {
                //May not tick
                return false;
            }
        } else {
            return true;
        }
    }

    /** Resets the variables used to control whether fields are editable. */
    public void resetEditable() {
        this.numTicked = 0;
        this.ticked = false;
        this.unticked = false;
    }

    @Override
    public void enableSearch() {
        //Ensures that searching takes place on the focussed table.
        if (this.getTheForm().getDataManager() == this) {
            super.enableSearch();
        } else {
            //Search on other DRM
            this.otherDRM.enableSearch();
        }
    }
}
