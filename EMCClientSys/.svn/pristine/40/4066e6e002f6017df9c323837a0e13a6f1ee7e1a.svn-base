/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.journalapprovalgroups;

import emc.app.components.emcJComboBox;
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
import emc.enums.base.journals.Modules;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.functions.Functions;
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
 * @author riaan
 */
public class JournalApprovalGroupsForm extends BaseInternalFrame {

    private emcJPanel pnlApprovalGroups = new emcJPanel();
    private emcJPanel pnlButtons = new emcJPanel();
    private JournalApprovalGroupsFormDRM dataRelation;

    /** Constructor used for base module form. */
    public JournalApprovalGroupsForm(EMCUserData userData) {
        this(userData, null);
    }

    /** Constructor used for other modules. */
    public JournalApprovalGroupsForm(EMCUserData userData, Modules module) {
        super("Journal Approval Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new JournalApprovalGroupsFormDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.journals.BaseJournalApprovalGroups(), userData), userData, module);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("journalApprovalGroupId");
            dataRelation.setFormTextId2("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame(module);
    }

    private void tabAppprovalGroups(Modules module) {
        List keys = new ArrayList();
        keys.add("journalApprovalGroupId");
        keys.add("description");

        //If module not null, do not add module field to grid.
        if (Functions.checkBlank(module)) {
            keys.add("groupModule");
        }
        
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);

        //If module not null, do not add module field to grid.
        if (Functions.checkBlank(module)) {
            toptable.setComboBoxLookupToColumn("groupModule", new emcJComboBox(Modules.values()));
        }

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
        pnlButtons.add(new emcMenuButton("Approval Employees", new emc.menus.base.menuItems.display.JournalApprovalGroupEmployees(), this, 0, false), gbc);
        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
    }

    private void initFrame(Modules module) {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabAppprovalGroups(module);
        tabButtons();
        tabbedPanetop.add("Journal Approval Groups", this.pnlApprovalGroups);

        this.add(tabbedPanetop, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.EAST);
    }
}
