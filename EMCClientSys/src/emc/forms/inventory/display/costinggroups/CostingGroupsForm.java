/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.costinggroups;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.inventory.costing.CostLevelDropDown;
import emc.app.components.inventory.costing.CostTypeDropDown;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.inventory.costing.DefaultDimCostCalc;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class CostingGroupsForm extends BaseInternalFrame {

    private emcJPanel pnlCostingGroups = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    public CostingGroupsForm(EMCUserData userData) {
        super("Costing Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryCostingGroups.html"));
        try {

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryCostingGroup(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("costingGroupId");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabCostingGroups() {
        List keys = new ArrayList();
        keys.add("costingGroupId");
        keys.add("description");
        keys.add("costType");
        keys.add("costLevel");
        keys.add("costingGroup");
        keys.add("defaultDimensionCostCalc");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        toptable.setComboBoxLookupToColumn("costType", new CostTypeDropDown());
        toptable.setComboBoxLookupToColumn("costLevel", new CostLevelDropDown());
        toptable.setComboBoxLookupToColumn("defaultDimensionCostCalc", new emcJComboBox(DefaultDimCostCalc.values()));
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlCostingGroups.setLayout(new GridLayout(1, 1));
        pnlCostingGroups.add(topscroll);
        dataRelation.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabCostingGroups();
        tabbedPanetop.add("Costing Groups", this.pnlCostingGroups);
        this.add(tabbedPanetop);
    }
}
