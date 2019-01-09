/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.movestock;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.emctable.stock.ViewOnlyStockDRM;
import emc.app.components.inventory.emcStockButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.movestock.InventoryMoveStockSummaryDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryMoveStockSummaryForm extends BaseInternalFrame {

    private MoveStockSummaryDRM dataRelation;

    public InventoryMoveStockSummaryForm(EMCUserData userData) {
        super("Move Stock Summary", true, true, true, true, userData);
        this.setBounds(20, 20, 900, 390);
        try {
            StockDRMParameters params = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", "transId");

            dataRelation = new MoveStockSummaryDRM(
                    new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryMoveStockSummaryDS(), userData), params, userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemReference");
            dataRelation.setFormTextId2("itemDescription");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initFrame();
    }

    private void initFrame() {
        this.setLayout(new BorderLayout());

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Stock Moved", tablePane());
        this.add(tabbed, BorderLayout.CENTER);

        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    private emcJPanel tablePane() {
        List keys = new ArrayList();
        keys.add("createdBy");
        keys.add("createdDate");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("warehouse");
        keys.add("batch");
        keys.add("serial");
        keys.add("location");
        keys.add("pallet");
        keys.add("newLocation");
        keys.add("qty");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };

        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcStockButton(this, false, new boolean[] {false, true, true}));

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
