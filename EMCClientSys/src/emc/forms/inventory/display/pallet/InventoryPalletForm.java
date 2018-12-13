/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.pallet;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.InventoryPallet;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryPalletForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public InventoryPalletForm(EMCUserData userData) {
        super("Pallet", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(
                    new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryPallet(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("palletId");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {

        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Pallet", mainTab());
        this.add(tabbed);
    }

    private emcJPanel mainTab() {
        List keys = new ArrayList();
        keys.add("palletId");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }
}
