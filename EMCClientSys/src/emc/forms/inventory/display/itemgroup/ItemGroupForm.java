/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemgroup;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class ItemGroupForm extends BaseInternalFrame {

    private emcJPanel pnlItemGroups = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    private EMCLookupJTableComponent lookupAccessGroup;
    private EMCLookupTableCellEditor accessEditor;
    private EMCLookupJTableComponent lkpHierarchy;
    private EMCLookupTableCellEditor hierarchyEditor;

    public ItemGroupForm(EMCUserData userData) {
        super("Item Groups", true, true, true, true, userData);
        EMCUserData copyUD = userData.copyUserData();
        this.setBounds(20, 20, 900, 300);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryBrandGroups.html"));
        try {

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemGroup(), userData), userData);
            this.setDataManager(dataRelation);

            lookupAccessGroup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ItemAccessGroup());
            List accessKeys = new ArrayList();
            accessKeys.add("itemAccessGroupId");
            accessKeys.add("description");
            EMCLookupPopup accessPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemAccessGroup(), "itemAccessGroupId", accessKeys, copyUD);
            lookupAccessGroup.setPopup(accessPopup);
            accessEditor = new EMCLookupTableCellEditor(lookupAccessGroup);

            lkpHierarchy = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ClassificationHierarchies());
            List hierarchyKeys = new ArrayList();
            hierarchyKeys.add("hierarchyId");
            hierarchyKeys.add("description");
            EMCLookupPopup hierarchyPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.classifications.InventoryClassificationHierarchy(), "hierarchyId", hierarchyKeys, copyUD);
            lkpHierarchy.setPopup(hierarchyPopup);
            hierarchyEditor = new EMCLookupTableCellEditor(lkpHierarchy);


            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemGroup");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabItemGroups() {
        List keys = new ArrayList();
        keys.add("itemGroup");
        keys.add("description");
        keys.add("accessGroupId");
        keys.add("hierarchy");
        keys.add("scrap");
        keys.add("costingScrap");
        keys.add("quarantineReq");
        keys.add("commissionApplicable");
//        keys.add("mpsFlag");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);

        toptable.setLookupCellEditor(2, accessEditor);
        toptable.setLookupCellEditor(3, hierarchyEditor);
//        toptable.setComboBoxLookupToColumn("mpsFlag", new emcJComboBox(MPSLevel.values()));

        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlItemGroups.setLayout(new GridLayout(1, 1));
        pnlItemGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabItemGroups();
        tabbedPanetop.add("Item Groups", this.pnlItemGroups);
        this.add(tabbedPanetop, BorderLayout.CENTER);
    }
}
