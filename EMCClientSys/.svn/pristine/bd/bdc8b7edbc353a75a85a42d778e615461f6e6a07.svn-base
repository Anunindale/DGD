/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.creditors.display.creditnote.resources;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.util.NumFieldCalculator;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class CreditorsCreditNoteLinesDRM extends StockDRM {

    private NumFieldCalculator lineNoCalculator;
    private CreditorsCreditNoteLinesLRM lrm;

    /** Creates a new instance of CustomerInvoiceMasterDRM */
    public CreditorsCreditNoteLinesDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters params, EMCUserData userData) {
        super(tableDataSource, params, userData);
        this.lineNoCalculator = new NumFieldCalculator(this, "lineNo");
    }

    @Override
    public void updatePersist(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }

        if ((Long) this.getFieldValueAt(rowIndex, "recordID") == 0) {
            this.setFieldValueAt(rowIndex, "lineNo", lineNoCalculator.calcBigDecimalLineNo(rowIndex));
        }

        super.updatePersist(rowIndex);
    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);

        if (lrm != null) {
            lrm.doRowChanged(this);
        }
    }

    public void setLRM(CreditorsCreditNoteLinesLRM lrm) {
        this.lrm = lrm;
    }
}
