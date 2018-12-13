/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.debtors;

import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceLinesDS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class DebtorsInvoiceImportHelper {

    private DebtorsCustomerInvoiceMaster master;
    private List<DebtorsCustomerInvoiceLinesDS> lines = new ArrayList<DebtorsCustomerInvoiceLinesDS>();

    /**
     * Creates a new instance of DebtorsCutomerInvoiceImportHelper
     */
    public DebtorsInvoiceImportHelper() {
    }

    public void setMaster(DebtorsCustomerInvoiceMaster master) {
        this.master = master;
    }

    public void addLine(DebtorsCustomerInvoiceLinesDS line) {
        this.lines.add(line);
    }

    public DebtorsCustomerInvoiceMaster getMaster() {
        return this.master;
    }

    public List<DebtorsCustomerInvoiceLinesDS> getLines() {
        return this.lines;
    }

    public void clearLines() {
        this.lines.clear();
    }
}
