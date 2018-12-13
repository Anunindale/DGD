/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.customerinvoice.resources;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.util.NumFieldCalculator;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class CustomerInvoiceLinesDRM extends StockDRM {

    private NumFieldCalculator lineNoCalculator;
    private CustomerInvoiceLinesLRM lrm;

    /** Creates a new instance of CustomerInvoiceMasterDRM */
    public CustomerInvoiceLinesDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters params, EMCUserData userData) {
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
    public void setLRM(CustomerInvoiceLinesLRM lrm) {
        this.lrm = lrm;
    }
}
