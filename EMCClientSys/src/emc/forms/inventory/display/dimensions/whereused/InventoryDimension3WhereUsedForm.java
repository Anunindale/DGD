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
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.whreused.InventoryDimension3WhereUsed;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.Dimension3;
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
public class InventoryDimension3WhereUsedForm extends BaseInternalFrame {

    private EMCUserData userData;
    private EMCControlLookupComponentDRM dataManager;

    public InventoryDimension3WhereUsedForm(EMCUserData userData) {
        super("Where Used - Colour", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        if (userData.getUserData() == null || userData.getUserData().size() == 0) {
            userData.setUserData(0, " ");
        }
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryDimension3WhereUsed(), userData), userData) {

            @Override
            public void setUserData(EMCUserData userData) {
                Object lookupValue = dataManager.getLookup().getValue();
                if (Functions.checkBlank(lookupValue)) {
                    lookupValue = userData.getUserData(3);
                }
                userData.setUserData(0, lookupValue);
                super.setUserData(userData);
            }

            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                Logger.getLogger("emc").log(Level.SEVERE, "This is a view only form.", InventoryDimension3WhereUsedForm.this.userData);
            }

            @Override
            public void updatePersist(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "This is a view only form.", InventoryDimension3WhereUsedForm.this.userData);
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "This is a view only form.", InventoryDimension3WhereUsedForm.this.userData);
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId2("itemReference");
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
        EMCControlLookupComponent lkpDimension3 = new EMCControlLookupComponent(new Dimension3(), dataManager, "dimension3",
                txtDesc, "description", InventoryDimension3WhereUsed.class.getName());
        lkpDimension3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData));
        dataManager.setLookup(lkpDimension3);
        Component[][] comp = {{new emcJLabel("Colour"), lkpDimension3, new emcJLabel("Description"), txtDesc}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Colour");
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
