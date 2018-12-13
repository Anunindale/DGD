/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.unitsofmeasuredetailedconversion;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.datasource.BaseUOMDetailedConversionTableDS;
import emc.entity.inventory.InventoryItemMaster;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.ItemMaster;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseUOMDetailedConversionTableForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;
    private long masterRecordID;

    public BaseUOMDetailedConversionTableForm(EMCUserData userData) {
        super("Units of Measure Detailed Conversion", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        masterRecordID = (Long) userData.getUserData().remove(1);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseUOMDetailedConversionTableDS(), userData), userData) {

            @Override
            public void setUserData(EMCUserData userData) {
                masterRecordID = (Long) userData.getUserData().remove(1);
                super.setUserData(userData);
            }

            @Override
            public void updatePersist(int rowIndex) {
                if (rowIndex == -1) {
                    rowIndex = getLastRowAccessed();
                }
                if ((Long) getLastFieldValueAt("masterRecordID") == 0) {
                    setFieldValueAt(rowIndex, "masterRecordID", masterRecordID);
                }
                super.updatePersist(rowIndex);
            }

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                if ((Long) getLastFieldValueAt("masterRecordID") == 0) {
                    super.setFieldValueAt(rowIndex, "masterRecordID", masterRecordID);
                }
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("unit");
        dataManager.setFormTextId2("baseUnit");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);

        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("unit");
        keys.add("conversion");
        keys.add("baseUnit");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupTableLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupTableLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpItem = new EMCLookupJTableComponent(new ItemMaster());
        lkpItem.setPopup(new EMCLookupPopup(new InventoryItemMaster(), "itemReference", userData));
        table.setLookupToColumn("itemReference", lkpItem);

        table.setColumnEditable("itemDescription", false);
        table.setColumnEditable("unit", false);
        table.setColumnEditable("baseUnit", false);
    }
}
