/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditnoteapprovalgroups;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.creditnoteapprovalgroups.resources.CreditNoteApprovalGroupsDRM;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCreditNoteApprovalGroupEmployeesMI;
import emc.menus.debtors.menuitems.display.DebtorsCreditNoteApprovalGroupsMI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : This form is used to capture/edit Credit Note Approval Groups.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditNoteApprovalGroupsForm extends BaseInternalFrame {

    private CreditNoteApprovalGroupsDRM drm;

    /** Creates a new instance of MarketingGroupForm */
    public CreditNoteApprovalGroupsForm(EMCUserData userData) {
        super("Credit Note Approval Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        drm = new CreditNoteApprovalGroupsDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCreditNoteApprovalGroups(), userData), userData);

        drm.setTheForm(this);
        this.setDataManager(drm);

        drm.setFormTextId1("approvalGroupId");
        drm.setFormTextId2("description");
        
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Overview", createOverviewTab());

        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("approvalGroupId");
        keys.add("description");
        keys.add("higherLevelApprovalGroupId");
        
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpHigherLevelApprovalGroup = new EMCLookupJTableComponent(new DebtorsCreditNoteApprovalGroupsMI());
        lkpHigherLevelApprovalGroup.setPopup(new EMCLookupPopup(new DebtorsCreditNoteApprovalGroups(), "approvalGroupId", getUserData()));

        table.setLookupToColumn("higherLevelApprovalGroupId", lkpHigherLevelApprovalGroup);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        panel.add(tableScroll);
        
        return panel;
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcMenuButton("Employees", new DebtorsCreditNoteApprovalGroupEmployeesMI(), this, 0, false));

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
