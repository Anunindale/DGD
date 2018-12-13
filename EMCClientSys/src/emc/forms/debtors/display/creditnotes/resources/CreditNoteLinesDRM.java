/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.creditnotes.resources;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.util.NumFieldCalculator;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class CreditNoteLinesDRM extends StockDRM {

    private NumFieldCalculator lineNoCalculator;
    private CreditNoteLinesLRM lrm;

    /** Creates a new instance of CustomerInvoiceMasterDRM */
    public CreditNoteLinesDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters params, EMCUserData userData) {
        super(tableDataSource, params, userData);
        this.lineNoCalculator = new NumFieldCalculator(this, "lineNo");
    }

    @Override
    public void updatePersist(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }

        if ((Long) this.getFieldValueAt(rowIndex, "recordID") == 0) {
            this.setFieldValueAt(rowIndex, "lineNo", lineNoCalculator.calcLineNo(rowIndex));
        }

        super.updatePersist(rowIndex);

        //Master status may have been changed
        if (getLastUpdateStatus()) {
            this.getHeaderTable().refreshRecordIgnoreFocus(-1);
        }
    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);

        if (lrm != null) {
            lrm.doRowChanged(this);
        }
    }

    /**
     * Sets lookup relation manager.
     * @param lrm LRM to set.
     */
    public void setLRM(CreditNoteLinesLRM lrm) {
        this.lrm = lrm;
    }
}
