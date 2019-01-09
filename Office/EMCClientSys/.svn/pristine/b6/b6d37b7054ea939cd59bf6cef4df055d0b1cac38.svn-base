/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.transactions;

import emc.app.components.emctable.stock.ViewOnlyStockDRM;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.stocksettlement.InventoryStockSettlementHistory;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.inventory.transactions.datasource.InventoryTransactionsDS;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.forms.app.ActiveDimColumnData;
import emc.framework.EMCQuery;
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
public class TransactionDRM extends ViewOnlyStockDRM {

    private boolean fetchData = true;

    public TransactionDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, boolean fetchData, EMCUserData userData) {
        super(tableDataSource, param, userData);
        this.fetchData = fetchData;
        if (!fetchData) {
            setOriginalQuery(new InventoryTransactionsDS().getQuery());
        }
    }

    @Override
    public void doPartialRelation(int rowIndex) {
        super.doPartialRelation(rowIndex);
        this.setUserData(this.getUserData());
    }

    @Override
    public void setUserData(EMCUserData userData) {
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
                    if (col.equals("config")) data.setDimention1(true);
                    if (col.equals("size")) data.setDimention2(true);
                    if (col.equals("colour")) data.setDimention3(true);
                    if (col.equals("warehouse")) data.setWarehouse(true);
                    if (col.equals("batch")) data.setBatch(true);
                    if (col.equals("serialNo")) data.setSerialNo(true);
                    if (col.equals("location")) data.setLocation(true);
                    if (col.equals("pallet")) data.setPallet(true);
                }
                super.setChecked();
                super.setTableColumns(data);
            }
        }
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 0:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
                if (InventoryTransactionDirection.IN.toString().equals(this.getLastFieldValueAt("direction"))) {
                    query.addTableAnd(InventoryStockSettlementHistory.class.getName(), "recordID", InventoryTransactions.class.getName(), "outTxUsedInSettlement");
                    query.addAnd("inTxRecordId", getLastFieldValueAt("recordID"), InventoryStockSettlementHistory.class.getName());
                } else if (InventoryTransactionDirection.OUT.toString().equals(this.getLastFieldValueAt("direction"))) {
                    query.addTableAnd(InventoryStockSettlementHistory.class.getName(), "recordID", InventoryTransactions.class.getName(), "inTxRecordId");
                    query.addAnd("outTxUsedInSettlement", getLastFieldValueAt("recordID"), InventoryStockSettlementHistory.class.getName());
                }
                List x = new ArrayList();
                x.add(0, query);
                formUserData.setUserData(x);
                break;
        }

        return formUserData;
    }

    @Override
    public void addRelatedForm(BaseInternalFrame theFormToAdd) {
        //super.addRelatedForm(theFormToAdd);
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
