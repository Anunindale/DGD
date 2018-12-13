/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.inventory.register.forms.superclass;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class RegisterDRM extends emcDataRelationManagerUpdate {

    private String masterId;
    private String transId;
    private String warehouse;
    private String type;
    private String formType;
    private boolean doEasyEntry = true;
    private Double qty;

    public RegisterDRM(emcGenericDataSourceUpdate tableDataSource, boolean allowEasyEntry, EMCUserData userData) {
        super(tableDataSource, userData);
        this.doEasyEntry = allowEasyEntry;
        if (!doEasyEntry) {
            Object o = userData.getUserData(9);
            if (o != null) {
                if (o instanceof BigDecimal) {
                    qty = ((BigDecimal) o).doubleValue();
                } else {
                    qty = (Double) o;
                }
            }
        }
    }

    @Override
    public void setFieldValueAtRegardRelation(int rowIndex, String columnIndex, Object aValue, boolean doRelation) {
        if (!doEasyEntry && columnIndex.equals("quantity")) {
            Double val = (Double) aValue;
            if (val > 0 && qty < 0) {
                aValue = val * -1;
            }

        }
        super.setFieldValueAtRegardRelation(rowIndex, columnIndex, aValue, doRelation);
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        if (RegisterFormTypeEnum.STOCK_TAKE.toString().equals(formType)) {
            Logger.getLogger("emc").log(Level.WARNING, "You are not allowed to add any rows to a stock take journals registration.", this.getUserData());
            return;
        }
        double total = Double.valueOf(this.getFieldValueAt(this.getLastRowAccessed(), "totalQty").toString());
        double reg = Double.valueOf(this.getFieldValueAt(this.getLastRowAccessed(), "registeredQty").toString());
        super.insertRowCache(rowIndex, addRowAfter);
        this.setFieldValueAt(this.getLastRowAccessed(), "totalQty", total);
        this.setFieldValueAt(this.getLastRowAccessed(), "registeredQty", reg);
        this.setFieldValueAt(this.getLastRowAccessed(), "transId", transId);
        this.setFieldValueAt(this.getLastRowAccessed(), "masterId", masterId);
        if (!Functions.checkBlank(warehouse) && Functions.checkBlank(this.getFieldValueAt(this.getLastRowAccessed(), "warehouse"))) {
            this.setFieldValueAt(this.getLastRowAccessed(), "warehouse", warehouse);
        }
        this.setFieldValueAt(this.getLastRowAccessed(), "type", type);
        if (doEasyEntry) {
            setEasyEntryValues(rowIndex, addRowAfter);
        }
        this.setEditFlag(false);
    }

    @Override
    public void tableRowChanged(int rowIndex) {
        double total = Double.valueOf(this.getFieldValueAt(this.getLastRowAccessed(), "totalQty").toString());
        double reg = Double.valueOf(this.getFieldValueAt(this.getLastRowAccessed(), "registeredQty").toString());
        super.tableRowChanged(rowIndex);
        this.setFieldValueAt(this.getLastRowAccessed(), "totalQty", total);
        this.setFieldValueAt(this.getLastRowAccessed(), "registeredQty", reg);
        this.setFieldValueAt(this.getLastRowAccessed(), "transId", transId);
        this.setFieldValueAt(this.getLastRowAccessed(), "masterId", masterId);
        if (!Functions.checkBlank(warehouse) && Functions.checkBlank(this.getFieldValueAt(this.getLastRowAccessed(), "warehouse"))) {
            this.setFieldValueAt(this.getLastRowAccessed(), "warehouse", warehouse);
        }
        this.setFieldValueAt(this.getLastRowAccessed(), "type", type);
        this.setEditFlag(false);
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        if (RegisterFormTypeEnum.STOCK_TAKE.toString().equals(formType)) {
            Logger.getLogger("emc").log(Level.WARNING, "You are not allowed to remove any rows from a stock take journals registration.", this.getUserData());
            return;
        }
        double total = Double.valueOf(this.getFieldValueAt(this.getLastRowAccessed(), "totalQty").toString());
        double reg = Double.valueOf(this.getFieldValueAt(this.getLastRowAccessed(), "registeredQty").toString()) -
                Double.valueOf(this.getFieldValueAt(this.getLastRowAccessed(), "quantity").toString());
        super.deleteRowCache(rowIndex);
        this.setFieldValueAt(this.getLastRowAccessed(), "totalQty", total);
        this.setFieldValueAt(this.getLastRowAccessed(), "registeredQty", reg);
        this.setFieldValueAt(this.getLastRowAccessed(), "transId", transId);
        this.setFieldValueAt(this.getLastRowAccessed(), "masterId", masterId);
        if (!Functions.checkBlank(warehouse) && Functions.checkBlank(this.getFieldValueAt(this.getLastRowAccessed(), "warehouse"))) {
            this.setFieldValueAt(this.getLastRowAccessed(), "warehouse", warehouse);
        }
        this.setFieldValueAt(this.getLastRowAccessed(), "type", type);
    }

    @Override
    public void updatePersist(int rowIndex) {
        super.updatePersist(rowIndex);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
        if (columnIndex.equals("totalQty") || columnIndex.equals("registeredQty") || columnIndex.equals("transId") || columnIndex.equals("masterId")) {
            super.setEditFlag(false);
        }
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    private void setEasyEntryValues(int rowIndex, boolean addRowAfter) {
        try {
            String batch = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "batch");
            if (!Functions.checkBlank(batch)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "batch", batch);
            }
        } catch (Exception e) {
        }
        try {
            String serial = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "serial");
            if (!Functions.checkBlank(serial)) {
                int serialInt = Integer.parseInt(serial) + 1;
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "serial", String.valueOf(serialInt));
            }
        } catch (Exception e) {
        }
        try {
            String warehouse = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "warehouse");
            if (!Functions.checkBlank(warehouse)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "warehouse", warehouse);
            }
        } catch (Exception e) {
        }
        try {
            String location = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "location");
            if (!Functions.checkBlank(location)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "location", location);
            }
        } catch (Exception e) {
        }
        try {
            String pallet = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "pallet");
            if (!Functions.checkBlank(pallet)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "pallet", pallet);
            }
        } catch (Exception e) {
        }
        try {
            String uom = (String) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "uom");
            if (!Functions.checkBlank(uom)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "uom", uom);
            }
        } catch (Exception e) {
        }
        try {
            double width = (Double) getFieldValueAt(addRowAfter ? rowIndex : rowIndex + 1, "width");
            if (!Functions.checkBlank(width)) {
                setFieldValueAt(addRowAfter ? rowIndex + 1 : rowIndex, "width", width);
            }
        } catch (Exception e) {
        }
        emcJTableUpdate table = getMainTableComponent();
        emcTableModelUpdate model = table.getTableModel();

        if (addRowAfter) {
            rowIndex += 1;
        }

        table.changeSelection(rowIndex, model.getColumnByFieldName("quantity"), false, false);
    }
}
