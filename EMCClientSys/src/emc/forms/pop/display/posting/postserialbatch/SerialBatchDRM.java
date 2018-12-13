/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting.postserialbatch;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.framework.EMCUserData;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class SerialBatchDRM extends emcDataRelationManagerUpdate {

    private String transId;
    private PostSerialBatchForm theForm;

    public SerialBatchDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (emc.functions.Functions.checkBlank(super.getFieldValueAt(rowIndex, columnIndex))) {
            super.setFieldValueAt(rowIndex, "transId", transId);
        }
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
        if (columnIndex.equals("quantity")) {
            setRegQty();
        }
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void setRegQty() {
        double regQty = 0;
        for (int i = 0; i < this.getRowCount(); i++) {
            regQty += (Double) super.getFieldValueAt(i, "quantity");
        }
        theForm = (PostSerialBatchForm) this.getTheForm();
        theForm.setRegQty(regQty);
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        super.deleteRowCache(rowIndex);
        setRegQty();
    }

    @Override
    public void refreshData() {
        super.refreshData();
        setRegQty();
    }

    @Override
    public void refreshRecord(int rowIndex) {
        super.refreshRecord(rowIndex);
        setRegQty();
    }

    @Override
    public void updatePersist(int rowIndex) {
        super.updatePersist(rowIndex);
        setRegQty();
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        super.insertRowCache(rowIndex, addRowAfter);

        theForm = (PostSerialBatchForm) this.getTheForm();
        if (theForm.getClassRep() == 0) {
            //Check if a new line was inserted.  Old row will be updated if not saved, new row will not.
            if (isRowUpdated()) {
                return;
            }

            String batch = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "batch");
            if (!Functions.checkBlank(batch)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "batch", batch);
            }

            String serial = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "serial");
            if (!Functions.checkBlank(serial)) {
                try {
                    int serialInt = Integer.parseInt(serial) + 1;
                    setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "serial", String.valueOf(serialInt));
                } catch (NumberFormatException ex) {
                }
            }

            String location = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "location");
            if (!Functions.checkBlank(location)) {
                try {
                    setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "location", location);
                } catch (NumberFormatException ex) {
                }
            }

            Double width = (Double) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "width");
            if (!Functions.checkBlank(width)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "width", width);
            }

            String widthUOM = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "widthUOM");
            if (!Functions.checkBlank(widthUOM)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "widthUOM", widthUOM);
            }

            emcJTableUpdate table = getMainTableComponent();
            emcTableModelUpdate model = table.getTableModel();

            if (addRowAfter) {
                rowIndex += 1;
            }

            table.changeSelection(rowIndex, model.getColumnByFieldName("quantity"), false, false);
        }
    }
}
