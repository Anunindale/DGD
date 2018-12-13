/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemaccessgroup;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author wikus
 */
public class ItemAccessGroupForm extends BaseInternalFrame {

    private emcJPanel thePanel = new emcJPanel();
    private emcJPanel pnlApprovalGroups = new emcJPanel();
    private emcJPanel pnlButtons = new emcJPanel();
    private ItemAccessGroupsFormDRM dataRelation;

    public ItemAccessGroupForm(EMCUserData userData) {
        super("Item Access Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new ItemAccessGroupsFormDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemAccessGroup(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemAccessGroupId");
            dataRelation.setFormTextId2("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabAppprovalGroups() {
        List keys = new ArrayList();
        keys.add("itemAccessGroupId");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlApprovalGroups.setLayout(new GridLayout(1, 1));
        pnlApprovalGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void tabButtons() {
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(new emcMenuButton("Access Users", new emc.menus.inventory.menuitems.display.ItemAccessGroupEmployees(), this, 0, false), gbc);        
        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabAppprovalGroups();
        tabButtons();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(pnlApprovalGroups, BorderLayout.CENTER);
        thePanel.add(pnlButtons, BorderLayout.EAST);
        tabbedPanetop.add("Item Access Groups", this.thePanel);
        this.add(tabbedPanetop);
    }
}
