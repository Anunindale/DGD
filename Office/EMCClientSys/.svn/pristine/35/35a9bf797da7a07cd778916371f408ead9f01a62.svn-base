/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.journalaccessgroups;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroups;
import emc.forms.base.display.journalaccessgroups.resources.AccessGroupsDRM;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.JournalAccessGroupDefinitionsMI;
import emc.menus.base.menuItems.display.JournalAccessGroupEmployeesMI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import emc.enums.base.journals.Modules;
import emc.functions.Functions;

/**
 * @author riaan
 */
public class JournalAccessGroupsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    public JournalAccessGroupsForm(EMCUserData userData) {
        this(userData, null);
    }
    /** Creates a new instance of JournalAccessGroupsForm. */
    public JournalAccessGroupsForm(EMCUserData userData, Modules module) {
        super("Journal Access Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);

        drm = new AccessGroupsDRM(new emcGenericDataSourceUpdate(new BaseJournalAccessGroups(),userData), userData,module);

        drm.setTheForm(this);
        this.setDataManager(drm);

        drm.setFormTextId1("accessGroupId");
        drm.setFormTextId2("accessGroupDescription");

        this.initFrame(module);
    }

    /** Initializes the frame. */
    private void initFrame(Modules module) {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Overview", createOverviewTab(module));

        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab(Modules module) {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("accessGroupId");
        keys.add("accessGroupDescription");
        //If module not null, do not add module field to grid.
        if (Functions.checkBlank(module)) {
            keys.add("groupModule");
        }

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //If module not null, do not add module field to grid.
        if (Functions.checkBlank(module)) {
            table.setComboBoxLookupToColumn("groupModule", new emcJComboBox(Modules.values()));
        }
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        panel.add(tableScroll);

        return panel;
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        //buttons.add(new emcMenuButton("Definitions", new JournalAccessGroupDefinitionsMI(), this, 1, false));
        buttons.add(new emcMenuButton("Employees", new JournalAccessGroupEmployeesMI(), this, 2, false));
        
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
