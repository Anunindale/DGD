/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.stocktakecapture;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.journals.JournalLinesDRM;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class StocktakeCaptureForm extends BaseInternalFrame {

    private CaptureDRM dataManager;

    public StocktakeCaptureForm(EMCUserData userData) {
        super("Stock Take", true, true, true, true, userData);
        this.setBounds(20, 20, 900, 600);
        try {
            JournalLinesDRM journalLineDRM = (JournalLinesDRM) userData.getUserData(3);
            userData.getUserData().remove(3);
            dataManager = new CaptureDRM(new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(),
                    new InventoryStocktakeCaptureDS(), userData), userData);
            this.setDataManager(dataManager);
            dataManager.setJournalLineDRM(journalLineDRM);
            dataManager.setTheForm(this);
            dataManager.setFormTextId1("itemReference");
            dataManager.setFormTextId2("itemDescription");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Stock Take", tablePane());
        this.setContentPane(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("pageNumber");
        keys.add("warehouse");
        keys.add("location");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("batch");
        keys.add("serial");
        keys.add("pallet");
        keys.add("quantityOnHand");
        keys.add("quantityCounted");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table, dataManager.getUserData());
        table.setColumnEditable("quantityOnHand", false);
        table.setColumnEditable("itemDescription", false);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table, EMCUserData userData) {
        table.setLookupToColumn("itemReference", EMCItemLookupFactory.createItemLookup(userData));
        table.setLookupToColumn("warehouse", EMCDimensionLookupFactory.createWarehouseJTableLookup(userData));
        table.setLookupToColumn("location", EMCDimensionLookupFactory.createLocationJTableLookup(userData));
        table.setLookupToColumn("dimension1", EMCDimensionLookupFactory.createDimension1JTableLookup(userData));
        table.setLookupToColumn("dimension2", EMCDimensionLookupFactory.createDimension2JTableLookup(userData));
        table.setLookupToColumn("dimension3", EMCDimensionLookupFactory.createDimension3JTableLookup(userData));
        table.setLookupToColumn("pallet", EMCDimensionLookupFactory.createPalletJTableLookup(userData));
    }

    @Override
    public void setUserData(EMCUserData userData) {
        if (userData.getUserData() != null && userData.getUserData().size() > 3 && userData.getUserData(3) instanceof JournalLinesDRM) {
            userData.getUserData().remove(3);
        }
        super.setUserData(userData);
    }
}
