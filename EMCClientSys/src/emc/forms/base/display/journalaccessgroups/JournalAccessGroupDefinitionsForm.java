/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.journalaccessgroups;

import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupDefinitions;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupEmployees;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroups;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.JournalAccessGroupsMI;
import emc.menus.inventory.menuitems.display.JournalDefinitions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class JournalAccessGroupDefinitionsForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM drm;

    /** Creates a new instance of JournalAccessGroupDefinitionsForm. */
    public JournalAccessGroupDefinitionsForm(EMCUserData userData) {
        super("Journal Access Group Definitions", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        this.drm = new EMCControlLookupComponentDRM(
                new emcGenericDataSourceUpdate(new BaseJournalAccessGroupDefinitions(), userData), userData);
        this.setDataManager(drm);
        drm.setTheForm(this);
        drm.setFormTextId1("accessGroupId");
        drm.setFormTextId2("definitionId");

        initFrame(userData);
    }

    private void initFrame(EMCUserData userData) {
        List keys = new ArrayList();
        keys.add("definitionId");

        emcTableModelUpdate m = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        EMCLookupJTableComponent lkpDefitionId = new EMCLookupJTableComponent(new JournalDefinitions());
        lkpDefitionId.setPopup(new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData));

        table.setLookupToColumn("definitionId", lkpDefitionId);

        emcJTextField txtDesc = new emcJTextField();
        txtDesc.setEditable(false);

        EMCControlLookupComponent lkpAccessGroup = new EMCControlLookupComponent(new JournalAccessGroupsMI(), drm, "accessGroupId", txtDesc, "description", BaseJournalAccessGroupDefinitions.class.getName());
        drm.setLookup(lkpAccessGroup);
        lkpAccessGroup.setPopup(new EMCLookupPopup(new BaseJournalAccessGroups(), "approvalGroupId", userData));

        this.add(new EMCControlLookupPanel("Journal Access Group Definitions", "Access Group", lkpAccessGroup, "Description", txtDesc, tableScroll, "Definitions"));
    }
}
