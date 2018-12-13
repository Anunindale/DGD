/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.onhand;

import emc.forms.app.ActiveDimColumnData;
import emc.app.components.emctable.stock.ViewOnlyStockDRM;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.entity.inventory.transactions.datasource.InventorySummaryDS;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author wikus
 */
public class OnHandDRM extends ViewOnlyStockDRM {

    private boolean fetchData = true;

    public OnHandDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, boolean fetchData, EMCUserData userData) {
        super(tableDataSource, param, userData);
        this.fetchData = fetchData;
        if (!fetchData) {
            setOriginalQuery(new InventorySummaryDS().getQuery());
        }
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        //    super.setFieldValueAt(rowIndex, columnIndex, aValue);
    }

    //Next three methods where overridden for the grouping of the data according to the columns that are active
    /**
     * Method that adds columns to the table from the inventory button
     * @param data
     */
    @Override
    public void setTableColumns(ActiveDimColumnData data) {
        super.setTableColumns(data);
        if (fetchData) {
            EMCUserData userData = this.getUserData();
            List l = new ArrayList();
            l.add(userData.getUserData(0));
            l.add(userData.getUserData(1));
            userData.setUserData(l);
            this.setUserData(userData);
        }
    }

    /**
     * Also refresses all of the data because data could be grouped
     * @param rowIndex
     */
    @Override
    public void refreshRecord(int rowIndex) {
        super.refreshData();
    }

    @Override
    public void doPartialRelation(int rowIndex) {
        super.doPartialRelation(rowIndex);
        this.setUserData(this.getUserData());
    }

    @Override
    public void setUserData(EMCUserData userData) {

        List keys = this.getTableModel().getKeys();
        if (keys.contains("dimension1")) userData.setUserData(11, true);
        if (keys.contains("dimension2")) userData.setUserData(12, true);
        if (keys.contains("dimension3")) userData.setUserData(13, true);
        if (keys.contains("warehouse")) userData.setUserData(14, true);
        if (keys.contains("batch")) userData.setUserData(15, true);
        if (keys.contains("serialNo")) userData.setUserData(16, true);
        if (keys.contains("location")) userData.setUserData(17, true);
        if (keys.contains("pallet")) userData.setUserData(18, true);
        super.setUserData(userData);
        emcJTableUpdate table = this.getMainTableComponent();

        if (table != null) {
            Object value = this.getFieldValueAt(getLastRowAccessed(), "displayColumns");
            if (!Functions.checkBlank(value)) {
                ActiveDimColumnData data = new ActiveDimColumnData();
                StringTokenizer token = new StringTokenizer(value.toString(), "~");
                String col;
                while (token.hasMoreTokens()) {
                    col = token.nextToken();
                    if (col.equals("dimension1")) data.setDimention1(true);
                    if (col.equals("dimension2")) data.setDimention2(true);
                    if (col.equals("dimension3")) data.setDimention3(true);
                    if (col.equals("warehouse")) data.setWarehouse(true);
                    if (col.equals("batch")) data.setBatch(true);
                    if (col.equals("serialNo")) data.setSerialNo(true);
                    if (col.equals("location")) data.setLocation(true);
                    if (col.equals("pallet")) data.setPallet(true);
                }
                setChecked();
                super.setTableColumns(data);
            }
        }
    }

    @Override
    public void doRelation(int rowIndex) {
        if (fetchData) {
            super.doRelation(rowIndex);
        }
    }

    @Override
    public void filterData(Map<String, String[]> searchFields) {
        for (String[] value : searchFields.values()) {
            if (value != null) {
                fetchData = true;
                break;
            }
        }
        if (fetchData) {
            super.filterData(searchFields);
        }
    }

    @Override
    public void filterData(String fieldName) {
        fetchData = true;
        super.filterData(fieldName);
    }

    @Override
    public Object getFieldValueAt(int rowIndex, String columnIndex) {
        if (fetchData) {
            return super.getFieldValueAt(rowIndex, columnIndex);
        } else return null;
    }

    @Override
    public Object getLastFieldValueAt(String columnIndex) {
        if (fetchData) {
            return super.getLastFieldValueAt(columnIndex);
        } else return null;
    }

    @Override
    public int getRowCount() {
        if (fetchData) {
            return super.getRowCount();
        } else return 1;
    }
}
