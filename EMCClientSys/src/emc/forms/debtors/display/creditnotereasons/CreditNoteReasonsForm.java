/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditnotereasons;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCreditNoteApprovalGroupsMI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : This form is used to capture/edit Credit Note reasons.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditNoteReasonsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    private EMCLookupJTableComponent lkpApprovalGroup;

    /** Creates a new instance of MarketingGroupForm */
    public CreditNoteReasonsForm(EMCUserData userData) {
        super("Credit Note Reasons", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCreditNoteReasons(), userData), userData);

        drm.setTheForm(this);
        this.setDataManager(drm);

        drm.setFormTextId1("reasonCode");
        drm.setFormTextId2("description");

        this.initLookups(userData);
        this.initFrame();
    }

    /** Initializes lookups. */
    private void initLookups(EMCUserData userData) {
        lkpApprovalGroup = new EMCLookupJTableComponent(new DebtorsCreditNoteApprovalGroupsMI());
        lkpApprovalGroup.setPopup(new EMCLookupPopup(new DebtorsCreditNoteApprovalGroups(), "approvalGroupId", userData));
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Overview", createOverviewTab());

        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("reasonCode");
        keys.add("description");
        keys.add("approvalGroupId");
        keys.add("handlingCharge");
        
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        table.setLookupToColumn("approvalGroupId", lkpApprovalGroup);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        panel.add(tableScroll);
        
        return panel;
    }
}
