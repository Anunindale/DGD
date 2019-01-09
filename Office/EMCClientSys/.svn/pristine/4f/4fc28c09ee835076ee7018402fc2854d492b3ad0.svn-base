package emc.app.components.emctable.stock;

import emc.app.components.emctable.*;
import emc.app.components.emctable.editors.EMCTableCellEditor;
import emc.app.components.emctable.renderers.EMCJTableHeaderRenderer;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.scrollabledesktop.formspecialization.FormSpecialization;
import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.BatchInterface;
import emc.datatypes.systemwide.Dimention1Interface;
import emc.datatypes.systemwide.Dimention2Interface;
import emc.datatypes.systemwide.Dimention3Interface;
import emc.datatypes.systemwide.ItemInterface;
import emc.datatypes.systemwide.LocationInterface;
import emc.datatypes.systemwide.PalletInterface;
import emc.datatypes.systemwide.SerialNoInterface;
import emc.datatypes.systemwide.WarehouseInterface;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.TransEnum;
import emc.enums.modules.enumEMCModules;
import emc.forms.app.ActiveDimColumnData;
import emc.forms.app.InventoryViewForm;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author wikus
 */
public class StockDRM extends emcDataRelationManagerUpdate implements FormSpecialization {

    private emcJTableUpdate table;
    private emcTableModelUpdate model;
    private EMCUserData deskUserData = super.getUserData();
    private HashMap<String, EMCDataType> dtMap;
    private Set<String> allDTKeys;
    protected HashMap<String, TableCellEditor> cellEditors;
    private ItemLookupRelationManager itemLookupRelationManager;
    private EMCTableCellEditor dimension1Editor;
    private EMCTableCellEditor dimension2Editor;
    private EMCTableCellEditor dimension3Editor;
    private StockDRMParameters paramFile;

    /** Creates a new instance of StockDRM.  User specifies field names to generate related form user data.
     * This means that the DRM does not have to be overridden in order to open transaction and on hand forms. */
    public StockDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, userData);
        this.paramFile = param;
    }

    public StockDRM() {
        super();
    }

    /**
     * Add a range of columns the the table on the form
     * @param data holder class for the columns to be added.
     */
    public void setTableColumns(ActiveDimColumnData data) {
        model = super.getTableModel();
        int width = 0;
        if (model != null) {
            dtMap = this.getDataTypeMap();
            allDTKeys = dtMap.keySet();
            List<String> fieldKeys = model.getKeys();
            List<String> dimList = new ArrayList<String>() {

                @Override
                public void add(int pos, String element) {

                    int size = super.size();

                    if (super.size() <= pos) {
                        for (int i = size; i <= pos; i++) {
                            super.add("");
                        }
                    }

                    super.set(pos, element);
                }
            };
            Integer itemPos = null;
            for (String curKey : allDTKeys) {
                if (dtMap.get(curKey) instanceof Dimention1Interface) {
                    if (data.isDimention1()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        dimList.add(Dimention1Interface.sortCode, curKey);
                        fieldKeys.remove(curKey);
                        cellEditors.put(curKey, setLookup("dimention1"));
                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof Dimention3Interface) {
                    if (data.isDimention3()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        dimList.add(Dimention3Interface.sortCode, curKey);
                        fieldKeys.remove(curKey);
                        cellEditors.put(curKey, setLookup("dimention3"));

                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof Dimention2Interface) {
                    if (data.isDimention2()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        fieldKeys.remove(curKey);
                        dimList.add(Dimention2Interface.sortCode, curKey);
                        cellEditors.put(curKey, setLookup("dimention2"));

                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof BatchInterface) {
                    if (data.isBatch()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        fieldKeys.remove(curKey);
                        dimList.add(BatchInterface.sortCode, curKey);
                        cellEditors.put(curKey, setLookup("batch"));

                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof SerialNoInterface) {
                    if (data.isSerialNo()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        fieldKeys.remove(curKey);
                        dimList.add(SerialNoInterface.sortCode, curKey);
                        cellEditors.put(curKey, setLookup("serialNo"));

                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof WarehouseInterface) {
                    if (data.isWarehouse()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        fieldKeys.remove(curKey);
                        dimList.add(WarehouseInterface.sortCode, curKey);
                        cellEditors.put(curKey, setLookup("warehouse"));

                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof LocationInterface) {
                    if (data.isLocation()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        fieldKeys.remove(curKey);
                        dimList.add(LocationInterface.sortCode, curKey);

                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof PalletInterface) {
                    if (data.isPallet()) {
                        if (!fieldKeys.contains(curKey)) width += dtMap.get(curKey).getColumnWidth();
                        fieldKeys.remove(curKey);
                        dimList.add(PalletInterface.sortCode, curKey);

                    } else {
                        if (fieldKeys.contains(curKey)) {
                            fieldKeys.remove(curKey);
                            width -= dtMap.get(curKey).getColumnWidth();
                        }
                    }
                }
                if (dtMap.get(curKey) instanceof ItemInterface) {
                    itemPos = fieldKeys.indexOf(curKey) + 1;
                }
            }
            {
                for (int i = 0; i < dimList.size(); i++) {
                    if (Functions.checkBlank(dimList.get(i))) {
                        dimList.remove(i);
                        i--;
                    }
                }
                fieldKeys.addAll(itemPos == null ? fieldKeys.size() : itemPos, dimList);
            }
            model.setKeys(fieldKeys);
            model.fireTableStructureChanged();
            table = super.getMainTableComponent();
            if (this.getTheForm() != null) this.getTheForm().adjustFormSize(width, 0);
            {
                TableCellEditor editor;
                int i = 0;
                for (String s : fieldKeys) {
                    editor = cellEditors.get(s);
                    table.setLookupCellEditor(i, editor);
                    i++;
                }
            }
            EMCJTableHeaderRenderer headerRenderer;
            for (int i = 0; i < table.getColumnCount(); i++) {
                headerRenderer = new EMCJTableHeaderRenderer();
                table.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }
            table.setColumnSizesFromDataTypes();
        } else {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Form doesn`t hava a table.", deskUserData);
            }
        }
    }

    public List setChecked() {
        model = super.getTableModel();
        table = super.getMainTableComponent();
        cellEditors = new HashMap<String, TableCellEditor>();
        List<String> keys = this.model.getKeys();
        dtMap = this.getDataTypeMap();
        List<Boolean> checked = new ArrayList<Boolean>();
        for (int i = 0; i < 8; i++) {
            checked.add(false);
        }
        int column = 0;
        try {
            for (String s : keys) {
                if (dtMap.get(s) instanceof Dimention1Interface) {
                    checked.set(0, true);
                    dimension1Editor = (EMCTableCellEditor) table.getCellEditor(0, column);
                    cellEditors.put(s, dimension1Editor);
                    column++;
                    continue;
                } else if (dtMap.get(s) instanceof Dimention3Interface) {
                    checked.set(1, true);
                    dimension3Editor = (EMCTableCellEditor) table.getCellEditor(0, column);
                    cellEditors.put(s, dimension3Editor);
                    column++;
                    continue;
                } else if (dtMap.get(s) instanceof Dimention2Interface) {
                    checked.set(2, true);
                    dimension2Editor = (EMCTableCellEditor) table.getCellEditor(0, column);
                    cellEditors.put(s, dimension2Editor);
                    column++;
                    continue;
                } else if (dtMap.get(s) instanceof WarehouseInterface) {
                    checked.set(3, true);
                } else if (dtMap.get(s) instanceof BatchInterface) {
                    checked.set(4, true);
                } else if (dtMap.get(s) instanceof SerialNoInterface) {
                    checked.set(5, true);
                } else if (dtMap.get(s) instanceof LocationInterface) {
                    checked.set(6, true);
                } else if (dtMap.get(s) instanceof PalletInterface) {
                    checked.set(7, true);
                }
                cellEditors.put(s, table.getCellEditor(0, column));
                column++;
            }
        } catch (ClassCastException ex) {
            if (EMCDebug.getDebug()) Logger.getLogger("emc").log(Level.SEVERE, "The editors on your 3 dimension fields must extend EMCTableCellEditor", this.getUserData());
        }
        return checked;
    }

    public List containsKey() {
        dtMap = this.getDataTypeMap();
        List<Boolean> contain = new ArrayList<Boolean>(8);
        for (int i = 0; i < 8; i++) {
            contain.add(false);
        }
        for (EMCDataType type : dtMap.values()) {
            if (type instanceof Dimention1Interface) {
                contain.set(0, true);
            } else if (type instanceof Dimention3Interface) {
                contain.set(1, true);
            } else if (type instanceof Dimention2Interface) {
                contain.set(2, true);
            } else if (type instanceof WarehouseInterface) {
                contain.set(3, true);
            } else if (type instanceof BatchInterface) {
                contain.set(4, true);
            } else if (type instanceof SerialNoInterface) {
                contain.set(5, true);
            } else if (type instanceof LocationInterface) {
                contain.set(6, true);
            } else if (type instanceof PalletInterface) {
                contain.set(7, true);
            }
        }
        return contain;
    }

    private EMCTableCellEditor setLookup(String lkpType) {
        List Keys = new ArrayList<String>();
        EMCTableCellEditor theEditor = null;
        if (lkpType.equals("dimention1")) {
            theEditor = getDimension1Editor();
        } else if (lkpType.equals("dimention2")) {
            theEditor = getDimension2Editor();
        } else if (lkpType.equals("dimention3")) {
            theEditor = getDimension3Editor();
        } else if (lkpType.equals("warehouse")) {
            Keys.add("warehouseId");
            Keys.add("description");
            EMCLookupJTableComponent theLookup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Warehouse());
            EMCLookupPopup thePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryWarehouse(),
                    "warehouseId", Keys, this.getUserData());
            theLookup.setPopup(thePopup);
            theEditor = new EMCLookupTableCellEditor(theLookup);
        } else if (lkpType.equals("batch")) {
            Keys.add("itemBatchId");
            Keys.add("description");
            EMCLookupJTableComponent theLookup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ItemBatch());
            EMCLookupPopup thePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemBatch(),
                    "itemBatchId", Keys, this.getUserData());
            theLookup.setPopup(thePopup);
            theEditor = new EMCLookupTableCellEditor(theLookup);
        } else if (lkpType.equals("serialNo")) {
            Keys.add("serialId");
            Keys.add("description");
            EMCLookupJTableComponent theLookup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ItemSerial());
            EMCLookupPopup thePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemSerial(),
                    "serialId", Keys, this.getUserData());
            theLookup.setPopup(thePopup);
            theEditor = new EMCLookupTableCellEditor(theLookup);
        }

        return theEditor;
    }

    /** Fires lookup relations. */
    @Override
    public void doRelation(int rowIndex) {
        if (itemLookupRelationManager != null) {
            itemLookupRelationManager.doRowChanged(this);
        }
        super.doRelation(rowIndex);
    }

    /** Adds a lookup relation manager to the drm. */
    public void addItemLookupRelationManager(ItemLookupRelationManager lrm) {
        this.itemLookupRelationManager = lrm;
    }

    public EMCTableCellEditor getDimension1Editor() {
        if (!(dimension1Editor instanceof EMCLookupTableCellEditor)) {
            List Keys = new ArrayList();
            Keys.add("dimensionId");
            Keys.add("description");
            EMCLookupJTableComponent theLookup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension1());
            EMCLookupPopup thePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension1(),
                    "dimensionId", Keys, this.getUserData());
            theLookup.setPopup(thePopup);
            dimension1Editor = new EMCLookupTableCellEditor(theLookup);
        }
        return dimension1Editor;
    }

    public EMCTableCellEditor getDimension2Editor() {
        if (!(dimension2Editor instanceof EMCLookupTableCellEditor)) {
            List Keys = new ArrayList();
            Keys.add("dimensionId");
            Keys.add("description");
            EMCLookupJTableComponent theLookup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension2());
            EMCLookupPopup thePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension2(),
                    "dimensionId", Keys, this.getUserData());
            theLookup.setPopup(thePopup);
            dimension2Editor = new EMCLookupTableCellEditor(theLookup);
        }
        return dimension2Editor;
    }

    public EMCTableCellEditor getDimension3Editor() {
        if (!(dimension3Editor instanceof EMCLookupTableCellEditor)) {
            List Keys = new ArrayList();
            Keys.add("dimensionId");
            Keys.add("description");
            EMCLookupJTableComponent theLookup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension3());
            EMCLookupPopup thePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension3(),
                    "dimensionId", Keys, this.getUserData());
            theLookup.setPopup(thePopup);
            dimension3Editor = new EMCLookupTableCellEditor(theLookup);
        }
        return dimension3Editor;
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        EMCQuery query;
        int lastRowAccessed = getLastRowAccessed();

        switch (Index) {
            case 10:
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
                query.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
                query.addAnd("companyId", formUserData.getCompanyId());
                query.addAnd("itemId", getFieldValueAt(lastRowAccessed, paramFile.getItemIdField()), InventoryTransactions.class.getName());
                query.addAnd("closedFlag", false);
                if (!Functions.checkBlank(paramFile.getTransIdField())) query.addAnd("transId", getFieldValueAt(lastRowAccessed, paramFile.getTransIdField()), InventoryTransactions.class.getName());
                if (!Functions.checkBlank(paramFile.getDimension1Field()) && this.getTableModel().getKeys().contains(paramFile.getDimension1Field())) query.addAnd("dimension1Id", getFieldValueAt(lastRowAccessed, paramFile.getDimension1Field()), InventoryDimensionTable.class.getName());
                if (!Functions.checkBlank(paramFile.getDimension2Field()) && this.getTableModel().getKeys().contains(paramFile.getDimension2Field())) query.addAnd("dimension2Id", getFieldValueAt(lastRowAccessed, paramFile.getDimension2Field()), InventoryDimensionTable.class.getName());
                if (!Functions.checkBlank(paramFile.getDimension3Field()) && this.getTableModel().getKeys().contains(paramFile.getDimension3Field())) query.addAnd("dimension3Id", getFieldValueAt(lastRowAccessed, paramFile.getDimension3Field()), InventoryDimensionTable.class.getName());
                if (!Functions.checkBlank(paramFile.getSerialField()) && this.getTableModel().getKeys().contains(paramFile.getSerialField())) query.addAnd("itemSerialId", getFieldValueAt(lastRowAccessed, paramFile.getSerialField()), InventoryDimensionTable.class.getName());
                if (!Functions.checkBlank(paramFile.getBatchField()) && this.getTableModel().getKeys().contains(paramFile.getBatchField())) query.addAnd("batchId", getFieldValueAt(lastRowAccessed, paramFile.getBatchField()), InventoryDimensionTable.class.getName());
                if (!Functions.checkBlank(paramFile.getWarehouseField()) && this.getTableModel().getKeys().contains(paramFile.getWarehouseField())) query.addAnd("warehouseId", getFieldValueAt(lastRowAccessed, paramFile.getWarehouseField()), InventoryDimensionTable.class.getName());
                if (!Functions.checkBlank(paramFile.getLocationField()) && this.getTableModel().getKeys().contains(paramFile.getLocationField())) query.addAnd("locationId", getFieldValueAt(lastRowAccessed, paramFile.getLocationField()), InventoryDimensionTable.class.getName());
                formUserData.setUserData(0, query);
                formUserData.setUserData(1, "");
                formUserData.setUserData(2, TransEnum.ADD_EXTRA.toString());
                return formUserData;
            case 11:
                query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                query.addAnd("companyId", formUserData.getCompanyId());
                query.addAnd("itemId", getFieldValueAt(lastRowAccessed, paramFile.getItemIdField()));
                if (!Functions.checkBlank(paramFile.getDimension1Field()) && this.getTableModel().getKeys().contains(paramFile.getDimension1Field())) query.addAnd("dimension1", getFieldValueAt(lastRowAccessed, paramFile.getDimension1Field()));
                if (!Functions.checkBlank(paramFile.getDimension2Field()) && this.getTableModel().getKeys().contains(paramFile.getDimension2Field())) query.addAnd("dimension2", getFieldValueAt(lastRowAccessed, paramFile.getDimension2Field()));
                if (!Functions.checkBlank(paramFile.getDimension3Field()) && this.getTableModel().getKeys().contains(paramFile.getDimension3Field())) query.addAnd("dimension3", getFieldValueAt(lastRowAccessed, paramFile.getDimension3Field()));
                if (!Functions.checkBlank(paramFile.getSerialField()) && this.getTableModel().getKeys().contains(paramFile.getSerialField())) query.addAnd("serialNo", getFieldValueAt(lastRowAccessed, paramFile.getSerialField()));
                if (!Functions.checkBlank(paramFile.getBatchField()) && this.getTableModel().getKeys().contains(paramFile.getBatchField())) query.addAnd("batch", getFieldValueAt(lastRowAccessed, paramFile.getBatchField()));
                if (!Functions.checkBlank(paramFile.getWarehouseField()) && this.getTableModel().getKeys().contains(paramFile.getWarehouseField())) query.addAnd("warehouse", getFieldValueAt(lastRowAccessed, paramFile.getWarehouseField()));
                if (!Functions.checkBlank(paramFile.getLocationField()) && this.getTableModel().getKeys().contains(paramFile.getLocationField())) query.addAnd("location", getFieldValueAt(lastRowAccessed, paramFile.getLocationField()));
                formUserData.setUserData(0, query);
                formUserData.setUserData(1, true);
                formUserData.setUserData(2, TransEnum.ADD_EXTRA.toString());
                return formUserData;
            default:
                return formUserData;
        }
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAt(rowIndex, columnIndex, aValue);

        if (Functions.checkObjectsEqual(columnIndex, paramFile.getItemReferenceField())) {
            if (this.itemLookupRelationManager != null) {
                this.itemLookupRelationManager.fireItemUpdated();
            }
        }
    }

    public void doSpecialization(BaseInternalFrame testFrame) {
        if (testFrame instanceof InventoryViewForm) {
            ((InventoryViewForm) testFrame).setParentDRM(this);
        }
    }
}
