/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.journalapprovalgroups;

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
import emc.entity.base.journals.BaseJournalApprovalGroups;
import emc.enums.base.journals.Modules;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLJournalApprovalGroupEmployeesMenu;
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
public class GLJournalApprovalGroupsForm extends BaseInternalFrame {

    private emcJPanel pnlApprovalGroups = new emcJPanel();
    private emcJPanel pnlButtons = new emcJPanel();
    private GLJournalApprovalGroupsFormDRM dataRelation;

    public GLJournalApprovalGroupsForm(EMCUserData userData) {
        super("Journal Approval Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            if (userData.getUserData(0) == null) {
                //This form was not opened from a related form.
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalApprovalGroups.class);
                //Only show approval groups for GL module
                query.addAnd("groupModule", Modules.GL.toString());

                userData.setUserData(0, query);
            }
            dataRelation = new GLJournalApprovalGroupsFormDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new BaseJournalApprovalGroups(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("journalApprovalGroupId");
            dataRelation.setFormTextId2("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabAppprovalGroups() {
        List keys = new ArrayList();
        keys.add("journalApprovalGroupId");
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
        pnlButtons.add(new emcMenuButton("Approval Employees", new GLJournalApprovalGroupEmployeesMenu(), this, 0, false), gbc);
        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
    }

    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabAppprovalGroups();
        tabButtons();
        tabbedPanetop.add("Journal Approval Groups", this.pnlApprovalGroups);
        this.add(tabbedPanetop, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.EAST);
    }
}
