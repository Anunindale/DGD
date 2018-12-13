package emc.forms.creditors.display.approvalgroups;

import emc.app.components.emcJButton;
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
import emc.entity.creditors.CreditorsApprovalGroupSetup;
import emc.entity.creditors.CreditorsApprovalGroups;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.creditors.menuitems.display.CreditorsApprovalGroupSetupMI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @author claudette
 */
public class CreditorsApprovalGroupsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of CreditorsApprovalGroupsForm. */
    public CreditorsApprovalGroupsForm(EMCUserData userData) {
        super("Approval Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new CreditorsApprovalGroups(), userData), userData) {

            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData generated = super.generateRelatedFormUserData(formUserData, Index);
                EMCQuery query;
                List udList;
                switch (Index) {
                    case 1:
                        query = new EMCQuery(enumQueryTypes.SELECT, CreditorsApprovalGroupSetup.class);
                        query.addAnd("approvalGroupId", this.getLastFieldValueAt("approvalGroupId"));
                        udList = new ArrayList();
                        udList.add(0, query);
                        udList.add(1, "");
                        udList.add(this.getLastFieldValueAt("desctiption"));
                        udList.add(this.getLastFieldValueAt("approvalGroupId"));
                        generated.setUserData(udList);
                        break;
                }
                return generated;
            }
        };
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

    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("approvalGroupId");
        keys.add("description");
        keys.add("approveCreditNote");
        keys.add("approveInvoice");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

    private emcJPanel createButtonsPanel() {
        ArrayList<emcJButton> list = new ArrayList<emcJButton>();
        list.add(new emcMenuButton("Setup", new CreditorsApprovalGroupSetupMI(), this, 1, false));
        return emcSetGridBagConstraints.createButtonPanel(list);
    }
}
