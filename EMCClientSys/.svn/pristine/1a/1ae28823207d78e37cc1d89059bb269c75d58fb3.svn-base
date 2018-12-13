/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.dimensions.whereused;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.whreused.InventoryDimension1WhereUsed;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.Dimension1;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class InventoryDimension1WhereUsedForm extends BaseInternalFrame {

    private EMCUserData userData;
    private EMCControlLookupComponentDRM dataManager;

    public InventoryDimension1WhereUsedForm(EMCUserData userData) {
        super("Where Used - Configurations", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        if (userData.getUserData() == null || userData.getUserData().size() == 0) {
            userData.setUserData(0, " ");
        }
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryDimension1WhereUsed(), userData), userData) {

            @Override
            public void setUserData(EMCUserData userData) {
                Object lookupValue = dataManager.getLookup().getValue();
                if (Functions.checkBlank(lookupValue)) {
                    lookupValue = userData.getUserData(3);
                }
                userData.setUserData(0, lookupValue);
//                if (isSearching()) {
//                    userData.setUserData(1, this.getSearchFields());
//                }
                super.setUserData(userData);
            }

            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                Logger.getLogger("emc").log(Level.SEVERE, "This is a view only form.", InventoryDimension1WhereUsedForm.this.userData);
            }

            @Override
            public void updatePersist(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "This is a view only form.", InventoryDimension1WhereUsedForm.this.userData);
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "This is a view only form.", InventoryDimension1WhereUsedForm.this.userData);
            }
        };
        dataManager.setOriginalQuery(new EMCQuery());
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("itemReference");
        dataManager.setFormTextId2("itemDescription");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(lookupPane(), BorderLayout.NORTH);
        contentPane.add(tablePane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJPanel lookupPane() {
        emcJTextField txtDesc = new emcJTextField();
        EMCControlLookupComponent lkpDimension1 = new EMCControlLookupComponent(new Dimension1(), dataManager, "dimension1",
                txtDesc, "description", InventoryDimension1WhereUsed.class.getName());
        lkpDimension1.setPopup(new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData));
        dataManager.setLookup(lkpDimension1);
        Component[][] comp = {{new emcJLabel("Configurations"), lkpDimension1, new emcJLabel("Description"), txtDesc}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Configurations");
    }

    private emcJTabbedPane tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("itemReference");
        keys.add("itemDescription");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Where Used", tableScroll);
        return tabbed;
    }
}
