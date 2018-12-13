/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJTextField;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.app.util.NumFieldCalculator;
import emc.datatypes.EMCDouble;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.forms.pop.display.purchaseorders.resources.CancelLineButton;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class PurchaseOrderLinesDRM extends StockDRM {

    private NumFieldCalculator numFieldCalculator = new NumFieldCalculator(this, "lineNo");
    private EMCLookupRelationManager lookupRelationManager;
    private emcJTextField qtyOutstanding;
    //Formatting for calculated qty outstanding field
    EMCDouble dt = new EMCDouble();
    DecimalFormat format = new DecimalFormat(dt.getFormatToDisplay());
    private CancelLineButton cancelButton;

    //Used to prompt user as to whether a blanket order should be updated when saving a PO line
    private boolean promptBOUpdate = false;

    /** Creates a new instance of PurchaseOrderLinesDRM. */
    public PurchaseOrderLinesDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    public void setLookupRelationManager(EMCLookupRelationManager lookupRelationManager) {
        this.lookupRelationManager = lookupRelationManager;
    }

    @Override
    public void handleFocusLostFormComponents() {
        super.handleFocusLostFormComponents();
        this.getHeaderTable().refreshRecordNoDoRelation(-1);

    }

    @Override
    public void updatePersist(int rowIndex) {

        if (rowIndex == -1) {
            rowIndex = this.getLastRowAccessed();
        }

        if ((Long) this.getFieldValueAt(rowIndex, "recordID") == 0) {
            this.setFieldValueAt(rowIndex, "lineNo", numFieldCalculator.calcLineNo(rowIndex));
        } else {
            //Check if BO update prompt should be shown.
            if (!Functions.checkBlank(getFieldValueAt(rowIndex, "blanketOrderLineId"))) {
                getUserData().setUserData(3, EMCDialogFactory.createQuestionDialog(this.getTheForm(), "Update Blanket Order?", "Do you want the quantity change on the selected line to be reflected on the Blanket Order from which it was released?") == JOptionPane.YES_OPTION);
            }
        }

        this.getHeaderTable().refreshRecordNoDoRelation(-1);
        super.updatePersist(rowIndex);

    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAt(rowIndex, columnIndex, aValue);

        if ("quantity".equals(columnIndex) && !Functions.checkBlank(getFieldValueAt(rowIndex, "blanketOrderLineId"))) {
            //Should prompt for BO update when saving
            promptBOUpdate = true;
        }
    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);

        this.promptBOUpdate = false;

        lookupRelationManager.doRowChanged(this);
        if (qtyOutstanding != null) {
            Double val = (Double) this.getFieldValueAt(rowIndex, "quantity") - (Double) this.getFieldValueAt(rowIndex, "itemsReceived");
            //Blanket order over release
            if (val < 0) {
                val = 0d;
            }
            dt = new EMCDouble();
            format = new DecimalFormat(dt.getFormatToDisplay());
            format.setMinimumFractionDigits(dt.getNumberDecimalsDisplay());
            format.setMaximumFractionDigits(dt.getNumberDecimalsDisplay());
            qtyOutstanding.setText(format.format(val));
        }

        String status = (String) this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "status");

        if (cancelButton != null) {
            double itemsReceived = (Double) this.getFieldValueAt(rowIndex, "itemsReceived");
            double quantity = (Double) this.getFieldValueAt(rowIndex, "quantity");
            if ((status.equals(PurchaseOrderStatus.ORDERED.toString()) || status.equals(PurchaseOrderStatus.PARTIALLY_RECEIVED.toString())) && (quantity != itemsReceived)) {
                if (!cancelButton.isEnabled()) {
                    cancelButton.setEnabled(true);
                }
            } else {
                cancelButton.setEnabled(false);
            }
        }

    }

    @Override
    public void deleteRowCache(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }
        emcDataRelationManagerUpdate header = this.getHeaderTable();

        String status = (String) header.getFieldValueAt(header.getLastRowAccessed(), "status");

        if (!(status.equals(PurchaseOrderStatus.APPROVED.toString())) && !(status.equals(PurchaseOrderStatus.REQUISITION.toString()))) {
            Logger.getLogger("emc").log(Level.SEVERE, "Lines may not be deleted after a Purchase Order is ordered.  Use the Cancel Line button.", header.getUserData());
        } else {
            if (!Functions.checkBlank(getFieldValueAt(rowIndex, "blanketOrderLineId"))) {
                getUserData().setUserData(3, EMCDialogFactory.createQuestionDialog(this.getTheForm(), "Update Blanket Order?", "Do you want the quantity on the selected line to be added back to the Blanket Order from which it was released?") == JOptionPane.YES_OPTION);
            }
            super.deleteRowCache(rowIndex);
            header.refreshRecordNoDoRelation(-1);
        }
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        emcDataRelationManagerUpdate master = this.getHeaderTable();
        String statusString = (String) (master.getFieldValueAt(master.getLastRowAccessed(), "status"));

        if (statusString.equals(PurchaseOrderStatus.REQUISITION.toString()) || statusString.equals(PurchaseOrderStatus.APPROVED.toString())) {
            super.insertRowCache(rowIndex, addRowAfter);
            this.getHeaderTable().refreshRecordNoDoRelation(-1);
        } else {
            java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot insert a new line in a Purchase Order with a status of ordered or higher.", getUserData());
        }

    }

    public emcJTextField getQtyOutstanding() {
        return qtyOutstanding;
    }

    public void setQtyOutstanding(emcJTextField qtyOutstanding) {
        this.qtyOutstanding = qtyOutstanding;
    }

    /** Sets the cancel button. */
    public void setCancelButton(CancelLineButton cancelButton) {
        this.cancelButton = cancelButton;
    }
    //method added to fix stange behavior when
    //POmaster is not saved with button click directly on lines and use down arrow to add lines 3 rd line disappear
    //HACK - not nice do not do elsewhere! Rico

    @Override
    public void setHasTheFocus(boolean hasTheFocus) {
        //if the master had focus and the master is loosing focus first try to save the master
        if (this.checkFocus() == false && hasTheFocus == true && this.getHeaderTable().checkFocus()) {
            if (this.getHeaderTable().isRowUpdated()) {
                //check if new row
                if (this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "recordID").toString().equals("0")) {
                    this.getHeaderTable().storeInHeaderLinesSetup();
                }
            }
        }
        super.setHasTheFocus(hasTheFocus);
    }
}
