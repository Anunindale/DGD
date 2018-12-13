/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.app.util.NumFieldCalculator;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.JournalStatus;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class JournalLinesDRM extends StockDRM {

    private NumFieldCalculator numFieldCalculator = new NumFieldCalculator(this, "lineNo");
    private List<EMCLookupRelationManager> lookupRelationManagers = new ArrayList<EMCLookupRelationManager>();

    /** Creates a new instance of JournalLinesDRM. */
    public JournalLinesDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    public void addLookupRelationManager(EMCLookupRelationManager lookupRelationManager) {
        this.lookupRelationManagers.add(lookupRelationManager);
    }

    @Override
    public Object getFieldValueAt(int rowIndex, String columnIndex) {
        Object ret = super.getFieldValueAt(rowIndex, columnIndex);
        if ((Long) this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "recordID") != 0) {

            boolean linesFieldSet = false;

            if (columnIndex.equals("contraAccount") && Functions.checkBlank(ret)) {
                boolean eFlag = this.isRowUpdated();
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "journalContraAccount");
                if (masterCA != null) {
                    this.setFieldValueAt(rowIndex, columnIndex, masterCA);
                    linesFieldSet = true;
                }
                if (!eFlag) this.setEditFlag(false);
            } else if (columnIndex.equals("contraType") && Functions.checkBlank(ret)) {
                boolean eFlag = this.isRowUpdated();
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "journalContraType");
                if (masterCA != null) {
                    this.setFieldValueAt(rowIndex, columnIndex, masterCA);
                    linesFieldSet = true;
                }
                if (!eFlag) this.setEditFlag(false);

            } else if (columnIndex.equals("lineDate") && Functions.checkBlank(ret)) {
                boolean eFlag = this.isRowUpdated();
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "journalNumber");
                if (masterCA != null) {
                    if (rowIndex == 0) {
                        this.setFieldValueAt(rowIndex, columnIndex, Functions.nowDate());
                    } else if (rowIndex > 0) {
                        this.setFieldValueAt(rowIndex, columnIndex, getFieldValueAt(rowIndex - 1, columnIndex));
                    }
                    linesFieldSet = true;
                }
                if (!eFlag) this.setEditFlag(false);
            }
            //if (linesFieldSet) this.getHeaderTable().doPartialRelation(this.getHeaderTable().getLastRowAccessed());
        }
        return ret;
    }

    @Override
    public void updatePersist(int rowIndex) {

        if (rowIndex == -1) {
            rowIndex = this.getLastRowAccessed();
        }

        if ((Double) this.getFieldValueAt(rowIndex, "lineNo") == 0) {
            this.setFieldValueAt(rowIndex, "lineNo", numFieldCalculator.calcLineNo(rowIndex));
        }

        super.updatePersist(rowIndex);

    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);

        for (EMCLookupRelationManager lookupRelationManager : lookupRelationManagers) {
            lookupRelationManager.doRowChanged(this);
        }
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        List x;
        EMCQuery query;
        String journalMasterId = (String) super.getFieldValueAt(this.getLastRowAccessed(), "journalNumber");
        switch (Index) {
            case 1:
                query = new InventoryStocktakeCaptureDS().getQuery();
                query.removeAndNotJoin("journalNumber", InventoryJournalLines.class.getName());
                query.addAnd("journalNumber", journalMasterId, InventoryJournalLines.class.getName());
                query.addTableAnd(InventoryStocktakeRegister.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "masterId");
                x = new ArrayList();
                x.add(0, query);
                x.add(1, "");
                x.add(2, false);
                x.add(3, this);
                formUserData.setUserData(x);
                return formUserData;
            case 2:
                query = new InventoryStocktakeCaptureDS().getQuery();
                query.removeAndNotJoin("journalNumber", InventoryJournalLines.class.getName());
                query.addAnd("journalNumber", journalMasterId, InventoryJournalLines.class.getName());
                x = new ArrayList();
                x.add(0, query);
                x.add(1, "");
                x.add(2, true);
                x.add(3, this);
                formUserData.setUserData(x);
                return formUserData;

        }
        return super.generateRelatedFormUserData(formUserData, Index);
    }

    public void setupStockTakeTable(boolean stockTake) {
        emcJTableUpdate table = this.getMainTableComponent();
        if (table != null) {
            boolean posted = JournalStatus.POSTED.toString().equals((String) this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "journalStatus"));
            table.getColumnModel().getColumn(table.getEmcTableModel().getColumnByFieldName("quantity")).setHeaderValue(stockTake && !posted ? "Counted Qty" : "Qty");
            for (Object s : table.getEmcTableModel().getKeys()) {
                table.setColumnEditable((String) s, !stockTake);
            }
        }
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        if (InventoryJournalTypes.STOCKTAKE.toString().equals(this.getHeaderTable().getLastFieldValueAt("journalType"))) {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add line to a stock take journal here.", this.getUserData());
            return;
        }
        super.insertRowCache(rowIndex, addRowAfter);
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        if (InventoryJournalTypes.STOCKTAKE.toString().equals(this.getHeaderTable().getLastFieldValueAt("journalType"))) {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to remove line from a stock take journal here.", this.getUserData());
            return;
        }
        super.deleteRowCache(rowIndex);
    }

    @Override
    public void setHasTheFocus(boolean hasTheFocus) {
        super.setHasTheFocus(hasTheFocus);

        //If focus gained, refresh components.  This ensures that tabs/buttons are in the correct state if the user transfers focus from the
        //master table to the lines table without saving the master.
        if (hasTheFocus) {
            JournalsDRM masterDRM = (JournalsDRM)this.getHeaderTable();

            if (masterDRM != null) {
                masterDRM.updateComponents();
            }
        }
    }
}
